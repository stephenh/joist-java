package joist.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import joist.domain.uow.UoW;

import org.apache.commons.lang.ObjectUtils;

public abstract class AbstractChanged implements Changed {

  private final Map<String, Object> properties = new LinkedHashMap<String, Object>();
  private final DomainObject instance;

  protected AbstractChanged(DomainObject instance) {
    this.instance = instance;
  }

  public void record(String primitveProperty, Object oldValue, Object newValue) {
    if (!ObjectUtils.equals(oldValue, newValue) && !this.properties.containsKey(primitveProperty)) {
      this.properties.put(primitveProperty, oldValue);
      if (UoW.isOpen()) {
        UoW.enqueue(this.instance);
      }
    }
  }

  public void record(String collectionProperty) {
    this.properties.put(collectionProperty, null);
    if (UoW.isOpen()) {
      UoW.enqueue(this.instance);
    }
  }

  public boolean contains(String propertyName) {
    return this.properties.containsKey(propertyName);
  }

  public int size() {
    return this.properties.size();
  }

  public void clear() {
    this.properties.clear();
  }

  public Object getOriginal(String propertyName) {
    return this.properties.get(propertyName);
  }

}
