---
layout: default
title: Domain Objects
---

Domain Objects
==============

Overview
--------

Domain objects are generated from the local database schema. This means domain objects always match the schema, much like the Rails/Fowler [ActiveRecord](http://www.martinfowler.com/eaaCatalog/activeRecord.html) pattern.

joist.domain uses the [Generation Gap](http://martinfowler.com/dslwip/) pattern to separate boilerplate getters/setters from business logic. For each table `foo`, the generated code created both a `Foo` and `FooCodegen` class. `Foo` is never touched again, so you can add domain logic without fear of it being overwritten.

Example
-------

[Child.java](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/src/main/features/domain/Child.java) is a clean-slate for any business logic:

<pre name="code" class="java">
    public class Child extends ChildCodegen {
    }
</pre>

While [ChildCodegen.java](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/src/codegen/features/domain/ChildCodegen.java) has the appropriate boilerplate getter/setter methods, for example:

<pre name="code" class="java">
    public abstract class ChildCodegen extends AbstractDomainObject {

        ...
        private Integer id = null;
        private String name = null;
        private Integer version = null;
        private ForeignKeyHolder&lt;Parent&gt; parent = new ForeignKeyHolder&lt;Parent&gt;(Parent.class);
        ...

        ... Validation rules are added based on the db constraints
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
