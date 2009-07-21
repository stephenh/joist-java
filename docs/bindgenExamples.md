---
layout: default
title: Bindgen Examples
---

Bindgen Examples
================

Given a class `Foo`, annotated with `@Bindable`, Bindgen automatically generates `FooBinding` during the compile phase of `javac` or Eclipse. `FooBinding`'s can be constructed around an instance of `foo` and provide type-safe `Binding`s for `foo`'s properties.

Foo Example
-----------

For example, with class `Foo`:

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
    Foo foo = new Foo();
    FooBinding fooBinding = new FooBinding(foo);

    StringBinding nameBinding = fooBinding.name();
    nameBinding.set("bob"); // equivalent to foo.setName("bob");

    StringBinding zazBinding = fooBinding.bar().zaz();
    zazBinding.set("zaz"); // equivalent to foo.getBar().setZaz("zaz");
</pre>

Framework Example
-----------------

The benefit of `fooBinding.bar().zaz()` over `foo.getBar().setZaz()` is that we can pass `zazBinding` around like an UL/OGNL expression for frameworks to put data in/out of.

An example from the Bindgen [github site](http://github.com/stephenh/bindge) of pseudo-code for a potential framework (such as [joist.web](./web.html)):

<pre name="code" class="java">
    @Bindable
    public class HomePage extends AbstractPage {

        public Form form = new Form("Login");
        public Employee employee = null; // assigned by the framework

        @Override
        public void onInit() {
            // static import of BindKeyword.bind
            HomePageBinding b = bind(this);

            // read on render/set on post the employer's name
            this.form.add(new TextField(b.employee().employer().name()));

            // read on reader/set on post the employee's name
            this.form.add(new TextField(b.employee().name()));

            // call our submit method on POST
            this.form.add(new SubmitField(b.submit()));
        }

        public void submit() {
            // do stuff with this.username and this.password
        }
    }
</pre>

Stateless Example
-----------------

As of Bindgen 2.0, bindings can be stateless. This means you do not have to re-instantiate bindings for each "root" instance you want to evaluate the binding path against.

For example:

<pre name="code" class="java">
    // Store this in a map/static variable
    StringBindingPath&lt;Foo&gt; nameBinding = new FooBinding().name();

    // Later, with multiple foos:
    Assert.assertEquals("name1", nameBinding.getWithRoot(new Foo("name1")));
    Assert.assertEquals("name2", nameBinding.getWithRoot(new Foo("name2")));

    // Setting also works:
    Foo foo = new Foo("name");
    nameBinding.setWithRoot(foo, "name2");
</pre>

This example only showed a path with one level (`fooBinding.name()`), but stateless bindings work with arbitrarily deep paths (e.g. `fooBinding.bar().name()` for binding to the name of foo's bar).

Stateless bindings are also thread-safe--multiple threads can be calling `getWithRoot` on the same binding instance and they will not step on each other's toes.

