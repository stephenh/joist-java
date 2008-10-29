package features.domain;

import features.domain.mappers.ManyToManyABarAlias;
import features.domain.mappers.ManyToManyAFooAlias;
import features.domain.mappers.ManyToManyAFooToBarAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;

public abstract class ManyToManyAFooToBarCodegen extends AbstractDomainObject {

    private Id<ManyToManyAFooToBar> id = null;
    private Integer version = null;
    private ManyToManyAFoo manyToManyAFoo = null;
    private Integer manyToManyAFooId = null;
    private ManyToManyABar manyToManyABar = null;
    private Integer manyToManyABarId = null;

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
        if (this.manyToManyAFoo == null && this.manyToManyAFooId != null && UoW.isOpen()) {
            ManyToManyAFooAlias a = new ManyToManyAFooAlias("a");
            this.manyToManyAFoo = Select.from(a).where(a.id.equals(this.manyToManyAFooId)).unique();
        }
        return this.manyToManyAFoo;
    }

    public void setManyToManyAFoo(ManyToManyAFoo manyToManyAFoo) {
        this.recordIfChanged("manyToManyAFoo", this.manyToManyAFoo, manyToManyAFoo);
        this.manyToManyAFoo = manyToManyAFoo;
    }

    public ManyToManyABar getManyToManyABar() {
        if (this.manyToManyABar == null && this.manyToManyABarId != null && UoW.isOpen()) {
            ManyToManyABarAlias a = new ManyToManyABarAlias("a");
            this.manyToManyABar = Select.from(a).where(a.id.equals(this.manyToManyABarId)).unique();
        }
        return this.manyToManyABar;
    }

    public void setManyToManyABar(ManyToManyABar manyToManyABar) {
        this.recordIfChanged("manyToManyABar", this.manyToManyABar, manyToManyABar);
        this.manyToManyABar = manyToManyABar;
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
                ((ManyToManyAFooToBarCodegen) instance).manyToManyAFooId = manyToManyAFooId;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                ManyToManyAFooToBarCodegen instanceCodegen = instance;
                if (instanceCodegen.manyToManyAFoo != null) {
                    return instanceCodegen.manyToManyAFoo.getId().intValue();
                }
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyAFooId;
            }
        };
        public static final Shim<ManyToManyAFooToBar, Integer> manyToManyABarId = new Shim<ManyToManyAFooToBar, Integer>() {
            public void set(ManyToManyAFooToBar instance, Integer manyToManyABarId) {
                ((ManyToManyAFooToBarCodegen) instance).manyToManyABarId = manyToManyABarId;
            }
            public Integer get(ManyToManyAFooToBar instance) {
                ManyToManyAFooToBarCodegen instanceCodegen = instance;
                if (instanceCodegen.manyToManyABar != null) {
                    return instanceCodegen.manyToManyABar.getId().intValue();
                }
                return ((ManyToManyAFooToBarCodegen) instance).manyToManyABarId;
            }
        };
    }

}
