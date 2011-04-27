---
layout: default
title: Eager Loading
---

Eager Loading
=============

Joist implements a simple eager loading algorithm that, in certain scenarios, can dramatically improve the performance of loading domains objects from the database.

N+1 Problem
-----------

For context, the situation this optimization is for arises when running code like:

    Blog blog = loadBlog(1); // load from somewhere
    for (Post post : blog.getPosts()) {
      for (Comment comment : post.getComments()) {
        render(comment);
      }
    }
{: class=brush:java}

Where, if you have 100 posts, you load all 100 posts in 1 query (the `blog.getPosts()` line), but then end up making 100 (N) separate queries for each post's comments.

Another similar form is:

    List<Post> posts = loadPosts(); // load from somewhere
    for (Post post : posts) {
      render(post.getAuthor());
    }
{: class=brush:java}

Where, again with 100 posts, we assume `loadPosts` gets those in 1 query, but each `post.getAuthor()` makes it's own query, leading to 100 (N) separate queries for each post's author.

This degradation is called the N+1 query problem (see this post on [ORM prefetching](http://www.draconianoverlord.com/2010/07/16/orm-prefetching.html) for more details) and often trips up ORM libraries that try to hide the database from the programmer.

Approach
--------

Joist avoids this problem by eagerly loading:

1. the children for all currently-loaded parents (one-to-many relationships), and
2. the parents for all currently-loaded children (many-to-one relationships).

This functionality is currently hard-coded on--you always get it. Soon it should be configurable on a table/relation/call basis.

### Eagerly Loading One-to-Many

In the first code snippet above, the initial `post.getComments()` call for `Post#1`'s comments actually returns the comments for all 100 posts in the current UnitOfWork and caches them.

The SQL looks like:

    SELECT c.*
    FROM comment c
    WHERE c.post_id IN (1, 2, 3, ..., 100);
{: class=brush:sql}

The next 99 subsequent `post.getComments()` calls for `Post#2`'s through `Post#100`'s comments then get their results from the `comments` eager cache instead of each one making yet another SQL call.

### Eagerly Loading Many-to-One

In the second code snippet above, the initial `post.getAuthor()` call for `Post#1`'s authors actually loads the authors for all 100 posts in the current UnitOfWork and caches them.

The SQL looks like:

    SELECT a.*
    FROM author a
    WHERE a.id IN (1, 2, 3, ..., 100);
{: class=brush:sql}

The next 99 subsequent `post.getAuthor()` calls for `Post#2`'s through `Post#100`'s authors then get their results from the UnitOfWork IdentityMap instead of each one making yet another SQL call.

Pros/Cons
---------

The best thing about this approach, of course, is that you avoid the `N+1` problem and, depending on the code being executed, can see some nice performance savings.

It also uses a very simple query (no outer joins or subselects), so should be easy and quick for the database to fulfill (assuming the `post_id` foreign key is indexed, of course).

However, the downside is that you risk fetching data you do not need. If you only call `post1.getComments()` and never `post2.getComments()`, you've wasted time and memory fetching post 2's comments from the database.

This is somewhat mitigated by the fact that Joist discourages mapping large collections in the first place. E.g. if you have an `Employer` that can have thousands of `Employee`s, you should disable the `employer.getEmployees()` method from being generated because it will encourage business logic trying to do too much in a single UnitOfWork.

With this consideration (that child collections should be of a sane size) it should not be too risky to eagerly fetch a few extra children that you will not need. Most of the overhead should be fixed in the latency of the JDBC wire call, so a few more rows/objects shouldn't hurt.

That being said, Joist should fairly soon allow configuration of this behavior so you can enable/disable it as it fits your application's needs.

