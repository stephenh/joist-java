package features.domain;

import features.domain.queries.ParentDToChildCQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

public abstract class ParentDToChildCCodegen extends AbstractDomainObject {

    public static final ParentDToChildCQueries queries;
    private Integer id = null;
    private Integer version = null;
    private final ForeignKeyHolder<ParentDChildC> parentDChildC = new ForeignKeyHolder<ParentDChildC>(ParentDChildC.class);
    private final ForeignKeyHolder<ParentD> parentD = new ForeignKeyHolder<ParentD>(ParentD.class);
    protected Changed changed;

    static {
        Aliases.init();
        queries = new ParentDToChildCQueries();
    }

    protected ParentDToChildCCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentDToChildC>(Shims.parentDChildCId));
        this.addRule(new NotNull<ParentDToChildC>(Shims.parentDId));
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

    public Integer getVersion() {
        return this.version;
    }

    public ParentDChildC getParentDChildC() {
        return this.parentDChildC.get();
    }

    public void setParentDChildC(ParentDChildC parentDChildC) {
        if (this.parentDChildC.get() != null) {
           this.parentDChildC.get().removeParentDToChildCWithoutPercolation((ParentDToChildC) this);
        }
        this.setParentDChildCWithoutPercolation(parentDChildC);
        if (this.parentDChildC.get() != null) {
           this.parentDChildC.get().addParentDToChildCWithoutPercolation((ParentDToChildC) this);
        }
    }

    protected void setParentDChildCWithoutPercolation(ParentDChildC parentDChildC) {
        this.getChanged().record("parentDChildC", this.parentDChildC, parentDChildC);
        this.parentDChildC.set(parentDChildC);
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

    public ParentDToChildCChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentDToChildCChanged((ParentDToChildC) this);
        }
        return (ParentDToChildCChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setParentDChildC(null);
        this.setParentD(null);
    }

    static class Shims {
        protected static final Shim<ParentDToChildC, Integer> id = new Shim<ParentDToChildC, Integer>() {
            public void set(ParentDToChildC instance, Integer id) {
                ((ParentDToChildCCodegen) instance).id = id;
            }
            public Integer get(ParentDToChildC instance) {
                return ((ParentDToChildCCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentDToChildC, Integer> version = new Shim<ParentDToChildC, Integer>() {
            public void set(ParentDToChildC instance, Integer version) {
                ((ParentDToChildCCodegen) instance).version = version;
            }
            public Integer get(ParentDToChildC instance) {
                return ((ParentDToChildCCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<ParentDToChildC, Integer> parentDChildCId = new Shim<ParentDToChildC, Integer>() {
            public void set(ParentDToChildC instance, Integer parentDChildCId) {
                ((ParentDToChildCCodegen) instance).parentDChildC.setId(parentDChildCId);
            }
            public Integer get(ParentDToChildC instance) {
                return ((ParentDToChildCCodegen) instance).parentDChildC.getId();
            }
            public String getName() {
                return "parentDChildC";
            }
        };
        protected static final Shim<ParentDToChildC, Integer> parentDId = new Shim<ParentDToChildC, Integer>() {
            public void set(ParentDToChildC instance, Integer parentDId) {
                ((ParentDToChildCCodegen) instance).parentD.setId(parentDId);
            }
            public Integer get(ParentDToChildC instance) {
                return ((ParentDToChildCCodegen) instance).parentD.getId();
            }
            public String getName() {
                return "parentD";
            }
        };
    }

    public static class ParentDToChildCChanged extends AbstractChanged {
        public ParentDToChildCChanged(ParentDToChildC instance) {
            super(instance);
        }
        public boolean hasId() {
            return this.contains("id");
        }
        public Integer getOriginalId() {
            return (java.lang.Integer) this.getOriginal("id");
        }
        public boolean hasVersion() {
            return this.contains("version");
        }
        public Integer getOriginalVersion() {
            return (java.lang.Integer) this.getOriginal("version");
        }
        public boolean hasParentDChildC() {
            return this.contains("parentDChildC");
        }
        public ParentDChildC getOriginalParentDChildC() {
            return (ParentDChildC) this.getOriginal("parentDChildC");
        }
        public boolean hasParentD() {
            return this.contains("parentD");
        }
        public ParentD getOriginalParentD() {
            return (ParentD) this.getOriginal("parentD");
        }
    }

}
