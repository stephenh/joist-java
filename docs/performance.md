---
layout: default
title: Performance
---

Performance
===========

Startup Time
------------

Joist was designed to start as quickly as possible, especially on large schemas that are typical of enterprise projects.

To achieve the fastest possible startup time, Joist avoids runtime bytecode generation (see [shims](./shims.html) and [aliases](./aliases.html)). So instead of re-generating potentially several hundred classes (for large schemas) with CGLIB or ASM on each startup, all of Joist's persistence hooks and meta-data are regular, static Java code that load quickly. 

For example, running 1 JUnit test to save an `Address` object on a ~200-table schema:

* Hibernate: 6.7 seconds
* Joist: 0.4 seconds

Joist starts 94% faster than Hibernate on this large, ~200-table schema.

Most of the remaining startup time, ~0.3 seconds worth, is the database connection/pool getting established, so it is unlikely to go away.

---

Notes:

* Reproducing: Unfortunately the tested schema is proprietary (yeah, this sucks--suggestions of alternative, openly-available schemas of similar size are appreciated).
* Hardware: Thinkpad T61p, 2.5Ghz, 4GB RAM, Windows Vista 64bit.

Insert/Update Time
------------------

The first test is just inserting 1 object-per-commit, X number-of-times:

<a href="http://github.com/stephenh/joist/raw/69c0b3fca7083118f632f938b13ac1aa2e3487aa/perf/reports/2009_04_19_08_38/testcase0.jpg"><img src="http://github.com/stephenh/joist/raw/69c0b3fca7083118f632f938b13ac1aa2e3487aa/perf/reports/2009_04_19_08_38/testcase0.jpg" style="width:40em;"/></a>

For this, Joist and Hibernate are pretty similar.

---

The next test is inserting X objects-per-commit:

<a href="http://github.com/stephenh/joist/raw/69c0b3fca7083118f632f938b13ac1aa2e3487aa/perf/reports/2009_04_19_08_38/testcase1.jpg"><img src="http://github.com/stephenh/joist/raw/69c0b3fca7083118f632f938b13ac1aa2e3487aa/perf/reports/2009_04_19_08_38/testcase1.jpg" style="width:40em;"/></a>

Joist is ~60-70% faster than Hibernate when bulk-inserting items.

(Technically Joist cheats here because, although both Joist and Hibernate use the PostgreSQL sequences for id generation, Joist grabs all of the new entity ids with one bulk SQL statement while Hibernate uses 1-query-per-entity.)

---

Finally, updating X objects-per-commit:

<a href="http://github.com/stephenh/joist/raw/69c0b3fca7083118f632f938b13ac1aa2e3487aa/perf/reports/2009_04_19_08_38/testcase2.jpg"><img src="http://github.com/stephenh/joist/raw/69c0b3fca7083118f632f938b13ac1aa2e3487aa/perf/reports/2009_04_19_08_38/testcase2.jpg" style="width:40em;"/></a>

Joist is ~30% faster than Hibernate when bulk-updating items.

---

Notes:

* Configuration: See the [JoistDriver](http://github.com/stephenh/joist/blob/master/perf/src/main/joist/perf/JoistDriver.java) and [HibernateDriver](http://github.com/stephenh/joist/blob/master/perf/src/main/joist/perf/HibernateDriver.java) classes
* Reproducing: See the `joist.perf` project, make sure to run `FeaturesCycle` to setup the `features` database, then run `ant run` to kick off [Japex](https://japex.dev.java.net/)
* Hardware: Thinkpad T61p, 2.5Ghz, 4GB RAM, Windows Vista 64bit.

