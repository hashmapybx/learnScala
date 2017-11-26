package com.ybx.cnn


//封装worker的信息
class WorkerInfo(val id: String, val memory: Int, val core: Int) {
  //todo 上衣次心跳

  var lastHeartbeatTime : Long = _
}
