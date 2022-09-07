name := "keystore-opener"

version := "1.0"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq (
  "org.bouncycastle" % "bcprov-jdk15on" % "1.66",
  "commons-codec" % "commons-codec" % "1.14",
  "com.typesafe.akka" %% "akka-actor" % "2.5.32"
)
