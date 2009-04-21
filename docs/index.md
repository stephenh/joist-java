---
layout: default
title: Joist
---

Joist
=====

Overview
--------

Joist is a web application development stack. It consists of [joist.orm](orm.html), an ORM with type-safe queries (no strings) and no runtime class generation (no CGLIB), and [joist.web](web.html), a component-based web framework.

The goal is a simple, productive environment for enterprise-scale webapps.

Sections
--------

* [Getting Started](gettingStarted.html)
* [joist.orm](orm.html)
* [joist.web](web.html)
* [bindgen](bindgen.html)
* [Eclipse Tips](eclipseTips.html)

Opinions
--------

Joist is opinionated. Currently these opinions mean:

* Large, enterprise teams are best served by a simple/known language (Java) and as much type safety as possible
* Domain objects exactly match schema (so best for greenfield projects with schema control)
* PostgreSQL is the only supported database (though there is an H2 branch with tests passing)
* Annotation processing (e.g. bindgen) works best in Eclipse running in a 1.6 JVM (this is difficult for Mac OSX until 64-bit Eclipse 1.5 ships)

Acknowledgements
----------------

Joist borrows ideas mainly from [Rails][1] ([migrations](ormMigrations.html), database-reflected-[domain objects](ormDomainObjects.html)), [Tapestry][2]/[Click][3] (component-based pages), and [JaQu][4] ([type-safe SQL queries](ormTypeSafeQueries.html)).

[1]: http://rubyonrails.org
[2]: http://tapestry.apache.org/
[3]: http://incubator.apache.org/click/
[4]: http://www.h2database.com/html/jaqu.html


