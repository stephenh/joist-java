package features.domain;

import features.domain.queries.InheritanceBRootChildQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class InheritanceBRootChildCodegen extends AbstractDomainObject {

    protected static InheritanceBRootChildAlias alias;
    public static final InheritanceBRootChildQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<InheritanceBRoot> inheritanceBRoot = new ForeignKeyHolder<InheritanceBRoot>(InheritanceBRoot.class);
    protected Changed changed;

    static {
        alias = new InheritanceBRootChildAlias("a");
        AliasRegistry.register(InheritanceBRootChild.class, alias);
        queries = new InheritanceBRootChildQueries();
        InheritanceBRoot.class.getName();
    }

    protected InheritanceBRootChildCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBRootChild>(Shims.name));
        this.addRule(new MaxLength<InheritanceBRootChild>(Shims.name, 100));
        this.addRule(new NotNull<InheritanceBRootChild>(Shims.inheritanceBRootId));
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

    public InheritanceBRoot getInheritanceBRoot() {
        return this.inheritanceBRoot.get();
    }

    public void setInheritanceBRoot(InheritanceBRoot inheritanceBRoot) {
        if (this.inheritanceBRoot.get() != null) {
           this.inheritanceBRoot.get().removeInheritanceBRootChildWithoutPercolation((InheritanceBRootChild) this);
        }
        this.setInheritanceBRootWithoutPercolation(inheritanceBRoot);
        if (this.inheritanceBRoot.get() != null) {
           this.inheritanceBRoot.get().addInheritanceBRootChildWithoutPercolation((InheritanceBRootChild) this);
        }
    }

    protected void setInheritanceBRootWithoutPercolation(InheritanceBRoot inheritanceBRoot) {
        this.getChanged().record("inheritanceBRoot", this.inheritanceBRoot, inheritanceBRoot);
        this.inheritanceBRoot.set(inheritanceBRoot);
    }

    public InheritanceBRootChildChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceBRootChildChanged((InheritanceBRootChild) this);
        }
        return (InheritanceBRootChildChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setInheritanceBRoot(null);
    }

    static class Shims {
        protected static final Shim<InheritanceBRootChild, Integer> id = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer id) {
                ((InheritanceBRootChildCodegen) instance).id = id;
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<InheritanceBRootChild, String> name = new Shim<InheritanceBRootChild, String>() {
            public void set(InheritanceBRootChild instance, String name) {
                ((InheritanceBRootChildCodegen) instance).name = name;
            }
            public String get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<InheritanceBRootChild, Integer> version = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer version) {
                ((InheritanceBRootChildCodegen) instance).version = version;
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<InheritanceBRootChild, Integer> inheritanceBRootId = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer inheritanceBRootId) {
                ((InheritanceBRootChildCodegen) instance).inheritanceBRoot.setId(inheritanceBRootId);
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).inheritanceBRoot.getId();
            }
            public String getName() {
                return "inheritanceBRoot";
            }
        };
    }

    public static class InheritanceBRootChildChanged extends joist.domain.AbstractChanged {
        public InheritanceBRootChildChanged(InheritanceBRootChild instance) {
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
        public boolean hasInheritanceBRoot() {
            return this.contains("inheritanceBRoot");
        }
        public InheritanceBRoot getOriginalInheritanceBRoot() {
            return (InheritanceBRoot) this.getOriginal("inheritanceBRoot");
        }
    }

}
