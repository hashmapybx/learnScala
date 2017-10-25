package day02

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by YBX on 2017/10/2.
  * 对前面的wordcount的方法进行升级。可以实现连续的进行计数，使用updateStateByKey方法进行
  * 进行连续的叠加计数统计单词数量
  */
object StatefulNetworkWordCount {
  def main(args: Array[String]) {
    if (args.length <2){
      System.err.println("usage: StatefulNetworkWordCount <hostname> <port>")
      System.exit(1)
    }

    //函数字面量，输入当前值与前一次的状态结果进行累加。
    val updateFunc = (values: Seq[Int], state: Option[Int]) => {

      val current = values.sum
      val previousCount = state.getOrElse(0) //前一次的状态的结果值

      Some(current + previousCount)
    }

    val newUpdateFunc = (iterator: Iterator[(String, Seq[Int], Option[Int])]) => {
      iterator.flatMap( t => updateFunc(t._2, t._3).map(p => (t._1, p)))
    }

    val conf = new SparkConf().setAppName("StatefulNetworkWordCount").setMaster("local[4]")
    val ssc = new StreamingContext(conf, Seconds(1))  //间隔一秒时间处理一次

    //checkpoint()
    ssc.checkpoint("D:\\QQ文件")
    //RDD的初始化值
    val initialRDD = ssc.sparkContext.parallelize(List(("hello", 1), ("world", 1),("hadoop", 1)))
    //使用socket作为输入源
    val lines = ssc.socketTextStream(args(0), args(1).toInt)
    val words = lines.flatMap(p => p.split(" "))
    val wordsDstream = words.map( word => (word, 1))
    //调用updateStateByKey
    val stateDstream = wordsDstream.updateStateByKey[Int](newUpdateFunc,
      new HashPartitioner(ssc.sparkContext.defaultParallelism), true, initialRDD)
    stateDstream.print()

    ssc.start()
    //等到程序退出
    ssc.awaitTermination()
  }
}
