package features.domain;

import bindgen.features.domain.ChildBinding;
import features.domain.queries.ChildQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.domain.validation.rules.Rule;

public abstract class ChildCodegen extends AbstractDomainObject {

    private static ChildBinding b = new ChildBinding();
    protected static ChildAlias alias;
    public static final ChildQueries queries;
    private Integer id = null;
    private String name = null;
    private static Rule<Child> nameNotNull = new NotNull<Child>(b.name());
    private static Rule<Child> nameMaxLength = new MaxLength<Child>(b.name(), 100);
    private Integer version = null;
    private ForeignKeyHolder<Parent> parent = new ForeignKeyHolder<Parent>(Parent.class);
    private static Rule<Child> parentNotNull = new NotNull<Child>(b.parent());
    protected Changed changed;

    static {
        alias = new ChildAlias("a");
        AliasRegistry.register(Child.class, alias);
        queries = new ChildQueries();
    }

    protected ChildCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(nameNotNull);
        this.addRule(nameMaxLength);
        this.addRule(parentNotNull);
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

    public Parent getParent() {
        return this.parent.get();
    }

    public void setParent(Parent parent) {
        if (this.parent.get() != null) {
           this.parent.get().removeChildWithoutPercolation((Child) this);
        }
        this.setParentWithoutPercolation(parent);
        if (this.parent.get() != null) {
           this.parent.get().addChildWithoutPercolation((Child) this);
        }
    }

    protected void setParentWithoutPercolation(Parent parent) {
        this.getChanged().record("parent", this.parent, parent);
        this.parent.set(parent);
    }

    public ChildChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ChildChanged((Child) this);
        }
        return (ChildChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setParent(null);
    }

    static class Shims {
        protected static final Shim<Child, Integer> id = new Shim<Child, Integer>() {
            public void set(Child instance, Integer id) {
                ((ChildCodegen) instance).id = id;
            }
            public Integer get(Child instance) {
                return ((ChildCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<Child, String> name = new Shim<Child, String>() {
            public void set(Child instance, String name) {
                ((ChildCodegen) instance).name = name;
            }
            public String get(Child instance) {
                return ((ChildCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<Child, Integer> version = new Shim<Child, Integer>() {
            public void set(Child instance, Integer version) {
                ((ChildCodegen) instance).version = version;
            }
            public Integer get(Child instance) {
                return ((ChildCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<Child, Integer> parentId = new Shim<Child, Integer>() {
            public void set(Child instance, Integer parentId) {
                ((ChildCodegen) instance).parent.setId(parentId);
            }
            public Integer get(Child instance) {
                return ((ChildCodegen) instance).parent.getId();
            }
            public String getName() {
                return "parent";
            }
        };
    }

    public static class ChildChanged extends joist.domain.AbstractChanged {
        public ChildChanged(Child instance) {
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
        public boolean hasParent() {
            return this.contains("parent");
        }
        public Parent getOriginalParent() {
            return (Parent) this.getOriginal("parent");
        }
    }

}
