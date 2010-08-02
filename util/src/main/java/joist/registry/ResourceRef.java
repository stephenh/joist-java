package joist.registry;

public interface ResourceRef<T> {

  T get();

  // I'd rather see this in ResourceRefHolder, or something like that...still unclear
  boolean isStarted();

}
