package day03

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by YBX on 2017/10/12.
  * Top N 算法spark实现
  */
object TopN {
  def main(args: Array[String]) {
    if (args.length < 2){
      System.err.println("usage: Top10NonUnique <input-path><port>")
      System.exit(1)
    }
    val conf = new SparkConf().setAppName("").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val  N = Integer.parseInt(args(0))
    val inputPath = args(1)
    val boradCast = sc.broadcast(N)

    val inputs = sc.textFile(inputPath, 1)   //分区数设为1
    inputs.saveAsTextFile(inputPath + "./output1.txt")
  }
}
