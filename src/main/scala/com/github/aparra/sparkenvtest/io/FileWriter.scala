package com.github.aparra.sparkenvtest.io

import java.io._

import com.github.tototoshi.csv.{CSVFormat, CSVWriter, DefaultCSVFormat, TSVFormat}
import org.apache.commons.lang.StringUtils._
import org.apache.spark.rdd.RDD

case class FileWriter(format: CSVFormat) {

  def write(in: String, repartition: Option[Int] = None)(lines: RDD[List[String]]) {
    val output = repartition match {
      case Some(value) => lines.repartition(value)
      case _ => lines
    }
    output.mapPartitions(toFormat).saveAsTextFile(in)
  }

  private def toFormat(partitionLines: Iterator[List[String]]): Iterator[String] = {
    val writer = new StringWriter()
    val typeWriter = CSVWriter.open(writer)(format)
    try {
      typeWriter.writeAll(partitionLines.toList)
    } finally { typeWriter.close() }

    writer.toString match {
      case output if isNotBlank(output) => Iterator(output)
      case _ => Iterator.empty
    }
  }
}

object FileWriter {
  object tsvFormat extends TSVFormat
  object csvFormat extends DefaultCSVFormat

  def tsv = FileWriter(tsvFormat)
  def csv = FileWriter(csvFormat)
}
