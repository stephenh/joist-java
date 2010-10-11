---
layout: default
title: joist-web Velocity
---

Velocity
========

Overview
--------

[Velocity](http://velocity.apache.org/) is used as joist-web's rendering engine. It allows a nice balance of control-driven and template-driven rendering.

Control Rendering
-----------------

Velocity 1.6 introduced a `Renderable` interface that allows variables in the Velocity model to override how they are included in the template.

joist-web's [`AbstractControl`](http://github.com/stephenh/joist/blob/master/web/src/main/joist/web/AbstractControl.java) implements this interface, wraps the Velocity's internals in a joist-web [`HtmlWriter`](http://github.com/stephenh/joist/blob/master/web/src/main/joist/web/util/HtmlWriter.java) and then delegates to the normal joist-web `Control.render` method.

This means that in the template:

    <some html>
    $control
    </some html>

Velocity will call `control.render(InternalContextAdapter,Writer)`, which will call `control.render(HtmlWriter)`, achieving streaming output of the control's content to Velocity's writer.

