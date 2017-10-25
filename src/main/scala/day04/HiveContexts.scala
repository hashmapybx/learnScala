package day04

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by YBX on 2017/10/22.
  */
object HiveContexts {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("HiveContexts").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val hiveContext = new HiveContext(sc)

    val input = hiveContext.jsonFile("c:/people.json")
    input.registerTempTable("people")

    val age = hiveContext.sql("select name, age from people where age > 13")
    age.show(2)
  }

}
