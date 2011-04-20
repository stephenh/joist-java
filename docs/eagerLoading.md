---
layout: default
title: Eager Loading
---

Eager Loading
=============

Joist implements a simple eager loading algorithm that, in certain scenarios, can dramatically improve the performance of loading domains objects from the database.

For context, the situation this optimization is for arises when running code like:

    Blog blog = loadBlog(1); // load from somewhere
    for (Post post : blog.getPosts()) {
      for (Comment comment : post.getComments()) {
        render(comment);
      }
    }
{: class=brush:java}

Where, if you have 100 posts, you load all 100 posts in 1 query (the `blog.getPosts()` line), but then end up making 100 separate queries for each post's comments.

This degradation is called the N+1 query problem (see this post on [ORM prefetching](http://www.draconianoverlord.com/2010/07/16/orm-prefetching.html) for more details) and often trips up ORM libraries that try to hide the database from the programmer.

Joist avoids this problem by eagerly loading the children for all currently parents.

For example, the first `post.getComments()` call for `Post#1` actually returns the comments for all currently-loaded posts, 1-100, and caches them. The 99 subsequent `post.getComments()` calls then get their results from the cache instead of making yet another SQL call.

This functionality is currently hard-coded on--you always get it. Pretty soon it should be configurable on a table/relation/call basis.

