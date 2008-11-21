
Intro
=====

An ORM with type-safe queries (no strings) and no runtime class generation.

For example:

    public Child findByName(String name) {
        ChildAlias c = new ChildAlias("c");
        return Select.from(c).where(c.name.equals(name)).unique();
    }

Examples
========

See [tests][1] and [queries][2] for examples of the syntax. [Base classes][3] and [aliases][4] are code generated.

See [CoolThings][7] for more examples.

[1]: master/Features/tests/features/domain/ChildTest.java
[2]: master/Features/src/main/features/domain/queries/ChildQueries.java
[3]: master/Features/src/codegen/features/domain/ChildCodegen.java
[4]: master/Features/src/codegen/features/domain/queries/ChildAlias.java
[7]: master/Documentation/CoolThings.md

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

Acknowledgements
================

* [JaQu][5] is where I stole the idea of pre-declaration of `XxxAlias` instances to build type-safe queries
* [PoEAA][6] is an awesome reference for enterprise software systems

[5]: http://h2database.com/html/jaqu.html
[6]: http://martinfowler.com/books.html#eaa

