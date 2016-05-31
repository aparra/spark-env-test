package com.github.aparra.sparkenvtest.job

trait JobConfig {
  def baseOutputPath: String
  def tmpBaseOutputPath: String
}

case class OutputPathBuilder(jobConfig: JobConfig, path: String) {
  def tmp: String = s"${jobConfig.tmpBaseOutputPath}/$path"
  def reliable: String = s"${jobConfig.baseOutputPath}/$path"
}
