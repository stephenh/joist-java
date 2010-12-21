---
layout: default
title: Validation Rules
---

Validation Rules
================

Overview
--------

Validation rules are domain-level rules executed on dirty domain objects prior to changes being sent to the database.

Implementation
--------------

Each domain object has a `List<ValidationRule>` [rules](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/domain/src/main/joist/domain/AbstractDomainObject.java#L15) that can be added or removed to as needed.

Code generation automatically adds some [basic](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/src/codegen/features/domain/ChildCodegen.java#L33) validation rules:

<pre name="code" class="java">
    public abstract class ChildCodegen ... {
        ...
        private void addExtraRules() {
            this.addRule(new NotNull&lt;Child&gt;(Shims.name));
            this.addRule(new MaxLength&lt;Child&gt;(Shims.name, 100));
        }
        ...
    }
</pre>

But custom/business validation rules are simple to add, either as one-off [inner classes](http://github.com/stephenh/joist/blob/aa200facb6f70cfd41282fb6153bad7521f31991/features/src/main/features/domain/ValidationAFoo.java#L19):

<pre name="code" class="java">
    public class ValidationAFoo {
        public ValidationAFoo() {
            this.addExtraRules();
        }

        private void addExtraRules() {
            this.addRule(new Rule&lt;ValidationAFoo&gt;() {
                public void validate(ValidationErrors errors, ValidationAFoo foo) {
                    if ("bar".equals(foo.getName())) {
                        errors.addPropertyError(foo, "name", "must not be bar");
                    }
                    if ("baz".equals(foo.getName())) {
                        errors.addObjectError(foo, "is all messed up");
                    }
                }
            });
        }
    }
</pre>

Or as higher-level, reusable rule, e.g.:

<pre name="code" class="java">
    private void addExtraRules() {
        this.addRule(new MustNotOverlap(Shims.children, ChildCodegen.Shims.begin, ChildCodegen.Shims.end)); // I think
    }
</pre>

(Note: This example was used on a previous-generation Joist-like framework to ensure that parent entities with collections of time-based entries could validate that none of the children overlapped each other and not copy/paste the boilerplate overlap code into each parent entity.)

Notes
-----

Validation rules might eventually use [bindgen](./bindgen.html)-generated bindings as bindings are stateful and so can navigate object graphs while shims are stateless and can only access single-level properties.



