---
layout: default
title: Joist
---

Joist
=====

Joist is a simple, productive Java ORM with no boilerplate, type-safe queries, and no runtime class generation.

How it Works
------------

You write [migrations](migrations.html) to modify your schema:

<pre name="code" class="java">
    public class m0002 extends AbstractMigration {
      public m0002() {
        super("Add employee table.");
      }

      public void apply() {
        createTable("employee",
          primaryId("id"),
          varchar("name"),
          varchar("email").nullable(),
          integer("version"));
      }
    }
</pre>

Run `cycle` to update your database and get clean, getter/setter-free [domain objects](domainObjects.html):

<pre name="code" class="java">
    public class Employee extends EmployeeCodegen {
      // put your business logic here, it won't get over-written
    }
</pre>

Now you can write [type-safe queries](typeSafeQueries.html):

<pre name="code" class="java">
    public List&lt;Employee&gt; findByName(String name) {
      EmployeeAlias e = new EmployeeAlias("e");
      return Select.from(e).where(e.name.eq(name));
    }
</pre>

The goal is a simple, "it just works" domain layer for enterprise-scale schemas.

To jump in, see [getting started](gettingStarted.html).

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

Implementation Details:

* [Aliases](aliases.html) facilitate type-safe queries
* [Shims](shims.html) are generated instead of reflection
* [Patterns](patterns.html) like Unit of Work, Identity Map, etc.
* [Code Generation](codeGeneration.html)
* [Eclipse Tips](eclipseTips.html)

Opinions
--------

Joist is tailored for projects that agree with its opinions:

* Enterprise teams are best served by as much type safety as possible
* Domain objects should match and be driven by the schema
* PostgreSQL (and MySQL in ANSI mode) are the supported databases

Community
---------

* Source code: [http://github.com/stephenh/joist](http://github.com/stephenh/joist)
* Forum: [https://groups.google.com/forum/#!forum/joist](https://groups.google.com/forum/#!forum/joist)

Acknowledgements
----------------

Joist borrows ideas mainly from [Rails][1] ([migrations](migrations.html) and database-reflected-[domain objects](domainObjects.html)) and [JaQu][4] ([type-safe SQL queries](typeSafeQueries.html)).

[1]: http://rubyonrails.org
[4]: http://www.h2database.com/html/jaqu.html


