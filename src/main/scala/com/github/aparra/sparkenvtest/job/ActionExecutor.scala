package com.github.aparra.sparkenvtest.job

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}

object ActionExecutor {

  def executeUsing(hadoopConfiguration: Configuration)(action: Action) {
    val hdfs = FileSystem.get(hadoopConfiguration)
    val deleteSource = true

    action match {
      case remove: RemovePath =>
        hdfs.delete(new Path(remove.target), true)

      case move: MovePath =>
        FileUtil.copy(
          hdfs, new Path(move.from),
          hdfs, new Path(move.to),
          deleteSource,
          hadoopConfiguration)

      case merge: MergeFile =>
        FileUtil.copyMerge(
          hdfs, new Path(merge.from),
          hdfs, new Path(merge.to),
          deleteSource,
          hadoopConfiguration,
          null)
    }
  }
}