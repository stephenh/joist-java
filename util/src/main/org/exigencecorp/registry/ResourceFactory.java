package org.exigencecorp.registry;

public interface ResourceFactory<T> {

    T create();

    void destroy(T resource);

}
