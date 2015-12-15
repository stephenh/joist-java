package joist.domain;

import java.util.Map;

public interface Changed {

  void clear();

  int size();

  boolean contains(String propertyName);

  Object getOriginal(String propertyName);

  void record(String primitveProperty, Object oldValue, Object newValue);

  void record(String collectionProperty);

  Map<String, Object> getMap();
}
