package com.twerkmeister.phantomexample

import org.slf4j.LoggerFactory

import models.{Events, User}
import org.joda.time.DateTime
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Success

object PhantomExample extends Connector {
  val logger = LoggerFactory.getLogger(this.getClass.getName)

  val defaultUsers = List(User("111111", "Adam"), User("222222", "Michaela"))
  def main(args: Array[String]): Unit = {
    Future.traverse(EventStream(defaultUsers)) { event =>
      Events.store(event)
    }.flatMap { _ =>
      Events.getForUserSince(defaultUsers(1).id, DateTime.now().minusWeeks(1))
    }.andThen {
      case Success(events) =>
        logger.info(s"***** Fetched ${events.size} events for user 222222 for the last week *****")
        events.foreach{event => logger.info(event.toString)}
        logger.info("*************************************************************")
    }.andThen{
      case _ =>
        //shutting all phantom connections and stopping program execution
        session.getCluster.close()
    }
  }
}
