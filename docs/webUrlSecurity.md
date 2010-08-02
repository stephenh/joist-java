---
layout: default
title: joist-web
---

Url Security
============

Overview
--------

joist-web auto-binds request parameters to a `Page`'s public fields.

This is a convenient way to access request parameters, but also a potential security hole, e.g. if the user starts changing `creditCard.htm?card=1` to `creditCard.htm?card=10` just to see what will happen.

Approach
--------

joist-web's [`Page`](http://github.com/stephenh/joist/blob/master/web/src/main/joist/web/Page.java) interface has a method hook, `isAllowedViaUrl(Object)` to address this issue.

Out of the box, any `Object` is allowed to be bound from the URL--`isAllowedViaUrl` always returns `true`.

However, in your own `AbstractMyAppPage`, the following idiom is recommended:

<pre name="code" class="java">
    @Override
    public final boolean isAllowedViaUrl(Object converted) {
        if (converted instanceof DomainObject) {
            if (!this.isAllowedViaUrl((DomainObject) converted)) {
                this.messages.addError("You do not have access to that page");
                redirect(LoginPage.class);
            }
            return true;
        } else {
            return true; // non-domain objects are okay
        }
    }

    public boolean isAllowedViaUrl(DomainObject instance) {
        return false;
    }
</pre>

This makes `isAllowedViaUrl(Object)` final and, instead of just returning `true`, says that any `DomainObject` must now be explicitly allowed to be bound.

So, if a developer does nothing, `creditCard.htm?card=1` will do nothing--the field `CreditCard.card` is a `DomainObject` and, without any other action, won't be bound.

Instead, the `CreditCardPage` needs to override the `isAllowedViaUrl(DomainObject)` method with something like:

<pre name="code" class="java">
    public boolean isAllowedViaUrl(DomainObject instance) {
        return instance instanceof CreditCard &amp;&amp; ((CreditCard) instance).getUser().equals(this.getCurrentUser());
    }
</pre>

Where `User` is one of your domain objects and `getCurrentUser()` is a helper method that returns the logged-in user.

This will allow the user to view their, and only their, credit cards on the `creditCard.htm` page.

If checks like this become boilerplate, they could be put in a base class or otherwise abstracted.

