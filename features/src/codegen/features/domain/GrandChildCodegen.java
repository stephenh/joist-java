package features.domain;

import features.domain.queries.GrandChildQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class GrandChildCodegen extends AbstractDomainObject {

    public static final GrandChildQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private final ForeignKeyHolder<Child> child = new ForeignKeyHolder<Child>(Child.class);
    protected Changed changed;

    static {
        Aliases.grandChild();
        queries = new GrandChildQueries();
    }

    protected GrandChildCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<GrandChild>(Shims.name));
        this.addRule(new MaxLength<GrandChild>(Shims.name, 100));
        this.addRule(new NotNull<GrandChild>(Shims.childId));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.getChanged().record("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.getChanged().record("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Child getChild() {
        return this.child.get();
    }

    public void setChild(Child child) {
        if (this.child.get() != null) {
           this.child.get().removeGrandChildWithoutPercolation((GrandChild) this);
        }
        this.setChildWithoutPercolation(child);
        if (this.child.get() != null) {
           this.child.get().addGrandChildWithoutPercolation((GrandChild) this);
        }
    }

    protected void setChildWithoutPercolation(Child child) {
        this.getChanged().record("child", this.child, child);
        this.child.set(child);
    }

    public GrandChildChanged getChanged() {
        if (this.changed == null) {
            this.changed = new GrandChildChanged((GrandChild) this);
        }
        return (GrandChildChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setChild(null);
    }

    static class Shims {
        protected static final Shim<GrandChild, Integer> id = new Shim<GrandChild, Integer>() {
            public void set(GrandChild instance, Integer id) {
                ((GrandChildCodegen) instance).id = id;
            }
            public Integer get(GrandChild instance) {
                return ((GrandChildCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<GrandChild, String> name = new Shim<GrandChild, String>() {
            public void set(GrandChild instance, String name) {
                ((GrandChildCodegen) instance).name = name;
            }
            public String get(GrandChild instance) {
                return ((GrandChildCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<GrandChild, Integer> version = new Shim<GrandChild, Integer>() {
            public void set(GrandChild instance, Integer version) {
                ((GrandChildCodegen) instance).version = version;
            }
            public Integer get(GrandChild instance) {
                return ((GrandChildCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<GrandChild, Integer> childId = new Shim<GrandChild, Integer>() {
            public void set(GrandChild instance, Integer childId) {
                ((GrandChildCodegen) instance).child.setId(childId);
            }
            public Integer get(GrandChild instance) {
                return ((GrandChildCodegen) instance).child.getId();
            }
            public String getName() {
                return "child";
            }
        };
    }

    public static class GrandChildChanged extends AbstractChanged {
        public GrandChildChanged(GrandChild instance) {
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
        public boolean hasChild() {
            return this.contains("child");
        }
        public Child getOriginalChild() {
            return (Child) this.getOriginal("child");
        }
    }

}
