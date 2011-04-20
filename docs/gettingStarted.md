---
layout: default
title: Getting Started
---

Getting Started
===============

To get started, download the joist-starter project (currently only in github).

From there, you can configure:

1. `build.properties` -- the administrator password for your local database (the username defaults to `root` for MySQL and `postgres` for Postgres).

2. `JoistCli.java` -- the database name and type (MySQL or Postgres).

3. `EmployeeTest.java` -- the database name and type.

Joist will create a user for the application's database. The user's name is `<database-name>_role` and the password is `<database-name>_role`. You can change these in the `JoistCli` class by setting the `user`/`password` fields of the `dbAppUserSettings` object.

Next, run the `starter-cycle` launch file with Eclipse, and this create (or recreate) the database, apply the migrations, and run the code generator.

...still flushing out.

Moving On
---------

From here, assuming you want to use joist in your application, you can:

* Rename the packages to fit your convention
* Change the source output folders to fit your convention
* Configure your automated build to run the `Cli JoistCli.cycle` command
* Configure your production deployments to run `Cli JoistCli.migrateDatabase` command



