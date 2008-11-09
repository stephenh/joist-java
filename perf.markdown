
ParentsLotsTest
===============

The unit test:

    public void testLotsOfUpdateInOne() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Parent p = new Parent();
            p.setName("foo");
        }
        this.commitAndReOpen();
        long mid = System.currentTimeMillis();
        Log.debug("Took {}ms", (mid - start));
        for (int i = 0; i < 5000; i++) {
            Parent p = UoW.getCurrent().getRepository().load(Parent.class, 2 + i);
            p.setName("foo" + i);
        }
        this.commitAndReOpen();
        long end = System.currentTimeMillis();
        Log.debug("Took {}ms", (end - mid));
        Log.debug("Took {}ms", (end - start));
    }

Hibernate
---------

    2008-11-08 16:20:57,770 [main]  DEBUG features.domain.ParentsLotsTest - Insert took 2418ms
    2008-11-08 16:21:01,297 [main]  DEBUG features.domain.ParentsLotsTest - Update took 3542ms
    2008-11-08 16:21:07,226 [main]  DEBUG features.domain.ParentsLotsTest - Query took 5929ms
    2008-11-08 16:21:07,226 [main]  DEBUG features.domain.ParentsLotsTest - Took 11889ms

    2008-11-08 16:21:27,019 [main]  DEBUG features.domain.ParentsLotsTest - Insert took 2434ms
    2008-11-08 16:21:30,545 [main]  DEBUG features.domain.ParentsLotsTest - Update took 3526ms
    2008-11-08 16:21:36,459 [main]  DEBUG features.domain.ParentsLotsTest - Query took 5914ms
    2008-11-08 16:21:36,459 [main]  DEBUG features.domain.ParentsLotsTest - Took 11874ms
    12 seconds total

ORM
---

With batching:

    2008-11-08 00:10:38,229 [main]  DEBUG features.domain.ParentsLotsTest - Took 1888ms
    2008-11-08 00:10:40,741 [main]  DEBUG features.domain.ParentsLotsTest - Took 2512ms
    2008-11-08 00:10:40,741 [main]  DEBUG features.domain.ParentsLotsTest - Took 4400ms
    4.7 seconds total

    2008-11-08 00:11:12,895 [main]  DEBUG features.domain.ParentsLotsTest - Took 1810ms
    2008-11-08 00:11:15,501 [main]  DEBUG features.domain.ParentsLotsTest - Took 2606ms
    2008-11-08 00:11:15,501 [main]  DEBUG features.domain.ParentsLotsTest - Took 4416ms
    4.7 seconds total

With batching plus ids-at-once:

    2008-11-08 00:26:05,414 [main]  DEBUG features.domain.ParentsLotsTest - Took 1310ms
    2008-11-08 00:26:08,005 [main]  DEBUG features.domain.ParentsLotsTest - Took 2591ms
    2008-11-08 00:26:08,005 [main]  DEBUG features.domain.ParentsLotsTest - Took 3901ms
    4.1 seconds total

    2008-11-08 21:19:31,604 [main]  DEBUG features.domain.ParentsLotsTest - Insert took 1279ms
    2008-11-08 21:19:34,257 [main]  DEBUG features.domain.ParentsLotsTest - Update took 2653ms
    2008-11-08 21:19:40,217 [main]  DEBUG features.domain.ParentsLotsTest - Query took 5960ms
    2008-11-08 21:19:40,217 [main]  DEBUG features.domain.ParentsLotsTest - Took 9892ms
    10.2 seconds total

CBAS
====

Corporate enterprise project, ~450 tables, running 1 JUnit test to save a simple domain object:

* sh6: Thinkpad T60p, 2.0Ghz, 2GB RAM, Windows XP 32bit
  * Hibernate: 23.5 seconds (unplugged)
  * Hibernate: 10.1 seconds (plugged in)
  * ORM: 0.8 seconds (unplugged)
  * ORM: 0.4 seconds (plugged in)
* sh7: Thinkpad T61p, 2.5Ghz, 4GB RAM, Windows Vista 64bit
  * Hibernate: 13.3 seconds (unplugged)
  * Hibernate: 8.7 seconds (plugged in)
  * ORM: 0.52 seconds (unplugged)
  * ORM: 0.35 seconds (plugged in)

