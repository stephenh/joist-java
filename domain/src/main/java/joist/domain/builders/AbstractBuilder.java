package joist.domain.builders;

import joist.domain.DomainObject;
import joist.domain.uow.UoW;

/**
 * Provides a reference to an instance that, if in acceptance tests,
 * can load the instance cross units of work to avoid disconnected exceptions.
 */
public abstract class AbstractBuilder<T extends DomainObject> {

  private T instance;

  protected AbstractBuilder(T instance) {
    if (instance == null) {
      throw new NullPointerException("Builders cannot wrap null instances");
    }
    this.instance = instance;
  }

  public T get() {
    if (UoW.isOpen() && this.instance.getId() != null) {
      // if the UoW is open, and this instance has been saved already,
      // ensure our instance is not from a prior UoW (e.g. in tests)
      this.instance = (T) UoW.load(this.instance.getClass(), this.instance.getId());
    }
    return this.instance;
  }

  @Override
  public int hashCode() {
    return this.instance.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof AbstractBuilder<?>) {
      return this.get().equals(((AbstractBuilder<?>) other).get());
    }
    return false;
  }

  /** Sets required defaults, for subclasses to override. */
  public AbstractBuilder<T> defaults() {
    return this;
  }
}
