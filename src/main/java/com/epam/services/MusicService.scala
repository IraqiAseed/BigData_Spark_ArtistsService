package com.epam.services

import org.apache.spark.rdd.RDD


trait MusicService {

  def topXWithRate(musicianName: String, x: Int): Map[String, Int]
  def topX(musicianName: String, x: Int):List[String]
  def getValidWords(musicianName: String):RDD[String]
  def compareTwoMusicians(musicianName1: String,musicianName2: String):List[String]
}
