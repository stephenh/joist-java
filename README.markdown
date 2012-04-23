
Joist is a ORM based on code generation.

The goal is to provide Rails-like "empty domain objects" in an ORM that is simple, pleasant to use, and, if needed, scales nicely to really large schemas.

See [joist.ws](http://joist.ws) for more information.

Todo
====

* Composite columns (e.g. TimePoint with both time+zone), if needed
* Don't muck with system properties
* Pre-load a UoW and then spawn multiple copies of it (e.g. like a 2nd-level cache, but for the specific set of objects preloaded (e.g. common objects that would otherwise be loaded for every loop), but not an always-on on of thing), e.g.

      UoWSnapshot s1 = UoW.go { loadCommonObjects() }
      for (thing : lotsOfThings) {
        UoW.go(s1, {
          // load new things, get common objects for free
        })
      }

  How to handle changes to snapshotted objects? Write-back? What about a cached snapshotted parent, having a child added, which ticks its version? Next iteration fails the op lock

* Repo interfaces
  * Implement stub that copies values (iterates Alias, `toJdbcValue`, `ArrayList<Object>`)
  * Only one commit/flush at a time, serialized transaction isolation, leverage op locks

* Configuration option (global, per-collection) to disable collection ticking
  * ...maybe remove/solve annoyance of cross-collection stomp?

