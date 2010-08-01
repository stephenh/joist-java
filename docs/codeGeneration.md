---
layout: default
title: Code Generation
---

Code Generation
===============

Overview
--------

Joist uses two types of code generation:

* [joist-domain](orm.html) uses "build time" code generation ran manually by the developer
* [joist-web](web.html) (via [bindgen](bindgen.html)) uses "design time" code generation ran automatically by the IDE

Why Build Time
--------------

joist-domain's code generation is well suited to build-time code generation because it:

* Uses relatively static meta-data, the database schema, so does not need to run continuously
* Uses non-code meta-data, the database schema, that has a small, non-zero gathering cost
* Changes the local database schema so should only happen at the developer's discretion

Why Design Time
---------------

joist-web's code generation is well suited to design-time code generation because it:

* Uses quickly changing meta-data, the source code, so needs to run continuously
* Uses codebase meta-data from the APT API, so has very little gathering cost

Why Not Run Time
----------------

Joist avoids runtime code generation because it:

* Delays generation until runtime which makes artifacts unavailable for compile-time type safety
* Makes debugging painful, stepping through dynamic proxies and CGLIB-generated classes
* Slows down startup time, sometimes considerably (see [performance](ormPerformance.html))
* Is more complex than generating plain Java source files

Approach
--------

* Both joist-domain and joist-web (via [bindgen](bindgen.html)) use joist-util's [sourcegen](http://github.com/stephenh/joist/blob/master/util/src/main/joist/sourcegen/GClass.java).

  sourcegen provides an abstraction layer of `GClass`, `GMethod`, and `GField` objects for generating source code into an object tree instead of a raw template, mostly ignoring tedious issues like class-level formatting, organizing imports, etc.
  
  sourcegen also allows decomposition of the generation logic, as a `GClass` can be passed to different modules for each to decorate, instead of all the logic for a class being commingled in a single template.

* joist-domain generates meta-data that drives the joist-domain ORM implementation at runtime, so it avoids generating low-level persistence code.

  This avoids complexities like directly generating SQL strings, but still provides a type-safe meta-data basis for the rest of the application and joist-domain implementation to leverage.

