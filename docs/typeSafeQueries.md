---
layout: default
title: Type-Safe Queries
---

Type-Safe Queries
=================

Joist uses the schema information available at build-time to generate [Alias](aliases.html) classes that form a kind of type-safe DSL for SQL queries.

Example
-------

For a table `child`, code generation will make an empty `ChildQueries` class. As bulk SQL operations are required, they can be added to `ChildQueries` like `findByName`:

<pre name="code" class="java">
    public Child findByName(String name) {
      ChildAlias c = new ChildAlias("c");
      return Select.from(c).where(c.name.equals(name)).unique();
    }
</pre>

The declared `ChildAlias c` variable presents the `FROM child c` "alias" portion of a SQL query.

Using `c`, you can type-safely reference `c.name` (a `StringAliasColumn` in the generated `ChildAlias` classes) for building the query's `where` or `order by` clauses.

For an example test case, see the [ChildQueryTest](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/tests/features/domain/queries/ChildQueryTest.java), some of which is included here:

<pre name="code" class="java">
    public void testFindForParentNameSql() {
      // SELECT * FROM child c
      // INNER JOIN parent p ON c.parent_id = p.id
      // WHERE p.name = 'bob'
      // ORDER BY p.name, c.name

      ChildAlias c = new ChildAlias("c");
      ParentAlias p = new ParentAlias("p");

      Select&lt;Child&gt; q = Select.from(c);
      q.join(p.on(c.parent));
      q.where(p.name.equals("bob"));
      q.orderBy(p.name.asc(), c.name.asc());

      Assert.assertEquals(Join.lines(
        "SELECT c.id, c.name, c.version, c.parent_id",
        " FROM \"child\" c",
        " INNER JOIN \"parent\" p ON c.parent_id = p.id",
        " WHERE p.name = ?",
        " ORDER BY p.name, c.name"), q.toSql());
      Assert.assertEquals(Copy.list("bob"), q.getWhere().getParameters());
    }
</pre>

Caveat
------

The type-safe SQL DSL currently only handles a subset of SQL queries--it is not a fully general substitute for relational algebra. When in doubt, you can drop down to straight SQL.

