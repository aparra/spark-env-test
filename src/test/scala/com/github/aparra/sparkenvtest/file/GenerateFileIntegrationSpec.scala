package com.github.aparra.sparkenvtest.file

import java.io.File
import java.nio.file.{Files, Paths}

import com.github.aparra.sparkenvtest.BaseSpec
import com.github.aparra.sparkenvtest.config.SparkEnvTestJobConfig
import com.github.aparra.sparkenvtest.io.FileWriter
import com.github.aparra.sparkenvtest.job.{Action, ActionExecutor, OutputPathBuilder}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalacheck.Gen
import org.scalatest.BeforeAndAfterAll

import scala.io.Source

class GenerateFileIntegrationSpec extends BaseSpec with BeforeAndAfterAll {

  implicit override val generatorDrivenConfig = PropertyCheckConfig(minSuccessful = 5)

  val config = new SparkConf().setMaster("local").setAppName("spark-env-test")
  val sparkContext = new SparkContext(config)

  val tempBasePath = "target/temp"
  val outBasePath = "target/out"

  case class StubJobConfig(tmpBaseOutputPath: String, baseOutputPath: String) extends SparkEnvTestJobConfig {
    override def fileOutputPath: OutputPathBuilder = OutputPathBuilder(this, path = "fake.txt")
  }

  override def afterAll() = {
    sparkContext.stop()
    sys.runtime.exec(s"rm -rf $tempBasePath").waitFor()
    sys.runtime.exec(s"rm -rf $outBasePath").waitFor()
  }

  private def fileContent = for {
    lines <- Gen.listOfN(5, List("Fake Content"))
  } yield sparkContext.parallelize(lines)

  property("Lines are merged into a single file") {
    forAll(fileContent) { rdd =>
      val jobConfig = StubJobConfig(s"$tempBasePath/${System.nanoTime()}", s"$outBasePath/${System.nanoTime()}")

      FileWriter.csv.write(in = jobConfig.fileOutputPath.tmp)(lines = rdd)
      Action.mergeFromTmpToReliable(jobConfig.fileOutputPath) foreach ActionExecutor.executeUsing(sparkContext.hadoopConfiguration)

      Files.exists(Paths.get(jobConfig.fileOutputPath.tmp)) mustBe false
      Files.exists(Paths.get(jobConfig.fileOutputPath.reliable)) mustBe true

      val csvLines = Source.fromFile(new File(jobConfig.fileOutputPath.reliable)).getLines().count(_.nonEmpty)
      csvLines mustEqual rdd.count()
    }
  }
}

