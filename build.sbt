
name := "firstSbt"

version := "1.0"
organization := "com.databricks"
scalaVersion := "2.10.6"

libraryDependencies ++= Seq(

  "org.apache.spark" % "spark-core_2.10" % "1.6.1",
  "org.apache.spark" % "spark-streaming_2.10" % "1.6.1" ,
  "org.apache.spark" % "spark-mllib_2.10" % "1.6.1" ,
  "org.apache.spark" % "spark-hive_2.10" % "1.6.1"
)