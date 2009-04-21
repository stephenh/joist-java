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

Joist is tailored for projects that agree with its opinions:

* Enterprise teams are best served by a simple language and as much type safety as possible
* Domain objects should match and be driven by the schema
* Most HTML templates are boilerplate spaghetti code with no levels of abstraction
* PostgreSQL is the database (though a h2 branch exists with passing tests)

Caveats
-------

* Annotation processing (e.g. bindgen) works best in Eclipse running in a 1.6 JVM--this is difficult developers on Mac OSX until the 64-bit Eclipse 1.5 ships

Source
------

Joist is hosted on github:

[http://github.com/stephenh/joist](http://github.com/stephenh/joist)

Acknowledgements
----------------

Joist borrows ideas mainly from [Rails][1] ([migrations](ormMigrations.html), database-reflected-[domain objects](ormDomainObjects.html)), [Tapestry][2]/[Click][3] (component-based pages), and [JaQu][4] ([type-safe SQL queries](ormTypeSafeQueries.html)).

[1]: http://rubyonrails.org
[2]: http://tapestry.apache.org/
[3]: http://incubator.apache.org/click/
[4]: http://www.h2database.com/html/jaqu.html


