package joist.domain.orm;

import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;

public class DummyDomainObject extends AbstractDomainObject {
    private Integer id;
    private final DummyChanged changed = new DummyChanged();

    public DummyChanged getChanged() {
        return this.changed;
    }

    public Integer getVersion() {
        return null;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.changed.record("id", this.id, id);
        this.id = id;
    }

    public class DummyChanged extends AbstractChanged {
        protected DummyChanged() {
            super(DummyDomainObject.this);
        }
    }
}
