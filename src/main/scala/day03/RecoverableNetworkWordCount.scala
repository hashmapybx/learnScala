package day03

import java.io.File
import java.nio.charset.Charset

import com.google.common.io.Files
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}

/**
  * Created by YBX on 2017/10/7.
  * spark streaming checkpoint机制
  */
object RecoverableNetworkWordCount {
  def createContext(ip:String, port: Int, outputpath:String, checkPointDir:String): StreamingContext ={

    println("cteating new context")
    val outputFile = new File(outputpath)
    if(outputFile.exists()) outputFile.delete()
    val conf = new SparkConf().setAppName("RecoverableNetworkWordCount").setMaster("local[4]")
    val ssc = new StreamingContext(conf, Seconds(1))
    ssc.checkpoint(checkPointDir)

    val lines = ssc.socketTextStream(ip, port)
    val words = lines.flatMap(_.split(" "))
    val wordsCount = words.map(x => (x, 1)).reduceByKey(_+_)
    wordsCount.foreachRDD((rdd:RDD[(String, Int)], time:Time) => {
      val counts = "counts at time " + time + " " + rdd.collect().mkString("[", ", ", "]")

      println(counts)
      println("Appending to " + outputFile.getAbsolutePath)
      Files.append(counts + "\n", outputFile, Charset.defaultCharset())
    })
    ssc
  }

  //将string 转化为int
  private object IntParam{
    def unapply(str: String): Option[Int] = {
      try {                     //try...catch 快捷键 ctrl + alt + t
        Some(str.toInt)
      } catch {
        case e : NumberFormatException => None
      }
    }
  }

  def main(args: Array[String]) {
    if (args.length != 4) {
      System.err.println("You arguments were " + args.mkString("[", ", ", "]"))
      System.err.println(
        """
          |Usage: RecoverableNetworkWordCount <hostname> <port> <checkpoint-directory>
          |     <output-file>. <hostname> and <port> describe the TCP server that Spark
          |     Streaming would connect to receive data. <checkpoint-directory> directory to
          |     HDFS-compatible file system which checkpoint data <output-file> file to which the
          |     word counts will be appended
          |
          |In local mode, <master> should be 'local[n]' with n > 1
          |Both <checkpoint-directory> and <output-file> must be absolute paths
        """.stripMargin
      )
      System.exit(1)
    }
    val Array(ip, IntParam(port), checkpointDirectory, outputPath) = args
    //getOrCreate方法，从checkpoint中重新创建StreamingContext对象或新创建一个StreamingContext对象
    val ssc = StreamingContext.getOrCreate(checkpointDirectory,
      () => {
        createContext(ip, port, outputPath, checkpointDirectory)
      })
    ssc.start()
    ssc.awaitTermination()
  }
}
