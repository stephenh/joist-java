
Overview
========

Joist is a webapp development stack that consists of:

* joist-domain--an ORM with type-safe queries (no strings) and no runtime class generation (no CGLIB).
* joist-web--a page-, component-based web framework that leverages bindgen for type safe data binding

See [http://joist.ws](http://joist.ws) for more documentation.

Todo
====

* Composite columns (e.g. TimePoint with both time+zone), if needed
* Group by queries
* Execute/load hand-written SQL queries (with inheritance?)
* H2 prototyping (continue spike in h2 branch)
* Setup trac/something
* Eager loading (continue spike in eager branch)
* Ajax/JSON controls
* Establish license
* Handle type enums with helper methods, e.g. `isXxx`/`isYyyy`
* Don't muck with system properties
* Put db behavior in after uow
* Remove statics Repository/UoW/etc.
* Allow collection add/remove, delegate to addXxx/removeXxx
* Fix joist.util.Log to make its own LogRecord
* Have domain objects track the repo they are for (e.g. FooQueries takes a UoW instance, is in your given Registry, no static data source)
* Disconnect/reconnect, e.g. eagerly letting go of connection
* Add DO.hasUncommittedChanges to denote flushed .vs comitted
* Provide offset alternative due to perf

