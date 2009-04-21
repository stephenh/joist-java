---
layout: default
title: Bindgen Performance
---

Bindgen Performance
===================

Overview
--------

Given bindgen is just method calls, it is fast.

This was not an explicit design goal, but instead a benefit that comes from the primary goal of using type-safe, compile-checked constructs.

Stats
-----

Calling the expression `parent.name` 100,000 times gives the follow stats:

<pre>
       ognl cached: 169ms
    click proputil:  58ms
       new binding:  15ms
  existing binding:   3ms
</pre>

So, in a tight loop like rendering a table, bindgen is 98% faster than OGNL.

The test code:

<pre name="code" class="java">
	public void testOgnlWithCachingByClickPropertyUtils() throws Exception {
		Parent p = new Parent("p");
		Child c = new Child(p, "c");

		Map&lt;String, String&gt; context = new HashMap&lt;String, String&gt;();
		PropertyUtils.getValueOgnl(c, "parent.name", context); // Let it cache
		long start = System.currentTimeMillis();
		for (int i = 0; i &lt; loops; i++) {
			PropertyUtils.getValueOgnl(c, "parent.name", context);
		}
		stop("ognl cached", start);
	}

	public void testClickPropertyUtils() {
		Parent p = new Parent("p");
		Child c = new Child(p, "c");

		Map&lt;?, ?&gt; cache = new HashMap&lt;String, String&gt;();
		PropertyUtils.getValue(c, "parent.name", cache); // Let it cache
		long start = System.currentTimeMillis();
		for (int i = 0; i &lt; loops; i++) {
			PropertyUtils.getValue(c, "parent.name", cache);
		}
		stop("click proputil", start);
	}

	public void testBindgenNewBindingEachTime() {
		Parent p = new Parent("p");
		Child c = new Child(p, "c");

		long start = System.currentTimeMillis();
		for (int i = 0; i &lt; loops; i++) {
			new ChildBinding(c).parent().name().get();
		}
		stop("new binding", start);
	}

	public void testBindgenReuseExistingBinding() {
		Parent p = new Parent("p");
		Child c = new Child(p, "c");

		ChildBinding cb = new ChildBinding();
		Binding&lt;String&gt; b = cb.parent().name();
		long start = System.currentTimeMillis();
		for (int i = 0; i &lt; loops; i++) {
			cb.set(c);
			b.get();
		}
		stop("existing binding", start);
	}

	private void stop(String description, long start) {
		System.out.println(//
				StringUtils.leftPad(description, 18) + ": "
				+ StringUtils.leftPad((System.currentTimeMillis() - start) + "ms", 5));
	}
</pre>

This should be a reproducible Japex-driven test, but is not yet.


