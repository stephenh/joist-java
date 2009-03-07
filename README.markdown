
Conversion
==========

[ConverterRegistry][1] does simple, inheritance-aware type conversion.

For example, you can register a single `DomainObjectToStringConverter` converter that knows how to convert all your domain classes (instead a different converter for every domain class).

It doesn't do any fancy list/array stuff yet, nor have many default converters, but the core seems really solid.

Gen
===

A object-oriented, Java-specific source code generation library.

E.g.:

    GClass foo = new GClass("package.Foo");
    foo.getField("name").type(String.class).setPrivate();
    foo.toCode(); // returns nicely-formatted source code

See [GClassTest](master/tests/org/exigencecorp/gen/GClassTest.java) for more examples.

I've found OO-based code generation to be much simpler than template-based code generation. Your code focuses on making high-level `GClass`, `GMethod`, and `GField` objects and then hands off to gen to format and output the entire class.

For example, you can start using abstraction by making one `GClass` instance and passing it around to various modules that encapsulate their own logic, instead of having all of the logic in one large, say, Velocity template.

Note that any code inside of a method is up to you--there are no `GIf`, `GWhile`, etc. classes. That would be overkill, so you just get a [StringBuilderr](master/src/main/org/exigencecorp/util/StringBuilderr.java):

    String niceHello = "hi!";
    String rudeHello = "ugh";
    GMethod hello = gc.getMethod("sayHello").argument("boolean", "nicely");
    hello.body.line("if (nicely) {");
    hello.body.line("    return \"{}\";", niceHello);
    hello.body.line("} else {");
    hello.body.line("    return \"{}\";", rudeHello);
    hello.body.line("}");

Yeah, triple-quote, multi-line strings would kick-ass here, but this makes due--more than a handful of `body.line` calls would be a candidate for refactoring anyway. Anything with lots of `body.line` calls for a single method is likely too complex and is probably an example of why code generation has fallen out of favor.

Jdbc
====

[Jdbc](master/src/main/org/exigencecorp/jdbc/Jdbc.java) is a `SimpleJdbcTemplate`-like wrapper around JDBC--it removes the boilerplate `try/finally/close` stuff.

For example:

    int id = Jdbc.queryForInt(conn, "select next_id from code_id where table_name = '{}'", tableName);

(Granted, don't use this if `tableName` is user input--SQL injection.)

Or:

    // psuedo-code
    List<List<Object>> allParameters = ...
    allParameters.add(new List(1, 'foo'));
    allParameters.add(new List(2, 'bar'));
    // does a batch insert
    Jdbc.updateAll(conn, "INSERT INTO table (id, name) VALUES (?, ?)", allParameters);

`SimpleJdbcTemplate` is okay, but their methods currently only take `DataSources`, not existing `Connections`, which means you can't use them inside a transaction--each Jdbc method has both a `DataSource` and a `Connection` version so you can pass in whichever one you happen to have.

Hopefully you don't have to JDBC directly all that often, but when you do, these are the core methods I've found handy.

Registry
========

Work in progress--supposed to be a nice way to manage your application's singletons.

Util
====

* `Log.debug` reads your stack trace


