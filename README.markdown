
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
* H2 prototyping
* Eager loading
  * `UoW.load(Foo.class, prefetch(Foo.Child, Foo.Child.GrandChild)`
  * Use JDBC spy to assert correct SQL is being used
  * Eager threshold? If N parent objects:
    * First child fetch gets children for 1st parent
    * Second child fetch gets children for remaining parents
* Don't muck with system properties
* Remove statics Repository/UoW/etc.
  * Have domain objects track the repository they are for (e.g. `FooQueries` takes a `UoW` instance, is in your given Registry, no static data source)
* Allow collection add/remove, delegate to `addXxx`/`removeXxx`
* Fix `joist.util.Log` to make its own LogRecord
* Disconnect/reconnect, e.g. eagerly letting go of connection
* Add `DomainObject.hasUncommittedChanges` to denote flushed .vs committed
* Provide offset alternative due to performance
* Extract joist-web


