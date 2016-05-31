package com.github.aparra.sparkenvtest

import com.github.aparra.sparkenvtest.config.SparkEnvTestJobConfig
import com.github.aparra.sparkenvtest.job.{JobLogic, Result, SuccessResult}

trait SparkEnvTestJobLogic extends JobLogic[SparkEnvTestJobConfig] {

  override def logic(jobConfig: SparkEnvTestJobConfig): Result = {
    SuccessResult(actions = Seq.empty)
  }
}