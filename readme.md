##Websocket server

Sample code that shows how to use `akka-http` and `akka-streams` to build custom websocket server.

This is a modified clone of the [websocket-akka-http](https://github.com/ScalaConsultants/websocket-akka-http) project.  Modifications include:

- Move to Scala 2.11.8
- Move to Akka streams 2.4.16, no experimental
- Move to Akka Http 10.0.1, no experimental
- Move to SBT 0.13.13
- Syntax migration to Akka Http 10.0
- Changed from port 8080 to 9000

You can read about the code at the orignal author's [scalac.io](http://blog.scalac.io/2015/07/30/websockets-server-with-akka-http.html) blog.

Port 8080 was causing a *BindException: Address already in use* error, so changed to port 9000.

To run the websocket server:

    sbt run

Background actor that spams messages to channel number *123*.

    sbt "run with-client"


Websocket client terminal suggestion for Chrome is [*Dark WebSocket Terminal*](https://chrome.google.com/webstore/detail/dark-websocket-terminal/dmogdjmcpfaibncngoolgljgocdabhke?hl=en).

Echo server example on Dark Terminal:

	/connect ws://localhost:9000/ws-echo
	/send "Echo\ text."
	/disconnect

![echo console view](https://github.com/ROpsal/websocket-akka-http/blob/master/images/echo-window.png) 


Chat room example on Dark Terminal:

	/connect ws://localhost:9000/ws-chat/123?name=user
	/send "Whatever\ message\ you\ want\ to\ send."
	/disconnect

Where *123* is the chatroom number and *user* is the user name. If you want spaces, you need to escape them.

![chat console view](https://github.com/ROpsal/websocket-akka-http/blob/master/images/chat-window.png)

Another websocket client, implemented as a browser page, is at [http://www.websocket.org/echo.html](http://www.websocket.org/echo.html).