package com.github.aparra.sparkenvtest.job

sealed trait Result

case class SuccessResult(actions: Seq[Action]) extends Result

case class FailureResult(exception: Exception, actions: Seq[Action]) extends Result
