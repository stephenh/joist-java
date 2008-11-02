
Intro
=====

An ORM with type-safe queries (no strings) and no runtime class generation.

For example:

    public Child find(int id) {
        ChildAlias c = new ChildAlias("c");
        return Select.from(c).where(c.id.equals(id)).unique();
    }

Patterns
========

* Unit of Work
* Data Mapper and Metadata Mapping
* Foreign Key Mapping with back pointer

Examples
========

See [tests][1] and [mappers][2] for examples of the syntax. [Base classes][3] and [aliases][4] are code generated.

[1]: master/Features/tests/features/domain/ChildTest.java
[2]: master/Features/src/main/features/domain/mappers/ChildMapper.java
[3]: master/Features/src/codegen/features/domain/ChildCodegen.java
[4]: master/Features/src/codegen/features/domain/mappers/ChildAlias.java

Todo
====

* Percolation
* Validation rules

Acknowledgements
================

* [JaQu][5] is where I stole the idea of pre-declaration of `XxxAlias` instances to build type-safe queries
* [PoEAA][6] is an awesome reference for enterprise software systems

[5]: http://h2database.com/html/jaqu.html
[6]: http://martinfowler.com/books.html#eaa

