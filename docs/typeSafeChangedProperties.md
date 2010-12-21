---
layout: default
title: Type-safe Changed Properties
---

Type-safe Changed Properties
============================

Overview
--------

Joist automatically tracks dirty and original values in domain objects.

Besides knowing what has changed, it also exposes them in a type-safe API.

For example, given a Domain Object `foo`, the API for checking whether `foo`'s name has changed is: `foo.getChanged().hasName()`.

The `getChanged()` method is used because:

* It is more type-safe than a String-based approach like `foo.hasChanged("name")`.
* It adds only one additional method (`getChanged()`) to `Foo`'s API, as opposed to cluttering the `Foo` API itself lots of extra, rarely-used `foo.hasNameChanged()`/`foo.getOriginalName()` methods for each one of `foo`'s fields.

Example
-------

<pre name="code" class="java">
    Parent p = Parent.queries.find(2);

    // Name was foo
    assertEquals("foo", p.getName());

    // Change it to bar
    p.setName("bar");

    // Seesthat name has changed
    assertTrue(p.getChanged().hasName());

    // Get the original name
    assertEquals("foo", p.getChanged().getOriginalName());
</pre>

