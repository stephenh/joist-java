---
layout: default
title: Eclipse Tips
---

Eclipse Tips
============

Static Import Favorites
-----------------------

If you list `MigrationKeywords` and `WebKeywords` as favorites, their static methods will show up in `Ctrl-Space` code completion:

`Java > Editor > Content Assist > Favorites` add:

    joist.domain.migrations.MigrationKeywords
    joist.web.WebKeywords

Now in migrations, typing `cr<Ctrl-Space>` will have `createTable` show up.

Static Import `*`
-----------------

Static import support is still kind of clunky in Eclipse, e.g. copy/pasting code that uses static imports from one class to another will not bring the static imports with it, creating compile errors that are annoying to resolve.

Setting `Java > Code Style > Organize Imports > Number of static imports needed for *` to `1` means that `MigrationKeywords.*` and `WebKeywords.*` will be used as soon as you have a single static import keyword in use and makes the copy/paste experience nicer.

