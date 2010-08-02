package joist.registry2;

import joist.registry.ResourceRef;
import joist.registry2.factories.RefFactory;
import joist.registry2.factories.RefReflectionFactory;

public class Ref<T> implements ResourceRef<T> {

  private volatile T value;
  private volatile RefFactory<T> factory;

  public T get() {
    if (this.value == null) {
      synchronized (this) {
        if (this.factory == null) {
          throw new RuntimeException("No current value or factory for: " + this);
        }
        if (this.value == null) {
          this.value = this.factory.create();
        }
      }
    }
    return this.value;
  }

  public synchronized void unset() {
    if (this.factory != null && this.value != null) {
      this.factory.destroy(this.value);
    }
    this.factory = null;
    this.value = null;
  }

  public synchronized void set(RefFactory<T> factory) {
    this.factory = factory;
  }

  public synchronized void set(Class<? extends T> impl) {
    this.factory = new RefReflectionFactory<T>(impl);
  }

  public synchronized void set(T value) {
    this.value = value;
    this.factory = null;
  }

  public boolean isStarted() {
    return false;
  }
}
