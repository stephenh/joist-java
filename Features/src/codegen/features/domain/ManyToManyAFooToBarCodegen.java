package features.domain;

import features.domain.mappers.ManyToManyAFooToBarAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;

public abstract class ManyToManyAFooToBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyAFooToBar.class, new ManyToManyAFooToBarAlias("a"));
    }

    private Id<ManyToManyAFooToBar> id = null;
    private Integer version = null;
    private ForeignKeyHolder<ManyToManyAFoo> manyToManyAFoo = new ForeignKeyHolder<ManyToManyAFoo>(ManyToManyAFoo.class);
    private ForeignKeyHolder<ManyToManyABar> manyToManyABar = new ForeignKeyHolder<ManyToManyABar>(ManyToManyABar.class);

    public Id<ManyToManyAFooToBar> getId() {
        return this.id;
    }

    public void setId(Id<ManyToManyAFooToBar> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
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

    public static class Shims {
        public static final Shim<ManyToManyAFooToBar, Id<ManyToManyAFooToBar>> id = new Shim<ManyToManyAFooToBar, Id<ManyToManyAFooToBar>>() {
            public void set(ManyToManyAFooToBar instance, Id<ManyToManyAFooToBar> id) {
                ((ManyToManyAFooToBarCodegen) instance).id = id;
            }
            public Id<ManyToManyAFooToBar> get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyAFooToBar, Integer> version = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer version) {
                ((ManyToManyAFooToBarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).version;
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
        public static final Shim<ManyToManyAFooToBar, Integer> manyToManyABarId = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer manyToManyABarId) {
                ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.setId(manyToManyABarId);
            }
            public Integer get(ManyToManyAFooToBar instance) {
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.getId();
            }
        };
    }

}
