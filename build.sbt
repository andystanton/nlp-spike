name := """hello-scala"""

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.apache.opennlp" % "opennlp-tools" % "1.5.3"
)
