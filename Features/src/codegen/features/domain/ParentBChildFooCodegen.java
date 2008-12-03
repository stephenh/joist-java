package features.domain;

import features.domain.ParentBChildFooAlias;
import features.domain.queries.ParentBChildFooQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class ParentBChildFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentBChildFoo.class, new ParentBChildFooAlias("a"));
    }

    public static final ParentBChildFooQueries queries = new ParentBChildFooQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<ParentBParent> parentBParent = new ForeignKeyHolder<ParentBParent>(ParentBParent.class);
    protected org.exigencecorp.domainobjects.Changed changed;

    protected ParentBChildFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentBChildFoo>("name", Shims.name));
        this.addRule(new MaxLength<ParentBChildFoo>("name", 100, Shims.name));
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

    public ParentBParent getParentBParent() {
        return this.parentBParent.get();
    }

    public void setParentBParent(ParentBParent parentBParent) {
        if (this.parentBParent.get() != null) {
           this.parentBParent.get().removeParentBChildFooWithoutPercolation((ParentBChildFoo) this);
        }
        this.setParentBParentWithoutPercolation(parentBParent);
        if (this.parentBParent.get() != null) {
           this.parentBParent.get().addParentBChildFooWithoutPercolation((ParentBChildFoo) this);
        }
    }

    protected void setParentBParentWithoutPercolation(ParentBParent parentBParent) {
        this.getChanged().record("parentBParent", this.parentBParent, parentBParent);
        this.parentBParent.set(parentBParent);
    }

    public ParentBChildFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentBChildFooChanged((ParentBChildFoo) this);
        }
        return (ParentBChildFooChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<ParentBChildFoo, java.lang.Integer> id = new Shim<ParentBChildFoo, java.lang.Integer>() {
            public void set(ParentBChildFoo instance, java.lang.Integer id) {
                ((ParentBChildFooCodegen) instance).id = id;
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).id;
            }
        };
        public static final Shim<ParentBChildFoo, java.lang.String> name = new Shim<ParentBChildFoo, java.lang.String>() {
            public void set(ParentBChildFoo instance, java.lang.String name) {
                ((ParentBChildFooCodegen) instance).name = name;
            }
            public String get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).name;
            }
        };
        public static final Shim<ParentBChildFoo, java.lang.Integer> version = new Shim<ParentBChildFoo, java.lang.Integer>() {
            public void set(ParentBChildFoo instance, java.lang.Integer version) {
                ((ParentBChildFooCodegen) instance).version = version;
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).version;
            }
        };
        public static final Shim<ParentBChildFoo, Integer> parentBParentId = new Shim<ParentBChildFoo, Integer>() {
            public void set(ParentBChildFoo instance, Integer parentBParentId) {
                ((ParentBChildFooCodegen) instance).parentBParent.setId(parentBParentId);
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).parentBParent.getId();
            }
        };
    }

    public static class ParentBChildFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public ParentBChildFooChanged(ParentBChildFoo instance) {
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
        public boolean hasParentBParent() {
            return this.contains("parentBParent");
        }
        public ParentBParent getOriginalParentBParent() {
            return (ParentBParent) this.getOriginal("parentBParent");
        }
    }

}
