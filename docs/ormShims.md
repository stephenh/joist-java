---
layout: default
title: Shims
---

Shims
=====

Overview
--------

Joist uses code-generated static inner classes, called "Shims", to access the fields of domain objects.

Each shim is an instance assigned to a `static final` field in `FooCodegen.Shims.fieldName`.

Because shims are instances, they can be put in lists and passed around as meta-data to the data mapper for meta-data-based mapping. This means that Joist's code generation does not have the complex/messy task of generating low-level SQL `SELECT`/`UPDATE` strings, but instead simply generates the meta-data that, at runtime, drives the meta data-based mapping.

Example
-------

For example:

<pre name="code" class="java">
    public abstract class FooCodegen {
      private String name;

      // public getters/setters
      public String getName() {
          return this.name;
      }
      public void setName(String name) {
          this.name = name;
      }

      // technically public, but "don't do that"
      public static class Shims {
        ...
        public static final Shim&lt;Child, java.lang.String&gt; name = new Shim&lt;Child, java.lang.String&gt;() {
          public void set(Child instance, java.lang.String name) {
            ((ChildCodegen) instance).name = name;
          }
          public String get(Child instance) {
            return ((ChildCodegen) instance).name;
          }
        };
        ...
      }
      ...
    }
</pre>

`FooCodegen.Shims.name` is then referenced in `FooAlias`'s list of columns for it to load/save from `Foo` when needed.

