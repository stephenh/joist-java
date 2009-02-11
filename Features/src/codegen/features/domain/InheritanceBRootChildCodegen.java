package features.domain;

import features.domain.queries.InheritanceBRootChildQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Changed;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

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
    }

    protected InheritanceBRootChildCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBRootChild>("name", Shims.name));
        this.addRule(new MaxLength<InheritanceBRootChild>("name", 100, Shims.name));
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

    public static class Shims {
        public static final Shim<InheritanceBRootChild, Integer> id = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer id) {
                ((InheritanceBRootChildCodegen) instance).id = id;
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).id;
            }
        };
        public static final Shim<InheritanceBRootChild, String> name = new Shim<InheritanceBRootChild, String>() {
            public void set(InheritanceBRootChild instance, String name) {
                ((InheritanceBRootChildCodegen) instance).name = name;
            }
            public String get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceBRootChild, Integer> version = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer version) {
                ((InheritanceBRootChildCodegen) instance).version = version;
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).version;
            }
        };
        public static final Shim<InheritanceBRootChild, Integer> inheritanceBRootId = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer inheritanceBRootId) {
                ((InheritanceBRootChildCodegen) instance).inheritanceBRoot.setId(inheritanceBRootId);
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).inheritanceBRoot.getId();
            }
        };
    }

    public static class InheritanceBRootChildChanged extends org.exigencecorp.domainobjects.AbstractChanged {
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
