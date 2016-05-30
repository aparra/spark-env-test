import sbt.Keys._
import sbt._

object SparkEnvTestProject {

  lazy val IntegrationTest = config("it") extend Test

  lazy val project = Project(id = s"spark-env-test", base = file("."))
    .configs(IntegrationTest)
    .settings(inConfig(IntegrationTest)(Defaults.testTasks): _*)
    .settings(
      parallelExecution in Test := true,
      parallelExecution in IntegrationTest := false,
      testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
      testOptions in IntegrationTest := Seq(Tests.Filter(integrationTestFilter))
    )
    .settings(libraryDependencies ++= Dependencies.all)
    .settings(
      organization := "com.github.aparra",
      version := "0.0.1-SNAPSHOT",
      scalaVersion := "2.10.6",
      dependencyOverrides += Dependencies.protobuf,
      scalacOptions ++= Seq("-Xmax-classfile-name",  "200")
    )

  def integrationTestFilter(name: String): Boolean = name endsWith "IntegrationSpec"
  def unitTestFilter(name: String): Boolean = !integrationTestFilter(name)
}
