package com.github.aparra.sparkenvtest.job

import org.apache.spark.SparkContext

trait JobLogic[T <: JobConfig] {

  def context: SparkContext
  def logic(jobConfig: T): Result

  def run(jobConfig: T) {
    val execute = ActionExecutor.executeUsing(context.hadoopConfiguration) _
    logic(jobConfig) match {
      case success: SuccessResult =>
        success.actions foreach execute
      case failure: FailureResult =>
        failure.actions foreach execute
        throw failure.exception
    }
  }
}