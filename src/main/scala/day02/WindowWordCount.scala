package day02

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by YBX on 2017/10/3.
  * Dstream 滑动窗口设计 wordcount统计案例
  *
  */
object WindowWordCount {
  def main(args: Array[String]) {
    //传入参数必须是localhost ip 窗口的长度 和滑动间隔
    /**
      * （1）窗口长度（window length），即窗口的持续时间，上图中的窗口长度为3
      * （2）滑动间隔（sliding interval），窗口操作执行的时间间隔，上图中的滑动间隔为2
      * 这两个参数必须是原始DStream 批处理间隔（batch interval）的整数倍（上图中的原始DStream的batch interval为1）
      *
      * */
    if (args.length < 4){
      System.err.println("usage :window wordcount <hostname> <port><window length><slidingDuration>")
      System.exit(1)

    }

    //设置日志级别
    StreamingExamples.setStreamingLogLevels()

    //创建SSC 时间间隔是5秒
    val conf = new SparkConf().setAppName("WindowWOrdCount").setMaster("local[4]")
    val ssc = new StreamingContext(conf, Seconds(5))

    //socket作为数据源
    val lines = ssc.socketTextStream(args(0), args(1).toInt, StorageLevel.MEMORY_AND_DISK_SER)
    val words = lines.flatMap(p => p.split(" "))

    //对window进行操作 对窗口中的单词进行计数
    val wordCount = words.map(x => (x, 1)).reduceByKeyAndWindow((a:Int, b:Int) => (a+b),
      Seconds(args(2).toInt), Seconds(args(3).toInt))

    wordCount.print()
    ssc.start()

    ssc.awaitTermination()
  }
}
