package day02

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by YBX on 2017/10/1.
  * 该程序通过netcat启动一个server,在sever端发布数据 streamingContext 程序的客户端接受数据
  *相当于netcat启动一个socket数据源
  * 要注意对于netcat 在linux环境下 nc -lp 9999 开启socket端口。然后往这个socket连接渠道里面发送数据
  *
  * [root@datanode3 ~]# nc -lp 9999
                    helo hadoop
                    second the is a love dog
  * 控制台上打印的信息
  * Time: 1506863951000 ms
-------------------------------------------

-------------------------------------------
Time: 1506863952000 ms
-------------------------------------------

-------------------------------------------
Time: 1506863953000 ms
-------------------------------------------

-------------------------------------------
Time: 1506863954000 ms
-------------------------------------------
(second,1)
(dog,1)
(a,1)
(the,1)
(is,1)
(love,1)

-------------------------------------------
Time: 1506863955000 ms
-------------------------------------------

  *
  */
object NetWordCount1 {
  def main(args: Array[String]) {
    //程序的健壮性判断。对传参数args进行判断
    if (args.length < 2) {
      System.err.println("Usage: NetworkWordCount <hostname> <port>")
      System.exit(1)
    }

    //设置程序运行的日志级别
    StreamingExamples.setStreamingLogLevels()

    val conf = new SparkConf().setAppName("NetworkWordCount").setMaster("local[4]")
    val ssc = new StreamingContext(conf, Seconds(1))

    /** create a socket stream on ip:port target count the words input stream of \n delimited text
      * note that there is no duplication o storage level only for running local
      *
      * replication necessary in distributed scenario gor fault tolerance.
      * */
    val lines = ssc.socketTextStream(args(0), args(1).toInt, StorageLevel.MEMORY_AND_DISK_SER)

    val words = lines.flatMap(_.split(" "))
    val wordsCount = words.map(p => (p, 1)).reduceByKey(_ + _)

    wordsCount.print()

    ssc.start()

    ssc.awaitTermination()
  }
}