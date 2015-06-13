Todo
====

* Composite columns (e.g. TimePoint with both time+zone), if needed
* Don't muck with system properties
* Repo interfaces
  * Implement stub that copies values (iterates Alias, `toJdbcValue`, `ArrayList<Object>`)
  * Only one commit/flush at a time, serialized transaction isolation, leverage op locks
* Configuration option (global, per-collection) to disable collection ticking
  * ...maybe remove/solve annoyance of cross-collection stomp?
* Document PostgreSQL/MySQL no fsync settings for faster tests
