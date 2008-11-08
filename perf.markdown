
ParentsLotsTest
===============

Hibernate
---------

    2008-11-08 00:06:38,059 [main]  DEBUG features.domain.ParentsLotsTest - Took 1950ms
    2008-11-08 00:06:41,289 [main]  DEBUG features.domain.ParentsLotsTest - Took 3230ms
    2008-11-08 00:06:41,289 [main]  DEBUG features.domain.ParentsLotsTest - Took 5180ms

    2008-11-08 00:07:01,665 [main]  DEBUG features.domain.ParentsLotsTest - Took 1966ms
    2008-11-08 00:07:04,911 [main]  DEBUG features.domain.ParentsLotsTest - Took 3246ms
    2008-11-08 00:07:04,911 [main]  DEBUG features.domain.ParentsLotsTest - Took 5212ms

    6 seconds total

ORM
---

With batching:

    2008-11-08 00:10:38,229 [main]  DEBUG features.domain.ParentsLotsTest - Took 1888ms
    2008-11-08 00:10:40,741 [main]  DEBUG features.domain.ParentsLotsTest - Took 2512ms
    2008-11-08 00:10:40,741 [main]  DEBUG features.domain.ParentsLotsTest - Took 4400ms

    2008-11-08 00:11:12,895 [main]  DEBUG features.domain.ParentsLotsTest - Took 1810ms
    2008-11-08 00:11:15,501 [main]  DEBUG features.domain.ParentsLotsTest - Took 2606ms
    2008-11-08 00:11:15,501 [main]  DEBUG features.domain.ParentsLotsTest - Took 4416ms

    4.7 seconds total

With batching plus ids-at-once:

    2008-11-08 00:26:05,414 [main]  DEBUG features.domain.ParentsLotsTest - Took 1310ms
    2008-11-08 00:26:08,005 [main]  DEBUG features.domain.ParentsLotsTest - Took 2591ms
    2008-11-08 00:26:08,005 [main]  DEBUG features.domain.ParentsLotsTest - Took 3901ms

    2008-11-08 00:26:47,646 [main]  DEBUG features.domain.ParentsLotsTest - Took 1326ms
    2008-11-08 00:26:50,143 [main]  DEBUG features.domain.ParentsLotsTest - Took 2497ms
    2008-11-08 00:26:50,143 [main]  DEBUG features.domain.ParentsLotsTest - Took 3823ms

    4.1 seconds total

CBAS
====

Corporate enterprise project, ~450 tables, running 1 JUnit test to save a simple domain object:

* sh6: Thinkpad T60p, 2.0Ghz, 2GB RAM, Windows XP 32bit
  * Hibernate: 23.5 seconds (unplugged)
  * Hibernate: 10.1 seconds (plugged in)
  * ORM: 0.8 seconds (unplugged)
  * ORM: 0.4 seconds (plugged in)
* sh7: Thinkpad T61p, 2.5Ghz, 4GB RAM, Windows Vista 64bit
  * Hibernate: 8.7 seconds (plugged in)
  * Hibernate: 13.3 seconds (unplugged)
  * ORM: 0.35 seconds (plugged in)
  * ORM: 0.52 seconds (unplugged)

