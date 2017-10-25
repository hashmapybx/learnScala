package day03

import java.util

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by YBX on 2017/10/11.
  * the demo from spark快速大数据分析这本书
  */


case class CallLog(callsign: String = "", contactlat: Double,
                   contactlong: Double, mylat: Double, mylong: Double)


object ChapterSixExample {

  def lookupInArray(sign: String, prefixArray: Array[String]) :String= {
    val pos = util.Arrays.binarySearch(prefixArray.asInstanceOf[Array[AnyRef]], sign) match {
      case x if x < 0 => -x -1
      case x => x
    }
    prefixArray(pos).split(",")(1)
  }

  def main(args: Array[String]) {
    //val master = args(0)
    val inputFile = args(1)
    val inputFile2 = args(2)
    val outputDir = args(3)
    val conf = new SparkConf().setAppName("ChapterSixExample").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val file = sc.textFile(inputFile)
    val count = sc.accumulator(0)     //spark广播变量的累加器,统计输入数据中每行包含字符串“lovecewvv”的次数

    file.foreach(line => {
      if (line.contains("KK6JKQ")){   //利用累加器
        count+=1
      }
    })

    println("Lines with 'KK6JKQ': " + count.value)
    // Create Accumulator[Int] initialized to 0
    val errorLines = sc.accumulator(0)
    val dataLines = sc.accumulator(0)
    val validSignCount = sc.accumulator(0)
    val invalidSignCount = sc.accumulator(0)
    val unknownCountry = sc.accumulator(0)
    val resolvedCountry = sc.accumulator(0)

    val callsigns = file.flatMap(line => {
      if (line == ""){
        errorLines +=1
      }else{
        dataLines +=1
      }
      line.split(" ")
    })

    //validate a call sign
    val callSignRegex = "\\A\\d?[a-zA-Z]{1,2}\\d{1,4}[a-zA-Z]{1,3}\\Z".r
    val validSigns = callsigns.filter{sign =>
      if ((callSignRegex findFirstIn sign).nonEmpty) {
        validSignCount += 1; true
      } else {
        invalidSignCount += 1; false
      }
    }
    val contactCounts = validSigns.map(callSign => (callSign, 1)).reduceByKey(_+_)
    //统计一下结果值
    contactCounts.count()
    if (invalidSignCount.value < 0.5 * validSignCount.value){
      contactCounts.saveAsTextFile(outputDir + "/output.txt")
    }else{
      println(s"there are too many error record ${invalidSignCount.value} for ${validSignCount.value}")
    }

    val signPrefixes = sc.broadcast(loadCallSignTable())

    val countryCOntactCounts = contactCounts.map{case (sign, count) => {
      val country = lookupInArray(sign, signPrefixes.value)
      (country, count)

    }}.reduceByKey((x, y) => x+y)
    countryCOntactCounts.saveAsTextFile(outputDir + "/countries.txt")

  }

  def loadCallSignTable()={
    Source.fromFile("./files/callsign_tb1_sorted").getLines().filter(_ != "").toArray
  }
}

