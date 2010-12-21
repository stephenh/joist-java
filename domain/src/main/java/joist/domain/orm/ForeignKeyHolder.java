package joist.domain.orm;

import joist.domain.DomainObject;
import joist.domain.exceptions.DisconnectedException;
import joist.domain.uow.UoW;
import joist.util.Default;

/** A value holder that will lazy load the foreign key. */
public class ForeignKeyHolder<T extends DomainObject> {

  private Class<T> domainClass;
  private Long id;
  private T instance;

  public ForeignKeyHolder(Class<T> domainClass) {
    this.domainClass = domainClass;
  }

  public T get() {
    if (this.instance == null && this.id != null) {
      if (UoW.isOpen()) {
        this.instance = UoW.load(this.domainClass, this.id);
      } else {
        throw new DisconnectedException();
      }
    }
    return this.instance;
  }

  public void set(T instance) {
    this.instance = instance;
    if (instance == null) {
      this.id = null;
    }
  }

  public Long getId() {
    if (this.instance != null) {
      // Return -1 as the dummy value that we have a value, but do not
      // know its id yet. So far this is only for the NotNull rule so
      // it can still use the xxxId shim
      return Default.value(this.instance.getId(), -1l);
    }
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

}
