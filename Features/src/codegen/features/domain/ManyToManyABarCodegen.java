package features.domain;

import features.domain.ManyToManyABarAlias;
import features.domain.ManyToManyAFooToBarAlias;
import features.domain.queries.ManyToManyABarQueries;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;
import org.exigencecorp.util.Copy;

abstract class ManyToManyABarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyABar.class, new ManyToManyABarAlias("a"));
    }

    public static final ManyToManyABarQueries queries = new ManyToManyABarQueries();
    private Id<ManyToManyABar> id = null;
    private String name = null;
    private Integer version = null;
    private static final ManyToManyAFooToBarAlias manyToManyAFooToBarsAlias = new ManyToManyAFooToBarAlias("a");
    private ForeignKeyListHolder<ManyToManyABar, ManyToManyAFooToBar> manyToManyAFooToBars = new ForeignKeyListHolder<ManyToManyABar, ManyToManyAFooToBar>((ManyToManyABar) this, manyToManyAFooToBarsAlias, manyToManyAFooToBarsAlias.manyToManyABar);

    protected ManyToManyABarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyABar>("name", Shims.name));
        this.addRule(new MaxLength<ManyToManyABar>("name", 100, Shims.name));
    }

    public Id<ManyToManyABar> getId() {
        return this.id;
    }

    public void setId(Id<ManyToManyABar> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getCurrent().getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public List<ManyToManyAFooToBar> getManyToManyAFooToBars() {
        return this.manyToManyAFooToBars.get();
    }

    public void addManyToManyAFooToBar(ManyToManyAFooToBar o) {
        o.setManyToManyABarWithoutPercolation((ManyToManyABar) this);
        this.addManyToManyAFooToBarWithoutPercolation(o);
    }

    public void addManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
        this.recordIfChanged("manyToManyAFooToBars");
        this.manyToManyAFooToBars.add(o);
    }

    public void removeManyToManyAFooToBar(ManyToManyAFooToBar o) {
        o.setManyToManyABarWithoutPercolation(null);
        this.removeManyToManyAFooToBarWithoutPercolation(o);
    }

    public void removeManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
        this.recordIfChanged("manyToManyAFooToBars");
        this.manyToManyAFooToBars.remove(o);
    }

    public List<ManyToManyAFoo> getManyToManyAFoos() {
        List<ManyToManyAFoo> l = new ArrayList<ManyToManyAFoo>();
        for (ManyToManyAFooToBar o : this.getManyToManyAFooToBars()) {
            l.add(o.getManyToManyAFoo());
        }
        return l;
    }

    public void addManyToManyAFoo(ManyToManyAFoo o) {
        ManyToManyAFooToBar a = new ManyToManyAFooToBar();
        a.setManyToManyABar((ManyToManyABar) this);
        a.setManyToManyAFoo(o);
    }

    public void removeManyToManyAFoo(ManyToManyAFoo o) {
        for (ManyToManyAFooToBar a : Copy.shallow(this.getManyToManyAFooToBars())) {
            if (a.getManyToManyAFoo().equals(o)) {
                a.setManyToManyAFoo(null);
                a.setManyToManyABar(null);
                UoW.getCurrent().delete(a);
            }
        }
    }

    public static class Shims {
        public static final Shim<ManyToManyABar, Id<ManyToManyABar>> id = new Shim<ManyToManyABar, Id<ManyToManyABar>>() {
            public void set(ManyToManyABar instance, Id<ManyToManyABar> id) {
                ((ManyToManyABarCodegen) instance).id = id;
            }
            public Id<ManyToManyABar> get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyABar, java.lang.String> name = new Shim<ManyToManyABar, java.lang.String>() {
            public void set(ManyToManyABar instance, java.lang.String name) {
                ((ManyToManyABarCodegen) instance).name = name;
            }
            public String get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).name;
            }
        };
        public static final Shim<ManyToManyABar, java.lang.Integer> version = new Shim<ManyToManyABar, java.lang.Integer>() {
            public void set(ManyToManyABar instance, java.lang.Integer version) {
                ((ManyToManyABarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).version;
            }
        };
    }

}
