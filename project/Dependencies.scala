import sbt._

object Dependencies {

  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.12.4" % "test"
  val scalaTest = "org.scalatest" %% "scalatest"  % "2.2.5" % "test"

  val spark = "org.apache.spark" %% "spark-core" % "1.6.0" % "provided"
  val protobuf = "com.google.protobuf" % "protobuf-java" % "2.5.0"

  val scopt = "com.github.scopt" %% "scopt" % "3.3.0"

  val scalaCsv = "com.github.tototoshi" %% "scala-csv" % "1.2.2"

  def all = Seq(spark, protobuf, scopt, scalaCsv) ++ Seq(scalaCheck, scalaTest)
}
