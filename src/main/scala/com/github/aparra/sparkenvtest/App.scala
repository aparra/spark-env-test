package com.github.aparra.sparkenvtest

import com.github.aparra.sparkenvtest.config.SparkEnvTestJobConfig
import org.apache.spark.{SparkConf, SparkContext}

object App extends App {

  SparkEnvTestJobConfig fromArgs args foreach (new Main(_))

  class Main(jobConfig: SparkEnvTestJobConfig) extends SparkEnvTestJobLogic {

    val config = new SparkConf()
    override val context = new SparkContext(config)

    try { run(jobConfig) } finally { context.stop() }
  }
}