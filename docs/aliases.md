---
layout: default
title: Aliases
---

Aliases
=======

Overview
--------

Aliases are code-generated classes the hold the column meta-data for tables. This meta-data is then used for both building [Type-Safe Queries](typeSafeQueries.html) and also the meta-data-driven loading/saving of primitives into `ResultSet`s and `PreparedStatement`s.

Example
-------

This is all generated code, but the basic idea is that it statically defines the table meta-data found at build-time:

<pre name="code" class="java">
    public class ChildAlias extends Alias&lt;Child&gt; {

      // The alias keeps a list, columns, of all of its columns
      private final List&lt;AliasColumn&lt;Child, ?, ?&gt;&gt; columns =
        new ArrayList&lt;AliasColumn&lt;Child, ?, ?&gt;&gt;();

      // Some basic primitive columns
      public final IdAliasColumn&lt;Child&gt; id =
        new IdAliasColumn&lt;Child&gt;(this, "id", ChildCodegen.Shims.id);
      public final StringAliasColumn&lt;Child&gt; name =
        new StringAliasColumn&lt;Child&gt;(this, "name", ChildCodegen.Shims.name);
      public final IntAliasColumn&lt;Child&gt; version =
        new IntAliasColumn&lt;Child&gt;(this, "version", ChildCodegen.Shims.version);

      // The parent_id foreign key has a specific ForeignKeyAliasColumn
      // column with the "on" join method
      public final ForeignKeyAliasColumn&lt;Child, Parent&gt; parent =
        new ForeignKeyAliasColumn&lt;Child, Parent&gt;(
          this,
          "parent_id",
          ChildCodegen.Shims.parentId);

      public ChildAlias(String alias) {
        super(Child.class, "child", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
        this.columns.add(this.parent);
      }

      // Common Alias columns used by the meta-data mapper
      public List&lt;AliasColumn&lt;Child, ?, ?&gt;&gt; getColumns() {
        return this.columns;
      }

      public IdAliasColumn&lt;Child&gt; getIdColumn() {
        return this.id;
      }

      public IntAliasColumn&lt;Child&gt; getVersionColumn() {
        return this.version;
      }

      public IdAliasColumn&lt;Child&gt; getSubClassIdColumn() {
        return null;
      }

      // To avoid foreign key conflicts (only for mysql), entities
      // are topographically sorted at build-time and given a
      // hardcoded insert order to be used during UoW.flush
      public int getOrder() {
        return 21;
      }
    }
</pre>

