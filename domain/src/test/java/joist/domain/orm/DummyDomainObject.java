package joist.domain.orm;

import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;

public class DummyDomainObject extends AbstractDomainObject {

  private Long id;
  private final DummyChanged changed = new DummyChanged();

  public DummyDomainObject() {
  }

  public DummyDomainObject(Long id) {
    this.id = id;
  }

  public DummyChanged getChanged() {
    return this.changed;
  }

  public Long getVersion() {
    return null;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.changed.record("id", this.id, id);
    this.id = id;
  }

  public class DummyChanged extends AbstractChanged {
    protected DummyChanged() {
      super(DummyDomainObject.this);
    }
  }
}
