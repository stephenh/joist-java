---
layout: default
title: joist-domain
---

joist-domain
============

Overview
--------

joist-domain is an ORM that focuses on performance, specifically startup time.

While startup time is of little concern in production, in TDD environments it is critical to maintaining productivity.

To achieve the fastest possible startup time, Joist uses code generation (see [shims](./ormShims.html) and [aliases](./ormAliases.html)) instead of the runtime bytecode generation. So, instead of re-generating potentially several hundred classes (on projects with large schemas) with CGLIB on each startup, all of joist-domain's persistence hooks and meta-data are regular, static Java code that loads quickly. 

Sections
--------

* [Domain Objects](ormDomainObjects.html)
* [Migrations](ormMigrations.html)
* [Validation Rules](ormValidationRules.html)
* [Type-Safe Queries](ormTypeSafeQueries.html)
* [Type-Safe Changed Properties](ormTypeSafeChangedProperties.html)
* [Auto-Maintained Back Pointers](ormBackPointers.html)
* [Aliases](ormAliases.html)
* [Shims](ormShims.html)
* [Patterns](ormPatterns.html)
* [Performance](ormPerformance.html)


