package features.domain;

import features.domain.queries.ManyToManyAFooToBarQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

public abstract class ManyToManyAFooToBarCodegen extends AbstractDomainObject {

    protected static ManyToManyAFooToBarAlias alias;
    public static final ManyToManyAFooToBarQueries queries;
    private Integer id = null;
    private Integer version = null;
    private ForeignKeyHolder<ManyToManyABar> manyToManyABar = new ForeignKeyHolder<ManyToManyABar>(ManyToManyABar.class);
    private ForeignKeyHolder<ManyToManyAFoo> manyToManyAFoo = new ForeignKeyHolder<ManyToManyAFoo>(ManyToManyAFoo.class);
    protected Changed changed;

    static {
        alias = new ManyToManyAFooToBarAlias("a");
        AliasRegistry.register(ManyToManyAFooToBar.class, alias);
        queries = new ManyToManyAFooToBarQueries();
        ManyToManyABar.class.getName();
        ManyToManyAFoo.class.getName();
    }

    protected ManyToManyAFooToBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyAFooToBar>(Shims.manyToManyABarId));
        this.addRule(new NotNull<ManyToManyAFooToBar>(Shims.manyToManyAFooId));
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

    public ManyToManyABar getManyToManyABar() {
        return this.manyToManyABar.get();
    }

    public void setManyToManyABar(ManyToManyABar manyToManyABar) {
        if (this.manyToManyABar.get() != null) {
           this.manyToManyABar.get().removeManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
        }
        this.setManyToManyABarWithoutPercolation(manyToManyABar);
        if (this.manyToManyABar.get() != null) {
           this.manyToManyABar.get().addManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
        }
    }

    protected void setManyToManyABarWithoutPercolation(ManyToManyABar manyToManyABar) {
        this.getChanged().record("manyToManyABar", this.manyToManyABar, manyToManyABar);
        this.manyToManyABar.set(manyToManyABar);
    }

    public ManyToManyAFoo getManyToManyAFoo() {
        return this.manyToManyAFoo.get();
    }

    public void setManyToManyAFoo(ManyToManyAFoo manyToManyAFoo) {
        if (this.manyToManyAFoo.get() != null) {
           this.manyToManyAFoo.get().removeManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
        }
        this.setManyToManyAFooWithoutPercolation(manyToManyAFoo);
        if (this.manyToManyAFoo.get() != null) {
           this.manyToManyAFoo.get().addManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
        }
    }

    protected void setManyToManyAFooWithoutPercolation(ManyToManyAFoo manyToManyAFoo) {
        this.getChanged().record("manyToManyAFoo", this.manyToManyAFoo, manyToManyAFoo);
        this.manyToManyAFoo.set(manyToManyAFoo);
    }

    public ManyToManyAFooToBarChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ManyToManyAFooToBarChanged((ManyToManyAFooToBar) this);
        }
        return (ManyToManyAFooToBarChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setManyToManyABar(null);
        this.setManyToManyAFoo(null);
    }

    static class Shims {
        protected static final Shim<ManyToManyAFooToBar, Integer> id = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer id) {
                ((ManyToManyAFooToBarCodegen) instance).id = id;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ManyToManyAFooToBar, Integer> version = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer version) {
                ((ManyToManyAFooToBarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<ManyToManyAFooToBar, Integer> manyToManyABarId = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer manyToManyABarId) {
                ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.setId(manyToManyABarId);
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.getId();
            }
            public String getName() {
                return "manyToManyABar";
            }
        };
        protected static final Shim<ManyToManyAFooToBar, Integer> manyToManyAFooId = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer manyToManyAFooId) {
                ((ManyToManyAFooToBarCodegen) instance).manyToManyAFoo.setId(manyToManyAFooId);
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyAFoo.getId();
            }
            public String getName() {
                return "manyToManyAFoo";
            }
        };
    }

    public static class ManyToManyAFooToBarChanged extends joist.domain.AbstractChanged {
        public ManyToManyAFooToBarChanged(ManyToManyAFooToBar instance) {
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
        public boolean hasManyToManyABar() {
            return this.contains("manyToManyABar");
        }
        public ManyToManyABar getOriginalManyToManyABar() {
            return (ManyToManyABar) this.getOriginal("manyToManyABar");
        }
        public boolean hasManyToManyAFoo() {
            return this.contains("manyToManyAFoo");
        }
        public ManyToManyAFoo getOriginalManyToManyAFoo() {
            return (ManyToManyAFoo) this.getOriginal("manyToManyAFoo");
        }
    }

}
