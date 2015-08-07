# simple-phantom-cassandra-example
This is a simple, but complete example on how to use the cassandra client library phantom. It contains the small pieces that I found missing in the available tutorials on phantom and cassandra, such as creating the schemas, enabling logging for phantom and finally tearing down the connection properly.

In this example a sequence of events is generated. Afterwards the events for a given time frame are fetched and printed.

# Explanation of the concepts
Websudos series on cassandra was a good start for me:
* http://blog.websudos.com/2014/08/16/a-series-on-cassandra-part-1-getting-rid-of-the-sql-mentality/
* http://blog.websudos.com/2014/08/23/a-series-on-cassandra-part-2-indexes-and-keys/
* http://blog.websudos.com/2014/10/29/a-series-on-cassandra-part-3-advanced-features/
* http://blog.websudos.com/2015/04/04/a-series-on-phantom-part-1-getting-started-with-phantom/

Also, checkout the official wiki:
* http://wiki.apache.org/cassandra/GettingStarted
