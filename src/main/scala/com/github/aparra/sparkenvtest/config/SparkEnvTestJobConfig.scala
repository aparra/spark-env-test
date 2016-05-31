package com.github.aparra.sparkenvtest.config

import com.github.aparra.sparkenvtest.job.{JobConfig, OutputPathBuilder}

trait SparkEnvTestJobConfig extends JobConfig {
  def fileOutputPath: OutputPathBuilder
}

object SparkEnvTestJobConfig {

  private[config] case class OptJobConfig(
    baseOutputPath: String = "",
    tmpBaseOutputPath: String = "",
    partitions: Int = 1
  ) extends SparkEnvTestJobConfig {

    override def fileOutputPath: OutputPathBuilder =
      OutputPathBuilder(this, path = "test.txt")
  }

  object OptJobConfig {

    val BASE_OUTPUT_PATH = "baseOutputPath"
    val TMP_OUTPUT_PATH = "tmpBaseOutputPath"
    val PARTITIONS = "partitions"

    private[config] val optParser = new scopt.OptionParser[OptJobConfig]("osc-reporting-sme-usage") {
      head("spark-env-test")

      opt[String](BASE_OUTPUT_PATH) required() valueName "<dir>" action { (p, c) =>
        c.copy(baseOutputPath = p)
      }

      opt[String](TMP_OUTPUT_PATH) required() valueName "<dir>" action { (p, c) =>
        c.copy(tmpBaseOutputPath = p)
      }

      opt[Int](PARTITIONS) required() valueName "<number-of-partitions>" action { (p, c) =>
        c.copy(partitions = p)
      }
    }
  }

  def fromArgs(args: Seq[String]): Option[SparkEnvTestJobConfig] =
    OptJobConfig.optParser.parse(args, OptJobConfig())
}
