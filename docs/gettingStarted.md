---
layout: default
title: Getting Started
---

Getting Started
===============

Overview
--------

Joist provides a bootstrap jar to provide a basic project structure. Out of the box, it provides Eclipse project files, a database environment, and unit, integration, and web testing infrastructure.

(Okay, web testing coming soon...it's done, but is not in the template project yet.)

Download
--------

Download the bootstrap jar:

* [http://repo.joist.ws/joist/bootstrap/0.1/jars/joist.bootstrap.jar](http://repo.joist.ws/joist/bootstrap/0.1/jars/joist.bootstrap.jar)

Run it with your project name:

* `java -jar joist.bootstrap.jar my_app`

Setup
-----

* Edit `build.properties` and set the `db.sa.password` to your local `postgres` user's password
* Edit `ivy.xml` and change the joist versions from `SNAPSHOT` to `0.1`--this is annoying, but please live with it for now
* Import the project into Eclipse--note there will be a few compile errors until the next time

Database Bootstrap
------------------

* From Eclipse, run `MyAppCycle`--this will create the `my_app` database and user in postgres, apply the two default migrations `m0000` and `m0001`, and run the code generation
* Hit Refresh and the compile errors should be resolved

Start Jetty
-----------

* From Eclipse, run `MyAppJetty`--this will start up Jetty on port 8080, you can start editing `index.htm` and `IndexPage`.

Running Tests
-------------

* See `tests/unit` and `tests/integration` for db-less and db-based, respectively, tests

