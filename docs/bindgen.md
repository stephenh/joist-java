---
layout: default
title: Bindgen
---

Bindgen
=======

Overview
--------

The separate [Bindgen](http://github.com/stephenh/bindgen) project is joist.web's type-safe alternative to expression languages like UL and OGNL. 

Bindgen uses code generation, but is built on top of the JDK6 annotation processor API to provide (in Eclipse) a seamless editing/generation experience. The generated code is kept up to date as soon as "save" is hit.

See [Code Generation](codeGeneration.html) for more discussion about the different types of code generation.

Approach
--------

Bindgen generates closure-like classes that shadow classes annotated with `@Bindable` and provide type-safe instances of the [`Binding`](http://github.com/stephenh/bindgen/blob/4471fecafcaf6adb86044b661837c37278d269ca/bindgen/src/org/exigencecorp/bindgen/Binding.java) interface to allow frameworks to `get`/`set` properties' data.

Example
-------

Given a class `Foo`, annotated with `@Bindable`, Bindgen automatically generates `FooBinding` during the compile phase of `javac` or Eclipse. `FooBinding`'s can be constructed around an instance of `foo` and provide type-safe `Binding`s for `foo`'s properties.

For example, `Foo`:

<pre name="code" class="java">
    @Bindable
    public class Foo {
        public String name;
        public Bar bar;
    }

    public class Bar {
        public String zaz;
    }
</pre>

Bindgen generates a class `FooBinding` that you can use like:

<pre name="code" class="java">
    FooBinding fooBinding = new FooBinding(new Foo());

    StringBinding nameBinding = fooBinding.name();
    nameBinding.set("bob"); // equivalent to foo.setName("bob");

    StringBinding zazBinding = fooBinding.bar().zaz();
    zazBinding.set("zaz"); // equivalent to foo.getBar().setZaz("zaz");
</pre>

The benefit of `fooBinding.bar().zaz()` over `foo.getBar().setZaz()` is that we can pass `zazBinding` around like an UL/OGNL expression for frameworks to put data in/out of.

See the [Bindgen](http://github.com/stephenh/bindgen) docs for more information.

Sections
--------

* [Screencasts](screencasts.html)
* [Performance](bindgenPerformance.html)

