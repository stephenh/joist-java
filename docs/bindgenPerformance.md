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

Calling the expression `parent.name` 1,000,000 times gives the follow stats:

<pre>
        ognl cached:         1124535381ns
     click proputil:          313962668ns
        new binding:          238833707ns
   existing binding:           23681850ns
     static binding:           28652874ns
</pre>

So, in a tight loop like rendering a table, bindgen is 97.5% faster than OGNL.

The test code:

<pre name="code" class="java">
    public class BindgenPerfTest extends TestCase {

        private static final int loops = 1000000;

        public void testOgnlWithCachingByClickPropertyUtils() throws Exception {
            Parent p = new Parent("p");
            Child c = new Child(p, "c");

            Map&lt;String, String&gt; context = new HashMap&lt;String, String&gt;
            PropertyUtils.getValueOgnl(c, "parent.name", context); // Let it cache  
            long start = System.nanoTime();
            for (int i = 0; i &lt; BindgenPerfTest.loops; i++) {
                Assert.assertEquals("p", PropertyUtils.getValueOgnl(c, "parent.name", context));
            }
            this.stop("ognl cached", start);
        }

        public void testClickPropertyUtils() {
            Parent p = new Parent("p");
            Child c = new Child(p, "c");

            Map&lt; ?&gt; cache = new HashMap&lt;String, String&gt;
            PropertyUtils.getValue(c, "parent.name", cache); // Let it cache  
            long start = System.nanoTime();
            for (int i = 0; i &lt; BindgenPerfTest.loops; i++) {
                Assert.assertEquals("p", PropertyUtils.getValue(c, "parent.name", cache));
            }
            this.stop("click proputil", start);
        }

        public void testBindgenNewBindingEachTime() {
            Parent p = new Parent("p");
            Child c = new Child(p, "c");

            long start = System.nanoTime();
            for (int i = 0; i &lt; BindgenPerfTest.loops; i++) {
                Assert.assertEquals("p", new ChildBinding(c).parent().name().get());
            }
            this.stop("new binding", start);
        }

        public void testBindgenReuseExistingBinding() {
            Parent p = new Parent("p");
            Child c = new Child(p, "c");

            ChildBinding cb = new ChildBinding();
            Binding&lt;String&gt; b = cb.parent().name();
            long start = System.nanoTime();
            for (int i = 0; i &lt; BindgenPerfTest.loops; i++) {
                cb.set(c);
                Assert.assertEquals("p", b.get());
            }
            this.stop("existing binding", start);
        }

        public void testBindgenReuseStaticBinding() {
            Parent p = new Parent("p");
            Child c = new Child(p, "c");

            ChildBinding cb = new ChildBinding();
            BindingRoot&lt;Child, String&gt; b = cb.parent().name();
            long start = System.nanoTime();
            for (int i = 0; i &lt; BindgenPerfTest.loops; i++) {
                Assert.assertEquals("p", b.getWithRoot(c));
            }
            this.stop("static binding", start);
        }

        private void stop(String description, long start) {
            System.out.println(StringUtils.leftPad(description, 18) + ": " + StringUtils.leftPad((System.nanoTime() - start) + "ns", 20));
        }
    }
</pre>

This should be a reproducible Japex-driven test, but is not yet.


