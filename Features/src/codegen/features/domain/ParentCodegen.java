package features.domain;

import features.domain.ChildAlias;
import features.domain.ParentAlias;
import features.domain.queries.ParentQueries;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class ParentCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(Parent.class, new ParentAlias("a"));
    }

    public static final ParentQueries queries = new ParentQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final ChildAlias childsAlias = new ChildAlias("a");
    private ForeignKeyListHolder<Parent, Child> childs = new ForeignKeyListHolder<Parent, Child>((Parent) this, childsAlias, childsAlias.parent);
    protected org.exigencecorp.domainobjects.Changed changed;

    protected ParentCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<Parent>("name", Shims.name));
        this.addRule(new MaxLength<Parent>("name", 100, Shims.name));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.getChanged().record("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.getChanged().record("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public List<Child> getChilds() {
        return this.childs.get();
    }

    public void addChild(Child o) {
        o.setParentWithoutPercolation((Parent) this);
        this.addChildWithoutPercolation(o);
    }

    public void removeChild(Child o) {
        o.setParentWithoutPercolation(null);
        this.removeChildWithoutPercolation(o);
    }

    protected void addChildWithoutPercolation(Child o) {
        this.getChanged().record("childs");
        this.childs.add(o);
    }

    protected void removeChildWithoutPercolation(Child o) {
        this.getChanged().record("childs");
        this.childs.remove(o);
    }

    public ParentChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentChanged((Parent) this);
        }
        return (ParentChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<Parent, java.lang.Integer> id = new Shim<Parent, java.lang.Integer>() {
            public void set(Parent instance, java.lang.Integer id) {
                ((ParentCodegen) instance).id = id;
            }
            public Integer get(Parent instance) {
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

    public static class ParentChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public ParentChanged(Parent instance) {
            super(instance);
        }
        public boolean hasId() {
            return this.contains("id");
        }
        public Integer getOriginalId() {
            return (java.lang.Integer) this.getOriginal("id");
        }
        public boolean hasName() {
            return this.contains("name");
        }
        public String getOriginalName() {
            return (java.lang.String) this.getOriginal("name");
        }
        public boolean hasVersion() {
            return this.contains("version");
        }
        public Integer getOriginalVersion() {
            return (java.lang.Integer) this.getOriginal("version");
        }
        public boolean hasChilds() {
            return this.contains("childs");
        }
    }

}
