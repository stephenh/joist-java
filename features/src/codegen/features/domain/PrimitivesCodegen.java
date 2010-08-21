package features.domain;

import features.domain.queries.PrimitivesQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class PrimitivesCodegen extends AbstractDomainObject {

    public static final PrimitivesQueries queries;
    private Boolean flag = false;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    protected Changed changed;

    static {
        Aliases.init();
        queries = new PrimitivesQueries();
    }

    protected PrimitivesCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<Primitives>(Shims.flag));
        this.addRule(new NotNull<Primitives>(Shims.name));
        this.addRule(new MaxLength<Primitives>(Shims.name, 100));
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public void setFlag(Boolean flag) {
        this.getChanged().record("flag", this.flag, flag);
        this.flag = flag;
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

    public PrimitivesChanged getChanged() {
        if (this.changed == null) {
            this.changed = new PrimitivesChanged((Primitives) this);
        }
        return (PrimitivesChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<Primitives, Boolean> flag = new Shim<Primitives, Boolean>() {
            public void set(Primitives instance, Boolean flag) {
                ((PrimitivesCodegen) instance).flag = flag;
            }
            public Boolean get(Primitives instance) {
                return ((PrimitivesCodegen) instance).flag;
            }
            public String getName() {
                return "flag";
            }
        };
        protected static final Shim<Primitives, Integer> id = new Shim<Primitives, Integer>() {
            public void set(Primitives instance, Integer id) {
                ((PrimitivesCodegen) instance).id = id;
            }
            public Integer get(Primitives instance) {
                return ((PrimitivesCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<Primitives, String> name = new Shim<Primitives, String>() {
            public void set(Primitives instance, String name) {
                ((PrimitivesCodegen) instance).name = name;
            }
            public String get(Primitives instance) {
                return ((PrimitivesCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<Primitives, Integer> version = new Shim<Primitives, Integer>() {
            public void set(Primitives instance, Integer version) {
                ((PrimitivesCodegen) instance).version = version;
            }
            public Integer get(Primitives instance) {
                return ((PrimitivesCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class PrimitivesChanged extends AbstractChanged {
        public PrimitivesChanged(Primitives instance) {
            super(instance);
        }
        public boolean hasFlag() {
            return this.contains("flag");
        }
        public Boolean getOriginalFlag() {
            return (java.lang.Boolean) this.getOriginal("flag");
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
    }

}
