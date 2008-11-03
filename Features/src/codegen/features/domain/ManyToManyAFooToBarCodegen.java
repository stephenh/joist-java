package features.domain;

import features.domain.mappers.ManyToManyAFooToBarAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class ManyToManyAFooToBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyAFooToBar.class, new ManyToManyAFooToBarAlias("a"));
    }

    private Id<ManyToManyAFooToBar> id = null;
    private Integer version = null;
    private ForeignKeyHolder<ManyToManyAFoo> manyToManyAFoo = new ForeignKeyHolder<ManyToManyAFoo>(ManyToManyAFoo.class);
    private ForeignKeyHolder<ManyToManyABar> manyToManyABar = new ForeignKeyHolder<ManyToManyABar>(ManyToManyABar.class);

    public Alias<? extends ManyToManyAFooToBar> newAlias(String alias) {
        return new ManyToManyAFooToBarAlias(alias);
    }

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
        this.recordIfChanged("manyToManyAFoo", this.manyToManyAFoo, manyToManyAFoo);
        this.manyToManyAFoo.set(manyToManyAFoo);
    }

    public ManyToManyABar getManyToManyABar() {
        return this.manyToManyABar.get();
    }

    public void setManyToManyABar(ManyToManyABar manyToManyABar) {
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
