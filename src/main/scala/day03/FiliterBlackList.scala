package day03

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by YBX on 2017/10/11.
  * spark实现实时过滤黑名单
  */
object FiliterBlackList {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("").setMaster("local[3]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(2))

    //设置要过滤的黑名单
    val b1 = Array(("Jim", true), ("Tom", true))
    //设置并行度
    val b1Rdd = sc.parallelize(b1, 3)
    val st = ssc.socketTextStream("localhost", 8888)  //nc -lp 8888
    //对输入数据进行转换
    val users = st.map{user => (user.split(" ")(1), 1)}

    val validRDD = users.transform( user => {
      val leftJoined = user.leftOuterJoin(b1Rdd)
      val filterRDD = leftJoined.filter(a => {
        if (a._2._2.getOrElse(false)){
          false
        }else{
          true
        }
      })
      val validRDD = filterRDD.map(b => b._2._1)
      validRDD
    })

    validRDD.print()
    //执行
    ssc.start()
    //等待完成
    ssc.awaitTermination()
  }
}
