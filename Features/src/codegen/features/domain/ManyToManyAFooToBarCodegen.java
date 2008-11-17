package features.domain;

import features.domain.ManyToManyAFooToBarAlias;
import features.domain.queries.ManyToManyAFooToBarQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;

abstract class ManyToManyAFooToBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyAFooToBar.class, new ManyToManyAFooToBarAlias("a"));
    }

    public static final ManyToManyAFooToBarQueries queries = new ManyToManyAFooToBarQueries();
    private Integer id = null;
    private Integer version = null;
    private ForeignKeyHolder<ManyToManyABar> manyToManyABar = new ForeignKeyHolder<ManyToManyABar>(ManyToManyABar.class);
    private ForeignKeyHolder<ManyToManyAFoo> manyToManyAFoo = new ForeignKeyHolder<ManyToManyAFoo>(ManyToManyAFoo.class);

    protected ManyToManyAFooToBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.recordIfChanged("id", this.id, id);
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

    public void setManyToManyABarWithoutPercolation(ManyToManyABar manyToManyABar) {
        this.recordIfChanged("manyToManyABar", this.manyToManyABar, manyToManyABar);
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

    public void setManyToManyAFooWithoutPercolation(ManyToManyAFoo manyToManyAFoo) {
        this.recordIfChanged("manyToManyAFoo", this.manyToManyAFoo, manyToManyAFoo);
        this.manyToManyAFoo.set(manyToManyAFoo);
    }

    public static class Shims {
        public static final Shim<ManyToManyAFooToBar, java.lang.Integer> id = new Shim<ManyToManyAFooToBar, java.lang.Integer>() {
            public void set(ManyToManyAFooToBar instance, java.lang.Integer id) {
                ((ManyToManyAFooToBarCodegen) instance).id = id;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyAFooToBar, java.lang.Integer> version = new Shim<ManyToManyAFooToBar, java.lang.Integer>() {
            public void set(ManyToManyAFooToBar instance, java.lang.Integer version) {
                ((ManyToManyAFooToBarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).version;
            }
        };
        public static final Shim<ManyToManyAFooToBar, Integer> manyToManyABarId = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer manyToManyABarId) {
                ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.setId(manyToManyABarId);
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.getId();
            }
        };
        public static final Shim<ManyToManyAFooToBar, Integer> manyToManyAFooId = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer manyToManyAFooId) {
                ((ManyToManyAFooToBarCodegen) instance).manyToManyAFoo.setId(manyToManyAFooId);
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyAFoo.getId();
            }
        };
    }

}
