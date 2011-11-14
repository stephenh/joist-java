---
layout: default
title: Patterns
---

Patterns
========

Forgive indulging in a bit of pattern obsession, but Joist uses several concepts and terminology from Fowler's [PoEAA](http://martinfowler.com/books.html#eaa) to drive its features.

Specifically, with the links to their implementations:

* [Unit of Work](https://github.com/stephenh/joist/blob/master/domain/src/main/java/joist/domain/uow/UnitOfWork.java)--coordinates dirty object tracking and validation

* [Identity Map](http://github.com/stephenh/joist/blob/master/domain/src/main/java/joist/domain/orm/IdentityMap.java)--ensures entities are represented by only 1 instance-per-UoW

* [Data Mapper](http://github.com/stephenh/joist/blob/master/domain/src/main/java/joist/domain/orm/mappers/DomainObjectMapper.java) (with Meta-data Mapping)--loads data out of `ResultSets` and into `PreparedStatements` using meta-data in `Alias` classes generated at build-time

* [Foreign Key Mapping](http://github.com/stephenh/joist/blob/master/domain/src/main/java/joist/domain/orm/ForeignKeyListHolder.java) (with automatically-maintained back pointer)--loads lists of objects on demand (e.g. `parent.getChildren()`)

* [Lazy Load](http://github.com/stephenh/joist/blob/master/domain/src/main/java/joist/domain/orm/ForeignKeyHolder.java) (with value holder, e.g. no virtual proxies)--loads objects on demand (e.g. `child.getParent()`)

* Class Table Inheritance--allows modeling inheritance (Joist doesn't support Single Table Inheritance or Concrete Table Inheritance)

* [Optimistic Offline Lock](http://github.com/stephenh/joist/blob/master/domain/src/main/java/joist/domain/orm/impl/InstanceUpdater.java#L53)--every object has a `version` column to ensure that stale data does not overwrite fresher data

* Finders--though called [queries][2] as they can also do bulk updates/deletes

* [Transformer Generation](http://github.com/stephenh/joist/blob/master/migrations/src/main/java/joist/codegen/passes/GenerateDomainCodegenPass.java)--input model of database schema is transformed into a Java model

* [Generation Gap](http://github.com/stephenh/joist/blob/master/migrations/src/main/java/joist/codegen/passes/GenerateDomainClassIfNotExistsPass.java)--user-modifiable class is output just once with boilerplate in the superclass


