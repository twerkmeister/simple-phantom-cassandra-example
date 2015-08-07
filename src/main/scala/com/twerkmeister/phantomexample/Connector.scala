package com.twerkmeister.phantomexample

import com.websudos.phantom.connectors.{ContactPoints, KeySpace}

/*
  This section is originally from http://blog.websudos.com/2015/04/04/a-series-on-phantom-part-1-getting-started-with-phantom/
 */

trait Keyspace {
  implicit val space: KeySpace = new KeySpace("phantom_cassandra_example")
}

object Defaults extends Keyspace {
  val hosts = Seq("127.0.0.1")
  val Connector = ContactPoints(hosts).keySpace(space.name)
}

trait Connector extends Defaults.Connector.Connector with Keyspace
