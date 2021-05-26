package com.epam.services

import com.epam.repo.WordsRepo
import com.epam.utils.WordsUtil
import org.apache.spark.rdd.RDD
import org.springframework.stereotype.Component

import scala.collection.JavaConverters.asScalaBufferConverter


@Component
class MusicServiceImpl(wordsRepo: WordsRepo) extends MusicService {


  override def topXWithRate(musicianName: String, x: Int): Map[String, Int] = {
    wordsRepo.allLines(musicianName)
      .map(_.toLowerCase())
      .flatMap(line => WordsUtil.getWords(line).asScala)
      .map((_, 1))
      .reduceByKey(_ + _)
      .map(_.swap)
      .sortByKey(ascending = false)
      .map(_.swap)
      .take(x)
      .toMap


  }

  override def topX(musicianName: String, x: Int): List[String] = {
    topXWithRate(musicianName, x).map(_._1).toList
  }

  override def getValidWords(musicianName: String): RDD[String] = {
    val allWords =
      wordsRepo.allLines(musicianName)
        .map(_.toLowerCase())
        .flatMap(line => WordsUtil.getWords(line).asScala)
    val notValidWords =
      wordsRepo.notValidLines()
        .map(_.toLowerCase())
        .flatMap(line => WordsUtil.getWords(line).asScala)

    allWords.subtract(notValidWords)
  }

  override def compareTwoMusicians(musicianName1: String, musicianName2: String): List[String] =
  {
    val mu1 = getValidWords(musicianName1)
    val mu2 = getValidWords(musicianName2)

    mu1.intersection(mu2).collect().toList
  }
}
