---
layout: default
title: Bindgen
---

Bindgen
=======

Overview
--------

The separate [Bindgen](http://github.com/stephenh/bindgen) project is joist-web's type-safe alternative to expression languages like UL and OGNL. 

Bindgen uses code generation, but is built on top of the JDK6 annotation processor API to provide (in Eclipse) a seamless editing/generation experience. The generated code is kept up to date as soon as "save" is hit.

See [Code Generation](codeGeneration.html) for more discussion about the different types of code generation.

Also see the Bindgen's [github site](http://github.com/stephenh/bindgen) for more information.

Approach
--------

Bindgen generates closure-like classes that shadow classes annotated with `@Bindable` and provide type-safe instances of the [`Binding`](http://github.com/stephenh/bindgen/blob/4471fecafcaf6adb86044b661837c37278d269ca/bindgen/src/org/exigencecorp/bindgen/Binding.java) interface to allow frameworks to `get`/`set` properties' data.

Sections
--------

* [Examples](bindgenExamples.html)
* [Screencasts](bindgenScreencasts.html)
* [Performance](bindgenPerformance.html)
* [Setup](bindgenSetup.html)


