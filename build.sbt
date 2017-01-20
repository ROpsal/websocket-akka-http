name := "websocket-akka-http"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaHttpVersion   = "10.0.1"
  val akkaStreamVersion = "2.4.16"

  Seq(
    "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "org.java-websocket" % "Java-WebSocket" % "1.3.0"
  )
}