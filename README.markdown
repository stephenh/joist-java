
Intro
=====

An ORM with type-safe queries (no strings) and no runtime class generation.

For example:

    public Child find(int id) {
        ChildAlias c = new ChildAlias("c");
        return Select.from(c).where(c.id.equals(id)).unique();
    }

Examples
========

See [tests][1] and [mappers][2] for examples of the syntax. [Base classes][3] and [aliases][4] are code generated.

[1]: master/Features/tests/features/domain/ChildTest.java
[2]: master/Features/src/main/features/domain/mappers/ChildMapper.java
[3]: master/Features/src/codegen/features/domain/ChildCodegen.java
[4]: master/Features/src/codegen/features/domain/mappers/ChildAlias.java

Patterns
========

* Registry (needs work on application/infrastructure integration)
* Unit of Work
* Identity Map
* Data Mapper (with Metadata Mapping)
* Foreign Key Mapping (with back pointer)
* Lazy Load (with value holder, e.g. no virtual proxies)
* Class Table Inheritance (no Single Table Inheritance or Concrete Table Inheritance)
* Optimistic Offline Lock (every object as a "version" column)

Todo
====

* Validation rules
* Differentiate mappers/finders, Finder registry/something

Acknowledgements
================

* [JaQu][5] is where I stole the idea of pre-declaration of `XxxAlias` instances to build type-safe queries
* [PoEAA][6] is an awesome reference for enterprise software systems

[5]: http://h2database.com/html/jaqu.html
[6]: http://martinfowler.com/books.html#eaa

