---
layout: default
title: Joist
---

Joist
=====

Overview
--------

Joist is a web application development stack. It consists of two sub-projects [joist-domain](orm.html), an ORM with type-safe queries (no strings) and no runtime class generation (no CGLIB), and [joist-web](web.html), a component-based web framework.

The goal is a simple, productive environment for enterprise-scale webapps.

Sections
--------

* [Screencasts](screencasts.html)
* [Getting Started](gettingStarted.html)
* [joist-domain](orm.html)
* [joist-web](web.html)
* [bindgen](bindgen.html)
* [Eclipse Tips](eclipseTips.html)
* [Code Generation](codeGeneration.html)

Opinions
--------

Joist is tailored for projects that agree with its opinions:

* Enterprise teams are best served by a simple language and as much type safety as possible
* Domain objects should match and be driven by the schema
* Most HTML templates are boilerplate spaghetti code with no levels of abstraction
* PostgreSQL is the database (though a h2 branch exists with passing tests)

Caveats
-------

* Annotation processing (e.g. bindgen) works best in Eclipse running in a 1.6 JVM.

  Just using a JDK5 JVM for running Eclipse itself with the JDK6 compiler option set is not enough--for APT to work, Eclipse needs its own JVM to have the JDK6-only annotation processing APIs.

  This is difficult developers on Mac OSX because Eclipse's SWT binaries are 32-bit but the Apple JDK6 is 64-bit. Mac OSX users will either have to find a 32-bit JDK6 to install or wait until the Eclipse 3.5 ships with 64-bit support. FWIW, the Eclipse 3.5 integration releases work well on win32.

* The type-safe SQL DSL currently only handles a simple subset of SQL queries. It needs flushed out by more real-world usage.

Source
------

Joist is hosted on github:

[http://github.com/stephenh/joist](http://github.com/stephenh/joist)

Community
---------

Joist is a new project and does not yet have an active community. If you're interested in getting involved or just being kept in the loop, feel free to email [stephen@exigencecorp.com](mailto:stephen@exigencecorp.com).

Acknowledgements
----------------

Joist borrows ideas mainly from [Rails][1] ([migrations](ormMigrations.html), database-reflected-[domain objects](ormDomainObjects.html)), [Tapestry][2]/[Click][3] (component-based pages), and [JaQu][4] ([type-safe SQL queries](ormTypeSafeQueries.html)).

[1]: http://rubyonrails.org
[2]: http://tapestry.apache.org/
[3]: http://incubator.apache.org/click/
[4]: http://www.h2database.com/html/jaqu.html


