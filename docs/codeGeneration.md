---
layout: default
title: Code Generation
---

Code Generation
===============

Why Build Time
--------------

Joist's code generation is well suited to build-time code generation because it:

* Uses relatively static meta-data, the database schema, so does not need to run continuously
* Uses non-code meta-data, the database schema, that has a small, non-zero gathering cost
* Changes the local database schema so should only happen at the developer's discretion

Why Not Run Time
----------------

Joist avoids runtime code generation because it:

* Delays generation until runtime which makes artifacts unavailable for compile-time type safety
* Makes debugging painful, stepping through dynamic proxies and CGLIB-generated classes
* Slows down startup time, sometimes considerably (see [performance](ormPerformance.html))
* Is more complex than generating plain Java source files

Approach
--------

* Joist uses [sourcegen](http://github.com/stephenh/joist/blob/master/util/src/main/joist/sourcegen/GClass.java), a Java code generation DSL.

  sourcegen provides an abstraction layer of `GClass`, `GMethod`, and `GField` objects for generating source code into an object tree instead of a raw template, mostly ignoring tedious issues like class-level formatting, organizing imports, etc.
  
  sourcegen also allows decomposition of the generation logic, as a `GClass` can be passed to different modules for each to decorate, instead of all the logic for a class being commingled in a single template.

* Joist generates meta-data that drives the ORM implementation at runtime, so it avoids generating low-level persistence code.

  This avoids complexities like directly generating SQL strings, but still provides a type-safe meta-data basis for the rest of the application and Joist implementation to leverage.

