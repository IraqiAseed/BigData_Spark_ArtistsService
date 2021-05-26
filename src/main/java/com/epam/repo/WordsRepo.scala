package com.epam.repo

import org.apache.spark.rdd.RDD


trait WordsRepo {
  def allLines(musicianName: String): RDD[String]
  def notValidLines(): RDD[String]
}
