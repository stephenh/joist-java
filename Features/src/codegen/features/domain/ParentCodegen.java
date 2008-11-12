package features.domain;

import features.domain.queries.ChildAlias;
import features.domain.queries.ParentAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class ParentCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(Parent.class, new ParentAlias("a"));
    }

    private Id<Parent> id = null;
    private String name = null;
    private Integer version = null;
    private List<Child> childs;

    protected ParentCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<Parent>("name", Shims.name));
        this.addRule(new MaxLength<Parent>("name", 100, Shims.name));
    }

    public Id<Parent> getId() {
        return this.id;
    }

    public void setId(Id<Parent> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getCurrent().getIdentityMap().store(Parent.class, this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public List<Child> getChilds() {
        if (this.childs == null) {
            if (UoW.isOpen() && this.getId() != null) {
                ChildAlias a = new ChildAlias("a");
                this.childs = Select.from(a).where(a.parent.equals(this.getId())).orderBy(a.id.asc()).list();
            } else {
                this.childs = new ArrayList<Child>();
            }
        }
        return this.childs;
    }

    public void addChild(Child o) {
        o.setParentWithoutPercolation((Parent) this);
        this.addChildWithoutPercolation(o);
    }

    public void addChildWithoutPercolation(Child o) {
        this.getChilds(); // hack
        this.recordIfChanged("childs");
        this.childs.add(o);
    }

    public void removeChild(Child o) {
        o.setParentWithoutPercolation(null);
        this.removeChildWithoutPercolation(o);
    }

    public void removeChildWithoutPercolation(Child o) {
        this.getChilds(); // hack
        this.recordIfChanged("childs");
        this.childs.remove(o);
    }

    public static class Shims {
        public static final Shim<Parent, Id<Parent>> id = new Shim<Parent, Id<Parent>>() {
            public void set(Parent instance, Id<Parent> id) {
                ((ParentCodegen) instance).id = id;
            }
            public Id<Parent> get(Parent instance) {
                return ((ParentCodegen) instance).id;
            }
        };
        public static final Shim<Parent, java.lang.String> name = new Shim<Parent, java.lang.String>() {
            public void set(Parent instance, java.lang.String name) {
                ((ParentCodegen) instance).name = name;
            }
            public String get(Parent instance) {
                return ((ParentCodegen) instance).name;
            }
        };
        public static final Shim<Parent, java.lang.Integer> version = new Shim<Parent, java.lang.Integer>() {
            public void set(Parent instance, java.lang.Integer version) {
                ((ParentCodegen) instance).version = version;
            }
            public Integer get(Parent instance) {
                return ((ParentCodegen) instance).version;
            }
        };
    }

}
