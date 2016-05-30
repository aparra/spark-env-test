val rootProject = SparkEnvTestProject.project
  .settings(Assembly.settings())
  .settings(mainClass in assembly := Some("com.github.aparra.sparkenvtest.App"))

scalaVersion in ThisBuild := "2.10.6"
