package day02

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{Logging, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by YBX on 2017/10/1.
  * spark streaming 的案例分析
  *其中最重要的master的URL，如果在本地运行的话"local[n]"，n必须大于receiver的数量。因为当inputDstream 与receivers关联的
  * 时候，receiver自身就需要一个线程来运行，否则便没有线程去接受数据。这样数据源传进来的数据就不能被处理。
  * 所以，不能使用"local[1]"或者"local".
  *
  * 另外在集群上运行的时候，要根据分配的CPU核数也必须大于receiver的数量。否则系统将无法处理数据。
  */
object WordCount {
  def main(args: Array[String]) {

    if(args.length < 2){
      System.err.println("Usage：NetworkWordCount <hostname> <port>")
      System.exit(1)
    }


    //修改日志级别次为Level.WARN
    StreamingExamples.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("WordCount").setMaster("local[3]")
    //seconds(2)设置间隔时间
    val ssc = new StreamingContext(conf, Seconds(2))

    val data = ssc.textFileStream("C:\\b.txt")
    val words = data.flatMap(_.split(" "))
    val countWords = words.map(p =>(p, 1)).reduceByKey(_+_)
    countWords.print()

    //启动streamingContext
    ssc.start()

    //spark streaming 启动一直运行。
    ssc.awaitTermination()
  }
}


//为了后期查看运行的结果，修改日志级别为Level.WARN
object  StreamingExamples extends  Logging {

  def setStreamingLogLevels(): Unit = {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      // We first log something to initialize Spark's default logging, then we override the
      // logging level.
      logInfo("Setting log level to [WARN] for streaming example." +
        " To override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)
    }

  }

}
