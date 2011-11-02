
Joist is a ORM based on code generation.

The goal is to provide Rails-like "empty domain objects" in an ORM that is simple, pleasant to use, and, if needed, scales nicely to really large schemas.

See [joist.ws](http://joist.ws) for more information.

Todo
====

* Composite columns (e.g. TimePoint with both time+zone), if needed
* Don't muck with system properties
* Allow child collections to be modifiable, delegate to `addXxx`/`removeXxx`

