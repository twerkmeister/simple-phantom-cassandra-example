package com.twerkmeister.phantomexample

import com.twerkmeister.phantomexample.models.{Event, User}
import org.joda.time.DateTime
import scala.util.Random
import scala.concurrent.duration._

object EventStream {

  private var time = DateTime.now().minusMonths(1)
  private val signedUp = scala.collection.mutable.Map[String, Boolean]().withDefaultValue(false)
  val events = Array("succeeded quiz", "failed quiz", "handed in assignment", "watched video")

  private def tick = {
    time = time.plusSeconds(Random.nextInt(24 * 60 * 60))
  }

  def pickUser(users: List[User]): User = {
    val index = Random.nextInt(users.size)
    users(index)
  }

  def generateRandomEvent(user: User): Event = {
    val eventDescription =
      if(signedUp(user.id)) {
        val index = Random.nextInt(events.size)
        events(index)
      }
      else{
        signedUp(user.id) = true
        "signup"
      }
    Event(user.id, time, eventDescription)
  }

  def apply(users: List[User]): Stream[Event] = {
    val user = pickUser(users)
    val event = generateRandomEvent(user)
    if(time.isBeforeNow) {
      tick
      event #:: apply(users)
    }
    else
      Stream.empty
  }
}
