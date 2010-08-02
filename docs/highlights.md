
Auto-Maintained Back Pointers
=============================

Both sides of foreign key relationships are automatically maintained. E.g.:

    Parent p = new Parent();
    Child c = new Child();
    p.addChild(c);
    assertTrue(c.getParent() == p); // passes

Or:

    Parent p = new Parent();
    Child c = new Child();
    c.setParent(p);
    assertTrue(p.getChilds().contains(c)); // passes

Note that the "other" children are not pulled back from the database until the `getChilds()` call--just doing `c.setParent` does not cause the parent's other children to immediately load.

Type-safe Changed Properties
============================

Dirty/original values are automatically tracked. E.g.:

    Parent p = Parent.queries.find(2);
    assertEquals("foo", p.getName());
    p.setName("bar");
    assertTrue(p.getChanged().hasName()); // passes
    assertEquals("foo", p.getChanged().getOriginalName()); // passes

Noted that the `getChanged()` method is only 1 additional method to the `Parent` interface (as opposed to cluttering the `Parent` API itself with the extra `hasNameChanged`/`getOriginalName` methods.

