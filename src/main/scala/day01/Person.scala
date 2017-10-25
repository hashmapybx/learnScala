package day01

/**
  * Created by YBX on 2017/9/22.
  * 类的继承关系
  */
class Person(name:String, age:Int) {
  println("construction person")
}

class Student(name:String, age:Int, var studnetNo: String) extends Person(name, age){
  println("construction student")

  override def toString: String = super.toString
}
object demo{
  def main(args: Array[String]) {
    val student = new Student("tom", 12, "1002")
    println(student.studnetNo)
  }
}


//构造函数执行的顺序

