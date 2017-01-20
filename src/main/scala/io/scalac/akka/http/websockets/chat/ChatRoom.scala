package io.scalac.akka.http.websockets.chat

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.GraphDSL.Implicits._
import akka.stream.scaladsl._
import akka.stream.{FlowShape}

class ChatRoom(roomId: Int, actorSystem: ActorSystem) {

  private[this] val chatRoomActor = actorSystem.actorOf(Props(classOf[ChatRoomActor], roomId))

  def websocketFlow(user: String) : Flow[Message, Message, _] = Flow.fromGraph (

      GraphDSL.create(Source.actorRef[ChatMessage](bufferSize = 5, OverflowStrategy.fail)) {

        implicit builder =>
          chatSource =>

          // Flow used as input it takes Message's.
          val fromWebsocket = builder.add(
            Flow[Message].collect {
              case TextMessage.Strict(txt) => IncomingMessage(user, txt)
            })

          // Flow used as output, it returns Message's.
          val backToWebsocket = builder.add(
            Flow[ChatMessage].map {
              case ChatMessage(author, text) => TextMessage(s"[$author]: $text")
            }
          )

          // Send messages to the actor, if send also UserLeft(user) before stream completes.
          val chatActorSink = Sink.actorRef[ChatEvent](chatRoomActor, UserLeft(user))

          // Merges both pipes.
          val merge = builder.add(Merge[ChatEvent](2))

          // Materialized value of Actor who sit in chatroom.
          val actorAsSource = builder.materializedValue.map(actor => UserJoined(user, actor))

          // Message from websocket is converted into Incoming Message and should be send to each in room.
          fromWebsocket ~> merge.in(0)

          // If Source actor is just created should be send as UserJoined and registered as participant in room.
          actorAsSource ~> merge.in(1)

          // Merges both pipes above and forward messages to chatroom Represented by ChatRoomActor.
          merge ~> chatActorSink

          // Actor already sits in chatRoom so each message from room is used as source and pushed back into websocket.
          chatSource ~> backToWebsocket

          // Expose ports.
          FlowShape(fromWebsocket.in, backToWebsocket.out)
    })

  def sendMessage(message: ChatMessage): Unit = chatRoomActor ! message

}

object ChatRoom {
  def apply(roomId: Int)(implicit actorSystem: ActorSystem) = new ChatRoom(roomId, actorSystem)
}