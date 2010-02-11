package joist.domain.orm;

import joist.domain.AbstractDomainObject;
import joist.domain.Changed;

public class DummyDomainObject extends AbstractDomainObject {
    private Integer id;

    public Changed getChanged() {
        return null;
    }

    public Integer getVersion() {
        return null;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
