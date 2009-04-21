---
layout: default
title: Patterns
---

joist.orm Patterns
==================

Fowler's [PoEAA](http://martinfowler.com/books.html#eaa) provided a lot of guidance to drive joist via its patterns.

Specifically, with the links to their implementation in joist:

* Registry--coordinates app start/shutdown and resource management

* [Unit of Work](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/uow/UnitOfWork.java)--coordinates dirty object tracking and validation

* [Identity Map](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/orm/IdentityMap.java)--ensures entities are represented by only 1 instance-per-UoW

* [Data Mapper](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/orm/mappers/DomainObjectMapper.java) (with Meta-data Mapping)--loads data out of `ResultSets` and into `PreparedStatements` using meta-data in `Alias` classes generated at build-time

* [Foreign Key Mapping](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/orm/ForeignKeyListHolder.java) (with automatically-maintained back pointer)--loads lists of objects on demand (e.g. `parent.getChildren()`)

* [Lazy Load](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/orm/ForeignKeyHolder.java) (with value holder, e.g. no virtual proxies)--loads objects on demand (e.g. `child.getParent()`)

* Class Table Inheritance--allows modeling inheritance (no Single Table Inheritance or Concrete Table Inheritance)

* [Optimistic Offline Lock](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/orm/impl/InstanceUpdater.java#L53)--every object has a `version` column to ensure that stale data does not overwrite fresher data

* Finders (though called [queries][2] as they can also do bulk updates/deletes)

* [Transformer Generation](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/codegen/passes/GenerateDomainCodegenPass.java) (input model of database schema is transformed into a Java model)

* [Generation Gap](http://martinfowler.com/dslwip/) (generated code is kept separate from user-modified subclasses)

