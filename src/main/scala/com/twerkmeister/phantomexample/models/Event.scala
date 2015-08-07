package com.twerkmeister.phantomexample
package models

import org.joda.time.DateTime
import com.websudos.phantom.dsl._

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

case class Event(userId: String, date: DateTime, description: String)

class Events extends CassandraTable[Events, Event] {
  object userId extends StringColumn(this) with PartitionKey[String]
  object date extends DateTimeColumn(this) with PrimaryKey[DateTime] with ClusteringOrder[DateTime] with Ascending
  object eventDescription extends StringColumn(this)

  def fromRow(row: Row): Event = {
    Event(
      userId(row),
      date(row),
      eventDescription(row)
    )
  }
}

object Events extends Events with Connector {
  //Creating the Schema the first time this object is used
  Await.ready(Events.create.ifNotExists().future(), 3 seconds)

  def store(event: Event): Future[ResultSet] = {
    insert
      .value(_.userId, event.userId)
      .value(_.date, event.date)
      .value(_.eventDescription, event.description)
      .future()
  }

  def getForUser(userId: String) = {
    select.where(_.userId eqs userId).fetch()
  }

  def getForUserSince(userId: String, since: DateTime) = {
    select.where(_.userId eqs userId).and(_.date gte since).fetch()
  }
}