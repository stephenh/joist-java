---
layout: default
title: Bindgen Setup
---

Bindgen Setup
=============

Bindgen is an annotation processor that you configure the Java compiler to run during its compilation run.

`javac`
-------

If you're using Ant, or just `javac` in general, you can use something like:

<pre name="code" class="xml">
		&lt;javac destdir="bin/main" classpathref="main.classpath" debug="true" source="1.6" target="1.6"&gt;
			&lt;src path="src/main"/&gt;
			&lt;src path="src/codegen"/&gt;
			&lt;compilerarg value="-s"/&gt;
			&lt;compilerarg value="bin/apt-javac"/&gt;
			&lt;compilerarg value="-processor"/&gt;
			&lt;compilerarg value="org.exigencecorp.bindgen.processor.BindgenAnnotationProcessor"/&gt;
			&lt;compilerarg value="-Abindgen.skipExistingBindingCheck=true"/&gt; &lt;!-- for javac --&gt;
			&lt;compilerarg value="-XprintRounds"/&gt;
		&lt;/javac&gt;
</pre>

Briefly:

* `-s bin/apt-javac` will put the APT-generated code in that directory.
* `-processor org.exigencecorp.bindgen.processor.BindgenAnnotationProcessor` gives `javac` the class name of the bindgen processor to run
* `-Abinding.skipExistingBindingCheck=true` is a hack bindgen needs for `javac` that should go away, hopefully
* `-XprintRounds` outputs debug information about what round for annotation processing the compiler is currently on

Eclipse
-------

Eclipse annotation processing is configured with Project Properties:

For `Project Properties / Java Compiler`

* Ensure the "Compiler compliance level" is 1.6

For `Project Properties / Java Compiler / Annotation Processing`

* Check "Enable annotation processing"
* Check "Enable processing in editor"
* Optionally set "Generated source directory" to something like "bin/apt"

For `Project Properties / Java Compiler / Annotation Processing / Factory Path`

* Click "Add JARs" and select the `bindgen.jar`

I typically have Ivy download my dependencies into `bin/jars` and then have Eclipse reference it there, e.g. `/ProjectName/bin/lib/main/jars/bindgen.jar`.

Ivy Repository
--------------

There is an Ivy-compatible repo for Joist at: [http://repo.joist.ws/](http://repo.joist.ws/).

