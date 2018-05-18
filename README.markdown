
Joist is an ORM based on code generation.

The goal is to provide Rails-like "empty domain objects" in an ORM that is simple, pleasant to use, and scales nicely to really large schemas.

See [joist.ws](http://joist.ws) for more information.

Build against MySQL
===================

The Joist test suite requires running tests against a local database; to use MySQL for this:

* Edit `features/build.properties` (which is not checked in) and set your local MySQL password

  On a clean ~18.04 Ubuntu, see [this SO answer](https://stackoverflow.com/questions/33991228/what-is-the-default-root-pasword-for-mysql-5-7/50305285#50305285) to set your local `root` password.

* Run `./gradlew install`

Build against Postgres
======================

The Joist test suite requires running tests against a local database; to use Postgres for this:

* Edit `features/build-pg.properties` (which is not checked in) and set your local `postgres` user/admin password

  On a clean Ubuntu, Postgres's admin user/password is configured by:

  * `sudo -u postgres psql postgres` and then `\password postgres` to set your local admin password
  * In `postgresql.conf` ensure `listen_addresses` is set

* Edit `features/.../Registry.java` and change the `db` field to `Db.PG`
* Edit `features/.../JoistCli.java` and change the `db` field to `Db.PG`
* Run `./gradlew install`

Note that because of Postgres's ability to defer FK constraints, the `features/.../codegen` output will all change as the MySQL version is currently checked-in.

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

