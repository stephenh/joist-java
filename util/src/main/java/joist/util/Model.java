package joist.util;

import java.util.LinkedHashMap;

/** A standard UI model object. */
public class Model extends LinkedHashMap<String, Object> {

  private static final long serialVersionUID = 1L;

  public static Model make(String key, Object value) {
    return new Model().with(key, value);
  }

  public Model with(String key, Object value) {
    this.put(key, value);
    return this;
  }

}
