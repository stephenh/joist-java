
Intro
=====

An ORM with type-safe queries (no strings) and no runtime class generation (no CGLIB).

Instead, code generation creates base classes for your domain objects, e.g.:

    public class Child extends ChildCodegen {
    }

The `ChildCodegen` class has the appropriate `getName()`/`setName()`, `getParent()`/`setParent()` methods.

If you want to add a column to your `child` table, you use a Rails-like migration:

    public class m0002 extends AbstractMigration {
        public m0002() {
          super("Add child.nick_name column.");
        }
        public void apply() {
            addColumn("child", varchar("nick_name"));
        }
    }

Run `UpdateDatabase`, it will clear your database, apply the `0001`, `0002`, etc., migrations, and then recreate the `XxxCodegen` classes.

The `ChildCodegen` class will have the new `getNickName()`/`setNickName()` methods in it. Any business logic you've added to `Child` is not overwritten.

Also, code generation creates a `ChildAlias` class for making type-safe queries, e.g.:

    public Child findByName(String name) {
        ChildAlias c = new ChildAlias("c");
        return Select.from(c).where(c.name.equals(name)).unique();
    }

The `ChildAlias` class also has column meta-data and references to the `ChildCodegen.Shims` for hydrating/dehydrating `Child` objects with no runtime code generation and no reflection.

Examples
========

See [tests][1] and [queries][2] for examples of the syntax. [Base classes][3] and [aliases][4] are code generated.

See [CoolThings][7] for more examples.

[1]: master/Features/tests/features/domain/ChildTest.java
[2]: master/Features/src/main/features/domain/queries/ChildQueries.java
[3]: master/Features/src/codegen/features/domain/ChildCodegen.java
[4]: master/Features/src/codegen/features/domain/ChildAlias.java
[7]: master/Documentation/CoolThings.md

Why Another ORM?
================

I used Rails and ActiveRecord for awhile--the empty class definition, with getters/setters filled in by database metadata, made a lot of sense to me. Java doesn't allow this at runtime, but build-time code generation works even better.

The projects I worked on had a "reset the database schema" build step every time someone changes the schema, so running code generation after resetting the database seemed convenient and did not add an extra step.

Large projects I worked on (200+ tables) also suffered at startup time due to runtime code generation. This was especially painful when practicing TDD. It seems like a waste to consistently re-generate all of the persistence hooks at runtime when you can do it just once at build time. See [Performance][8] for the 23 seconds->1 second and 8 seconds->.35 seconds (depending on the machine) startup time improvement over Hibernate.

[8]: master/Documentation/Performance.md

Patterns
========

* Registry (needs work on application/infrastructure integration)
* Unit of Work
* Identity Map
* Data Mapper (with Metadata Mapping)
* Foreign Key Mapping (with automatically-maintained back pointer)
* Lazy Load (with value holder, e.g. no virtual proxies)
* Class Table Inheritance (no Single Table Inheritance or Concrete Table Inheritance)
* Optimistic Offline Lock (every object as a "version" column)
* Finders (though called [queries][2] as they can also do bulk updates/deletes)
* Transformer Generation (input model of database schema is transformed into a Java model)
* Generation Gap (generated code is kept separate from user-modified subclasses)

Todo
====

* Builders
* Project dependencies
* Composite columns (e.g. TimePoint with both time+zone), if needed
* Group by queries
* Convert rules to bindgen?
* Refactor Insert into Insert/InsertTemplate?

Caveats
=======

* Still a hobby project at this point--needs more use in the real world
* I'm actively screwing around with the domain objects framework, "Click2" UI framework, and bindgen framework, so I probably have the Eclipse `.classpath`/`.project` files messed up
* The `bd` build tool has promise but is a distraction--there is no guarantee (or chance, really) things will work/generate out of the box

Acknowledgements
================

* [JaQu][5] is where I stole the idea of pre-declaration of `XxxAlias` instances to build type-safe queries
* [PoEAA][6] is an awesome reference for enterprise software systems

[5]: http://h2database.com/html/jaqu.html
[6]: http://martinfowler.com/books.html#eaa

