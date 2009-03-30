package features.domain;

import features.domain.queries.ParentBChildFooQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class ParentBChildFooCodegen extends AbstractDomainObject {

    protected static ParentBChildFooAlias alias;
    public static final ParentBChildFooQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<ParentBParent> parentBParent = new ForeignKeyHolder<ParentBParent>(ParentBParent.class);
    protected Changed changed;

    static {
        alias = new ParentBChildFooAlias("a");
        AliasRegistry.register(ParentBChildFoo.class, alias);
        queries = new ParentBChildFooQueries();
    }

    protected ParentBChildFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentBChildFoo>(Shims.name));
        this.addRule(new MaxLength<ParentBChildFoo>(Shims.name, 100));
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
        public static final Shim<ParentBChildFoo, Integer> id = new Shim<ParentBChildFoo, Integer>() {
            public void set(ParentBChildFoo instance, Integer id) {
                ((ParentBChildFooCodegen) instance).id = id;
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        public static final Shim<ParentBChildFoo, String> name = new Shim<ParentBChildFoo, String>() {
            public void set(ParentBChildFoo instance, String name) {
                ((ParentBChildFooCodegen) instance).name = name;
            }
            public String get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        public static final Shim<ParentBChildFoo, Integer> version = new Shim<ParentBChildFoo, Integer>() {
            public void set(ParentBChildFoo instance, Integer version) {
                ((ParentBChildFooCodegen) instance).version = version;
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        public static final Shim<ParentBChildFoo, Integer> parentBParentId = new Shim<ParentBChildFoo, Integer>() {
            public void set(ParentBChildFoo instance, Integer parentBParentId) {
                ((ParentBChildFooCodegen) instance).parentBParent.setId(parentBParentId);
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).parentBParent.getId();
            }
            public String getName() {
                return "parentBParent";
            }
        };
    }

    public static class ParentBChildFooChanged extends joist.domain.AbstractChanged {
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
