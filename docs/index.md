---
layout: default
title: Joist
---

Joist
=====

Overview
--------

Joist is an ORM with type-safe queries (no strings) and no runtime class generation (no CGLIB).

You write [migrations](migrations.html) to modify your schema, and then you get clean [domain objects](domainObjects.html) free of boilerplate getters/setters/collections, as those are generated for you.

The goal is a simple, productive domain layer for enterprise-scale schemas.

Performance
-----------

Joist focuses on startup time. While startup time is of little concern in production, in TDD environments it is critical to maintaining productivity.

To achieve the fastest possible startup time, Joist avoids runtime bytecode generation (see [shims](./shims.html) and [aliases](./aliases.html)). So instead of re-generating potentially several hundred classes (for large schemas) with CGLIB or ASM on each startup, all of Joist's persistence hooks and meta-data are regular, static Java code that load quickly. 

The obsession with startup time aside, Joist is also just all-around fast (see [performance](performance.html) and [eager loading](eagerLoading.html)).

Sections
--------

* [Getting Started](gettingStarted.html)
* [Domain Objects](domainObjects.html)
* [Migrations](migrations.html)
* [Validation Rules](validationRules.html)
* [Type-Safe Queries](typeSafeQueries.html)
* [Type-Safe Changed Properties](typeSafeChangedProperties.html)
* [Auto-Maintained Back Pointers](backPointers.html)
* [Eager Loading](eagerLoading.html)
* [Aliases](aliases.html)
* [Shims](shims.html)
* [Patterns](patterns.html)
* [Performance](performance.html)
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


