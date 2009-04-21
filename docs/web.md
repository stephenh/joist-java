---
layout: default
title: joist.web
---

joist.web
=========

Overview
--------

joist.web is a component-based web framework based heavily on [Click](http://incubator.apache.org/click)'s approach of component-generated HTML. Its most unique feature is using [bindgen](bindgen.html) to handle data binding.

Its strength is enterprise data-entry apps that have hundreds of pages of repeated forms, tables, forms, tables. joist.web can implement these in succinct, type-safe Java definitions and avoid lines and lines of repetitive template code.

Currently joist.web's weakness is doing nifty Ajax stuff. This is on the todo list, but waiting for a good problem to drive its solution.

Example
-------

This is the code-behind `Page` class for `foo.htm`, a data-entry page for `Foo` objects:

<pre name="code" class="java">
    @Bindable
    public class FooPage extends AbstractProjectPage {

        // When hitting foo.htm?foo=1, Foo with id=1 is put into this field
        public Foo foo;
        public Form form = new Form("form");

        @Override
        public void onInitt() {
            if (this.foo == null) {
                // They hit the "new" button instead of the "edit" button
                this.foo = new Foo();
            }

            // Define the form using the bindgen-generated bindings
            FooPageBinding b = new FooPageBinding(this);
            this.form.add(new TextArea(b.foo().name()));
            this.form.add(new TextArea(b.foo().description()));
            this.form.add(new SubmitButton(b.save()));
        }

        // In AbstractProjectPage, DomainObjects are not allowed to be set into any public field
        // unless this method explicitly allows them--prevents users from screwing with URLs
        @Override
        public boolean isAllowedViaUrl(DomainObject instance) {
            return instance instanceof Foo &amp;&amp; ((Foo) instance).getUser().equals(this.currentUser.get());
        }

        // Called when the user hit's submit
        public void save() {
            if (this.commit()) {
                this.messages.addInfo("Successfully saved {}", this.foo);
                redirect(EntriesPage.class);
            }
        }
    }
</pre>

And now the `foo.htm`, which is a Velocity template:

<pre name="code">
    $form
</pre>

Across lots of pages, the succinctness of the `foo.htm` page and the type-safety of the bindgen-based `Form` definition should be a boon to productivity.

