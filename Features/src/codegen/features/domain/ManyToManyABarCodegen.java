package features.domain;

import features.domain.mappers.ManyToManyABarAlias;
import features.domain.mappers.ManyToManyAFooToBarAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.util.Copy;

public abstract class ManyToManyABarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyABar.class, new ManyToManyABarAlias("a"));
    }

    private Id<ManyToManyABar> id = null;
    private String name = null;
    private Integer version = null;
    private List<ManyToManyAFooToBar> manyToManyAFooToBars;

    public Id<ManyToManyABar> getId() {
        return this.id;
    }

    public void setId(Id<ManyToManyABar> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public List<ManyToManyAFooToBar> getManyToManyAFooToBars() {
        if (this.manyToManyAFooToBars == null) {
            if (UoW.isOpen() && this.getId() != null) {
                ManyToManyAFooToBarAlias a = new ManyToManyAFooToBarAlias("a");
                this.manyToManyAFooToBars = Select.from(a).where(a.manyToManyABar.equals(this.getId())).orderBy(a.id.asc()).list();
            } else {
                this.manyToManyAFooToBars = new ArrayList<ManyToManyAFooToBar>();
            }
        }
        return this.manyToManyAFooToBars;
    }

    public void addManyToManyAFooToBar(ManyToManyAFooToBar o) {
        o.setManyToManyABarWithoutPercolation((ManyToManyABar) this);
        this.addManyToManyAFooToBarWithoutPercolation(o);
    }

    public void addManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
        this.getManyToManyAFooToBars(); // hack
        this.recordIfChanged("manyToManyAFooToBars");
        this.manyToManyAFooToBars.add(o);
    }

    public void removeManyToManyAFooToBar(ManyToManyAFooToBar o) {
        o.setManyToManyABarWithoutPercolation(null);
        this.removeManyToManyAFooToBarWithoutPercolation(o);
    }

    public void removeManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
        this.getManyToManyAFooToBars(); // hack
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
        public static final Shim<ManyToManyABar, String> name = new Shim<ManyToManyABar, String>() {
            public void set(ManyToManyABar instance, String name) {
                ((ManyToManyABarCodegen) instance).name = name;
            }
            public String get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).name;
            }
        };
        public static final Shim<ManyToManyABar, Integer> version = new Shim<ManyToManyABar, Integer>() {
            public void set(ManyToManyABar instance, Integer version) {
                ((ManyToManyABarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).version;
            }
        };
    }

}
