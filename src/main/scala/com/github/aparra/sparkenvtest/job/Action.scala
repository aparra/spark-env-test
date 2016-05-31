package com.github.aparra.sparkenvtest.job

sealed trait Action

case class MovePath(from: String, to: String) extends Action

case class MergeFile(from: String, to: String) extends Action

case class RemovePath(target: String) extends Action

object Action {

  def remove(paths: String*) = paths map RemovePath

  def moveFromTmpToReliable(paths: OutputPathBuilder*) =
    paths.map(path => MovePath(from = path.tmp, to = path.reliable))

  def mergeFromTmpToReliable(paths: OutputPathBuilder*) =
    paths.map(path => MergeFile(from = path.tmp, to = path.reliable))
}
