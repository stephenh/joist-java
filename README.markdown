
Joist is a ORM based on code generation.

The goal is to provide Rails-like "empty domain objects" in an ORM that is simple, pleasant to use, and, if needed, scales nicely to really large schemas.

See [joist.ws](http://joist.ws) for more information.

Building
========

From the command line:

    gradle install

Note that `install` is required to, like multi-project Maven setups, install the jars from upstream projects into your local Ivy repository for the downstream projects to fetch.

Note: The tests for the features project will fail until you create a local database schema by running the `FeaturesCycle.launch` target in Eclipse. (There is not currently a Buildr target to do this.)

Todo
====

* Composite columns (e.g. TimePoint with both time+zone), if needed
* Don't muck with system properties
* Repo interfaces
  * Implement stub that copies values (iterates Alias, `toJdbcValue`, `ArrayList<Object>`)
  * Only one commit/flush at a time, serialized transaction isolation, leverage op locks
* Configuration option (global, per-collection) to disable collection ticking
  * ...maybe remove/solve annoyance of cross-collection stomp?
* Document PostgreSQL/MySQL no fsync settings for faster tests

