---
layout: default
title: Joist
---

Joist
=====

Overview
--------

Joist is an ORM with type-safe queries (no strings) and no runtime class generation (no CGLIB).

You write [migrations](migrations.html) to modify your schema, and then Joist gives you clean [domain objects](domainObjects.html) free of boilerplate getters/setters/collections, which are all generated for you.

The goal is a simple, productive domain layer for enterprise-scale schemas.

To jump in, start at:

* [Getting Started](gettingStarted.html)

Why Joist is Awesome
--------------------

* [Domain Objects](domainObjects.html)
* [Type-Safe Queries](typeSafeQueries.html)
* [Auto-Maintained Back Pointers](backPointers.html)
* [Type-Safe Changed Properties](typeSafeChangedProperties.html)
* [Validation Rules](validationRules.html)
* [Migrations](migrations.html)
* [Eager Loading](eagerLoading.html)
* [Performance](performance.html)

Implementation Details
----------------------

* [Aliases](aliases.html)
* [Shims](shims.html)
* [Patterns](patterns.html)
* [Code Generation](codeGeneration.html)
* [Eclipse Tips](eclipseTips.html)

Opinions
--------

Joist is tailored for projects that agree with its opinions:

* Enterprise teams are best served by as much type safety as possible
* Domain objects should match and be driven by the schema
* PostgreSQL (and MySQL in ANSI mode) are the supported databases

Caveats
-------

* The type-safe SQL DSL currently only handles a subset of SQL queries--it is not a fully general substitute for relational algebra. When in doubt, you can drop down to straight SQL.

Source
------

Joist is hosted on github:

[http://github.com/stephenh/joist](http://github.com/stephenh/joist)

Acknowledgements
----------------

Joist borrows ideas mainly from [Rails][1] ([migrations](migrations.html) and database-reflected-[domain objects](domainObjects.html)) and [JaQu][4] ([type-safe SQL queries](typeSafeQueries.html)).

[1]: http://rubyonrails.org
[4]: http://www.h2database.com/html/jaqu.html


