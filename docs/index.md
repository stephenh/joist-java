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
      // put your business logic here, it won't get over written

      // getters/setters/collections are in the base class
    }
</pre>

Now you can write [type-safe queries](typeSafeQueries.html):

<pre name="code" class="java">
    public class EmployeeQueries {
      public List&lt;Employee&gt; findByName(String name) {
        EmployeeAlias e = new EmployeeAlias("e");
        return Select.from(e).where(e.name.eq(name)).list();
      }
    }
</pre>

The goal is a simple, "it just works" domain layer for enterprise-scale schemas.

To jump in, see [getting started](gettingStarted.html).

Screencast
----------

<p>
  <a href="http://joist.ws/casts/joist-3.flv" style="display:block;width:520px;height:330px;margin-left:1em;" id="player">&nbsp;</a>
  <script type="text/javascript"><!--
    flowplayer("player", "casts/flowplayer-3.2.8.swf", { clip: { autoPlay: false } });
  --></script>
</p>

Why Joist is Awesome
--------------------

* [Domain Objects](domainObjects.html) that are clean, simple, and "just work"
* [Type-Safe Queries](typeSafeQueries.html) that won't compile if your schema changes
* [Auto-Maintained Back Pointers](backPointers.html) so that parent/child collections are always in sync
* [Validation Rules](validationRules.html) for consistently enforcing business logic
* [Migrations](migrations.html) for maintaining your schema
* [Eager Loading](eagerLoading.html) to avoid the N+1 problem even in nested for loops
* [Performance](performance.html) that is as-fast-or-faster than Hibernate
* [Type-Safe Changed Properties](typeSafeChangedProperties.html)

Implementation Details:

* [Aliases](aliases.html) facilitate writing type-safe queries
* [Shims](shims.html) to map data instead of reflection or runtime-generated classes
* [Patterns](patterns.html) like Unit of Work, Identity Map, etc.
* [Code Generation](codeGeneration.html) to avoid boilerplate
* [Eclipse Tips](eclipseTips.html)

Blog posts:

* [Joist Builder Defaults](http://draconianoverlord.com/2012/03/29/joist-builder-defaults.html)
* [Joist, the Java ORM for Me](http://draconianoverlord.com/2012/03/21/joist-orm.html)
* [Joist Tip--Fast Database Resets](http://draconianoverlord.com/2010/08/22/joist-tip-fast-database-resets.html)
* [Joist vs. Hibernate SQL](http://draconianoverlord.com/2010/08/15/joist-vs-hibernate-sql.html)

Opinions
--------

Joist is tailored for projects that agree with its opinions:

* Enterprise teams are best served by as much type safety as possible
* Domain objects should match and be driven by the schema
* PostgreSQL (and MySQL in ANSI mode) are the supported databases
* Waiting for your ORM to start shouldn't kill your TDD loop

Community
---------

* Source code: [http://github.com/stephenh/joist](http://github.com/stephenh/joist)
* Forum: [https://groups.google.com/forum/#!forum/joist](https://groups.google.com/forum/#!forum/joist)

Acknowledgements
----------------

Joist borrows ideas mainly from [Rails][1] ([migrations](migrations.html) and database-reflected-[domain objects](domainObjects.html)) and [JaQu][4] ([type-safe SQL queries](typeSafeQueries.html)).

[1]: http://rubyonrails.org
[4]: http://www.h2database.com/html/jaqu.html


