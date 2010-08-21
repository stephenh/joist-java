package features.domain;

import features.domain.queries.ParentDChildAQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ParentDChildACodegen extends AbstractDomainObject {

    public static final ParentDChildAQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private final ForeignKeyHolder<ParentD> parentD = new ForeignKeyHolder<ParentD>(ParentD.class);
    protected Changed changed;

    static {
        Aliases.parentDChildA();
        queries = new ParentDChildAQueries();
    }

    protected ParentDChildACodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentDChildA>(Shims.name));
        this.addRule(new MaxLength<ParentDChildA>(Shims.name, 100));
        this.addRule(new NotNull<ParentDChildA>(Shims.parentDId));
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

    public ParentD getParentD() {
        return this.parentD.get();
    }

    public void setParentD(ParentD parentD) {
        this.setParentDWithoutPercolation(parentD);
    }

    protected void setParentDWithoutPercolation(ParentD parentD) {
        this.getChanged().record("parentD", this.parentD, parentD);
        this.parentD.set(parentD);
    }

    public ParentDChildAChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentDChildAChanged((ParentDChildA) this);
        }
        return (ParentDChildAChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setParentD(null);
    }

    static class Shims {
        protected static final Shim<ParentDChildA, Integer> id = new Shim<ParentDChildA, Integer>() {
            public void set(ParentDChildA instance, Integer id) {
                ((ParentDChildACodegen) instance).id = id;
            }
            public Integer get(ParentDChildA instance) {
                return ((ParentDChildACodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentDChildA, String> name = new Shim<ParentDChildA, String>() {
            public void set(ParentDChildA instance, String name) {
                ((ParentDChildACodegen) instance).name = name;
            }
            public String get(ParentDChildA instance) {
                return ((ParentDChildACodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<ParentDChildA, Integer> version = new Shim<ParentDChildA, Integer>() {
            public void set(ParentDChildA instance, Integer version) {
                ((ParentDChildACodegen) instance).version = version;
            }
            public Integer get(ParentDChildA instance) {
                return ((ParentDChildACodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<ParentDChildA, Integer> parentDId = new Shim<ParentDChildA, Integer>() {
            public void set(ParentDChildA instance, Integer parentDId) {
                ((ParentDChildACodegen) instance).parentD.setId(parentDId);
            }
            public Integer get(ParentDChildA instance) {
                return ((ParentDChildACodegen) instance).parentD.getId();
            }
            public String getName() {
                return "parentD";
            }
        };
    }

    public static class ParentDChildAChanged extends AbstractChanged {
        public ParentDChildAChanged(ParentDChildA instance) {
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
        public boolean hasParentD() {
            return this.contains("parentD");
        }
        public ParentD getOriginalParentD() {
            return (ParentD) this.getOriginal("parentD");
        }
    }

}
