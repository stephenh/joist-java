package joist.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import joist.domain.uow.UoW;

public abstract class AbstractChanged implements Changed {

  private final Map<String, Object> properties = new LinkedHashMap<String, Object>();
  private final DomainObject instance;

  protected AbstractChanged(DomainObject instance) {
    this.instance = instance;
  }

  @Override
  public void record(String primitveProperty, Object oldValue, Object newValue) {
    if (!ObjectUtils.equals(oldValue, newValue)) {
      if (!this.properties.containsKey(primitveProperty)) {
        this.properties.put(primitveProperty, oldValue);
      }
      if (UoW.isOpen()) {
        UoW.enqueue(this.instance);
      }
    }
  }

  @Override
  public void record(String collectionProperty) {
    this.properties.put(collectionProperty, null);
    if (UoW.isOpen()) {
      UoW.enqueue(this.instance);
    }
  }

  @Override
  public boolean contains(String propertyName) {
    return this.properties.containsKey(propertyName);
  }

  @Override
  public int size() {
    return this.properties.size();
  }

  @Override
  public void clear() {
    this.properties.clear();
  }

  @Override
  public Object getOriginal(String propertyName) {
    return this.properties.get(propertyName);
  }

  @Override
  public Map<String, Object> getMap() {
    return Collections.unmodifiableMap(this.properties);
  }
}
