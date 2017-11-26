package com.ybx.cnn


/**
  * 发送消息
  * */
trait RemoteMessage extends serializable


//worker---master
case class RegisterWorker(id: String, memory: Int, core: Int) extends RemoteMessage

//master---> worker的信息
case class RegisteredWorker(masterUrl: String) extends RemoteMessage

//worker -->worker
case object SendHeartbeat

//worker --> master
case class HeartBeat(workerId: String) extends RemoteMessage

//master---master
case object CheckTimeOutWorker