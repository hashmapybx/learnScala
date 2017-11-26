package com.ybx.cnn

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable

//导入用于定时器的报
import scala.concurrent.duration._
class RpcMaster(val masterHost: String, val masterPort: Int) extends Actor{
 //创建集合存储信息
  val idToworker = new mutable.HashMap[String, WorkerInfo]() //(id, workerInfo)

  //以后可以根据worker的信息进行排序,当以后worker挂掉之后，可以方便的删除worker的信息
  val workers = new mutable.HashSet[WorkerInfo]()
  //worker发送过来的注册信息,master接受信息


  override def preStart(): Unit = {
    //在master完成构造的时候就启动一个定时器，用于检测超市的worker
    println("prestart invoked after main constructor")
    //注意这边的时间间隔要大于心跳的时间，不能小于心跳时间
    //导入饮食转换的依赖
    import context.dispatcher
    context.system.scheduler.schedule(0 millis, 15000 millis, self, CheckTimeOutWorker)
  }

  override def receive : Receive ={

    case RegisterWorker(id, memory, core) => {
      //判断该worker的id信息有没有注册过
      if (!idToworker.contains("id")) {
        //把worker信息保存到本地文件系统，或者ZK里面
        val workerInfo = new WorkerInfo(id, memory, core)
        idToworker(id) = workerInfo
        workers += workerInfo    //将注测得worker保存到set集合
        //当worker注册完成之后，master要想worker返回一个消息，说你注册成功了以后只要给我发送心跳就OK了
        sender ! RegisteredWorker(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/RpcMaster")


      }
    }
     //接受worker发送过来的心跳
    case HeartBeat(id) => {
        //判断是否包含这个worker信息
      if (idToworker.contains(id)){
        val workerInfo = idToworker(id)
        //报活
         val currentTime = System.currentTimeMillis()
        //更新上衣次的心跳时间
        workerInfo.lastHeartbeatTime = currentTime

      }
    }
    //过滤超市的worker的信息
    case CheckTimeOutWorker => {
      val currentTme = System.currentTimeMillis()
      val toRemove = workers.filter(x => (currentTme - x.lastHeartbeatTime) > 5000 millis)
      for (w <- toRemove) {
        workers -= w     //去掉死掉的worker
        idToworker -= w.id
      }

      println(workers.size)  //打印出具有几个worker
    }

  }
}


object RpcMaster{
  def main(args: Array[String]): Unit = {
    val host = args(0)
    val port = args(1).toInt
    val confStr =
      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
      """.stripMargin

    val conf = ConfigFactory.parseString(confStr)
    //创建ActorSystem,是单例的是管理actor的老大
    val actorSystem = ActorSystem("MasterSystem", conf)
    //创建actor
    val master = actorSystem.actorOf(Props(new RpcMaster(host, port)), "RpcMaster")
   // master ! "hello"
    actorSystem.awaitTermination()
  }
}