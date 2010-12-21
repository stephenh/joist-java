---
layout: default
title: Auto-Maintained Back Pointers
---

Auto-Maintained Back Pointers
=============================

Overview
--------

Joist automatically maintains both sides of foreign key relationships.

This means if you add a child to a parent, you don't have to worry about explicitly setting the parent on the child, for fear that later the child will call `getParent()` and get back null. Joist maintains both sides.

Examples
--------

Using `parent.addChild(c)` means `c.getParent()` will just work:

<pre name="code" class="java">
    Parent p = new Parent();
    Child c = new Child();
    p.addChild(c);
    assertTrue(c.getParent() == p); // passes
</pre>

And using `child.setParent(p)` means that `p.getChilds()` will just work:

<pre name="code" class="java">
    Parent p = new Parent();
    Child c = new Child();
    c.setParent(p);
    assertTrue(p.getChilds().contains(c)); // passes
</pre>

Lazy Loading
------------

Note that when calling `child.setParent(parent)`, and the implicit `parent.addChild(child)` is done by Joist for you, the parent's "other" children are not pulled back from the database.

The changes to the `parent.childs` collection are kept lazy until the `parent.getChilds()` method is explicitly called. Then the database is hit and any `parent.childs` added/removed items that had been kept lazy are applied to the results before being returned from `parent.getChilds()`.

