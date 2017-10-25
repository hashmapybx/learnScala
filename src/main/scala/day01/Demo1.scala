package day01

/**
  * Created by YBX on 2017/9/21.
  * 本节主要讲解IndexedRowMatrix and BlockMatrix
  */
import java.util.{HashMap => JavaHashMap}
object Demo1 {
  def main(args: Array[String]) {
    val javaHashMap = new JavaHashMap[String, String]()
    javaHashMap.put("saprk", "hadoop")
    javaHashMap.put("mapreduce", "good")
    for (key <- javaHashMap.keySet().toArray()){
      println(key + ":" + javaHashMap.get(key))
    }
  }
}


abstract class Animal{
  var height: Int
  var color: String
  def eat:Unit
}
class Dog(var height:Int, var color: String) extends Animal{

  private var leg: Int = 0
  class Small(var sheight:Int, var scolor: String){
    def getDog(dog: Dog) = dog.leg
  }
  def eat()={
    println("the dog is ")
  }

}

