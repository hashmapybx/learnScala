package com.ybx.cnn

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem}
import com.typesafe.config.ConfigFactory

//用于定时器
import scala.concurrent.duration._
/**
  * 自定义RPC框架
  * 在master启动之后，worker启动，worker启动之后想master发送注册信息(将信息封装为case class)
  * worker
  *
  * */
class RpcWorker(val masterHost: String, val masterPort: Int, val memory: Int, val core: Int) extends Actor{

  var master : ActorSelection = _
  val workerId = UUID.randomUUID().toString  //产生workerid

  val CHECK_INTERVAL = 10000
  override def preStart(): Unit = {
    master = context.actorSelection(s"akka.tcp://MasterSystem@$masterHost:$masterPort/user/master")
   //想master发送注册
    master ! RegisterWorker(workerId, memory, core)
  }

  override def receive : Receive ={
    //worker接受master发送过来的信息
    case RegisteredWorker(masterUrl) => {
      println(masterUrl)
      // 发送定时心跳，需要定时器 启动定时器发送心跳 间隔单位怎么搞.可以先给自己发送,

      context.system.scheduler.schedule(0 millis, CHECK_INTERVAL millis, self, SendHeartbeat)
    }

    case SendHeartbeat => {
      //想master发送心跳
      println("send heartbeat to master")
      master ! HeartBeat(workerId)
    }
  }
}


object RpcWorker{
  def main(args: Array[String]): Unit = {

    val workerhost = args(0)
    val workerport = args(1)
    val masterhost = args(2)
    val masterport = args(3)
    val stringconfig =
      """
        |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
        |akka.remote.netty.tcp.hostname = "$host"
        |akka.remote.netty.tcp.port = "$port"
      """.stripMargin
    val config = ConfigFactory.parseFile(stringconfig)
    //创建ActorSystem负责监督整个actor的通信
    val actorSystem = ActorSystem("workersystem", config)
    actorSystem.actorOf(new RpcWorker(masterhost, masterport, memory, core), "RpcWorker")

    //等待该进程结束退出
    actorSystem.awaitTermination()
  }
}