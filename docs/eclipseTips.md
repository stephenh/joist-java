---
layout: default
title: Eclipse Tips
---

Eclipse Tips
============

Workspace Auto Refresh
----------------------

Setting `General > Workspace` checking `Refresh automatically` will have Eclipse automatically pick up the changes after you run code generation each time. Otherwise you'll have to manually tell Eclipse to refresh after running code generation.

Static Import Favorites
-----------------------

If you list `MigrationKeywords` in your Eclipse favorites setting, their static methods will show up in `Ctrl-Space` code completion:

`Java > Editor > Content Assist > Favorites` add:

    joist.migrations.MigrationKeywords

Now in migrations, typing `cr<Ctrl-Space>` will have `createTable` show up.

Static Import `*`
-----------------

Static import support is still kind of clunky in Eclipse, e.g. copy/pasting code that uses static imports from one class to another will not bring the static imports with it, creating compile errors that are annoying to resolve.

Setting `Java > Code Style > Organize Imports > Number of static imports needed for *` to `1` means that `MigrationKeywords.*` will be used as soon as you have a single static import keyword in use and makes the copy/paste experience nicer.

IBM SDK
-------

Surprisingly, the [IBM JDK](http://www.ibm.com/developerworks/java/jdk/) supports adding methods to classes while debugging. This capability helps avoids restarts (some, not all) and hence interruptions to workflow.

Sun JDKs do not support this--see [4910812](http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4910812) and vote for it.

To use the IBM JDK, download it from the "IBM Development Package for Eclipse" option, then change the Eclipse workspace preferences, `Window > Preferences > Java > Installed JREs`, add the `ibm_sdk60` and make it the default.

