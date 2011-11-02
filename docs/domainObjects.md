---
layout: default
title: Domain Objects
---

Domain Objects
==============

Overview
--------

Domain objects are generated from the local database schema. This means domain objects always match the schema, much like the Rails/Fowler [ActiveRecord](http://www.martinfowler.com/eaaCatalog/activeRecord.html) pattern.

Joist uses the Generation Gap pattern to separate boilerplate getters/setters from business logic in a separate base class that you never have to look at.

So, for each table `foo`, there are two files: `Foo.java` and `FooCodegen.java`. `Foo.java` is never touched again, so you can add domain logic without fear of it being overwritten, where as `FooCodegen.java` is overwritten with the latest schema information.

Example
-------

For a table `child`, you work with [Child.java](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/src/main/features/domain/Child.java), which is a clean-slate so you can focus solely on the business logic:

<pre name="code" class="java">
    public class Child extends ChildCodegen {
    }
</pre>

While the base class [ChildCodegen.java](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/src/codegen/features/domain/ChildCodegen.java) has the necessary boilerplate getter/setter methods, for example:

<pre name="code" class="java">
    public abstract class ChildCodegen extends AbstractDomainObject {

        ...
        private Integer id = null;
        private String name = null;
        private Integer version = null;
        private ForeignKeyHolder&lt;Parent&gt; parent = new ForeignKeyHolder&lt;Parent&gt;(Parent.class);
        ...

        // Validation rules are added based on the db constraints
        private void addExtraRules() {
            this.addRule(new NotNull&lt;Child&gt;(Shims.name));
            this.addRule(new MaxLength&lt;Child&gt;(Shims.name, 100));
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.getChanged().record("id", this.id, id);
            this.id = id;
            if (UoW.isOpen()) {
                UoW.getIdentityMap().store(this);
            }
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.getChanged().record("name", this.name, name);
            this.name = name;
        }

        ...
    }
</pre>
