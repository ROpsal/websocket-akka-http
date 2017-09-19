name := "websocket-akka-http"

version := "1.0"
scalaVersion := "2.12.3"

libraryDependencies ++= {
  val akkaHttpVersion   = "10.0.10"
  val akkaStreamVersion = "2.5.4"

  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion,
    "org.java-websocket" % "Java-WebSocket" % "1.3.4"
  )
}