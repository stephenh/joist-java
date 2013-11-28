
Joist is a ORM based on code generation.

The goal is to provide Rails-like "empty domain objects" in an ORM that is simple, pleasant to use, and, if needed, scales nicely to really large schemas.

See [joist.ws](http://joist.ws) for more information.

Building
========

From the command line:

    ./all.sh install

Note that `install` is required to, like multi-project Maven setups, install the jars from upstream projects into your local Ivy repository for the downstream projects to fetch.

Note: The tests for the features project will fail until you create a local database schema by running the `FeaturesCycle.launch` target in Eclipse. (There is not currently a Buildr target to do this.)



