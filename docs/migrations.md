---
layout: default
title: Migrations
---

Migrations
==========

Overview
--------

Schema changes are codified in Migration objects, much like Rails migrations.

Schema Versions
---------------

The schema version is tracked within the database itself, in the `schema_version` table. This table contains one row, with the `schema_version` column set to the current version, and the `update_lock` column set to `1` if an update is in progress.

The [Migrater](http://github.com/stephenh/joist/blob/master/domain/src/main/joist/domain/migrations/Migrater.java) class applies migrations to an existing database, starting at `m0000` if no `schema_version` table exists.

Example
-------

For example, to add a column to the `child` table, the migration looks like:

<pre name="code" class="java">
    public class m0002 extends AbstractMigration {
        public m0002() {
          super("Add child.nick_name column.");
        }
        public void apply() {
            addColumn("child", varchar("nick_name"));
        }
    }
</pre>

Run `ProjectCycle`, it will recreate the local database, apply the `0001`, `0002`, etc. migrations, and then regenerate the `XxxCodegen` classes.

The `ChildCodegen` class will now have the new `getNickName()`/`setNickName()` methods in it. Any business logic added to `Child` is not overwritten.

