package features.domain;

import features.domain.queries.ManyToManyABarQueries;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Changed;
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
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final ManyToManyAFooToBarAlias manyToManyAFooToBarsAlias = new ManyToManyAFooToBarAlias("a");
    private ForeignKeyListHolder<ManyToManyABar, ManyToManyAFooToBar> manyToManyAFooToBars = new ForeignKeyListHolder<ManyToManyABar, ManyToManyAFooToBar>((ManyToManyABar) this, manyToManyAFooToBarsAlias, manyToManyAFooToBarsAlias.manyToManyABar);
    protected Changed changed;

    protected ManyToManyABarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyABar>("name", Shims.name));
        this.addRule(new MaxLength<ManyToManyABar>("name", 100, Shims.name));
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

    public List<ManyToManyAFooToBar> getManyToManyAFooToBars() {
        return this.manyToManyAFooToBars.get();
    }

    public void addManyToManyAFooToBar(ManyToManyAFooToBar o) {
        o.setManyToManyABarWithoutPercolation((ManyToManyABar) this);
        this.addManyToManyAFooToBarWithoutPercolation(o);
    }

    public void removeManyToManyAFooToBar(ManyToManyAFooToBar o) {
        o.setManyToManyABarWithoutPercolation(null);
        this.removeManyToManyAFooToBarWithoutPercolation(o);
    }

    protected void addManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
        this.getChanged().record("manyToManyAFooToBars");
        this.manyToManyAFooToBars.add(o);
    }

    protected void removeManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
        this.getChanged().record("manyToManyAFooToBars");
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
                UoW.delete(a);
            }
        }
    }

    public ManyToManyABarChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ManyToManyABarChanged((ManyToManyABar) this);
        }
        return (ManyToManyABarChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<ManyToManyABar, Integer> id = new Shim<ManyToManyABar, Integer>() {
            public void set(ManyToManyABar instance, Integer id) {
                ((ManyToManyABarCodegen) instance).id = id;
            }
            public Integer get(ManyToManyABar instance) {
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

    public static class ManyToManyABarChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public ManyToManyABarChanged(ManyToManyABar instance) {
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
        public boolean hasManyToManyAFooToBars() {
            return this.contains("manyToManyAFooToBars");
        }
    }

}
