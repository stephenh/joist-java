package joist.registry;

public interface ResourceFactory<T> {

    T create();

    void destroy(T resource);

}
