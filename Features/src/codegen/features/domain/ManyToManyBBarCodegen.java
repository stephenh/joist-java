package features.domain;

import features.domain.ManyToManyBBarAlias;
import features.domain.ManyToManyBFooToBarAlias;
import features.domain.queries.ManyToManyBBarQueries;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;
import org.exigencecorp.util.Copy;

abstract class ManyToManyBBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyBBar.class, new ManyToManyBBarAlias("a"));
    }

    public static final ManyToManyBBarQueries queries = new ManyToManyBBarQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final ManyToManyBFooToBarAlias greenManyToManyBFooToBarsAlias = new ManyToManyBFooToBarAlias("a");
    private ForeignKeyListHolder<ManyToManyBBar, ManyToManyBFooToBar> greenManyToManyBFooToBars = new ForeignKeyListHolder<ManyToManyBBar, ManyToManyBFooToBar>((ManyToManyBBar) this, greenManyToManyBFooToBarsAlias, greenManyToManyBFooToBarsAlias.green);
    protected org.exigencecorp.domainobjects.Changed changed;

    protected ManyToManyBBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyBBar>("name", Shims.name));
        this.addRule(new MaxLength<ManyToManyBBar>("name", 100, Shims.name));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.getChanged().record("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.getChanged().record("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public List<ManyToManyBFooToBar> getGreenManyToManyBFooToBars() {
        return this.greenManyToManyBFooToBars.get();
    }

    public void addGreenManyToManyBFooToBar(ManyToManyBFooToBar o) {
        o.setGreenWithoutPercolation((ManyToManyBBar) this);
        this.addGreenManyToManyBFooToBarWithoutPercolation(o);
    }

    public void removeGreenManyToManyBFooToBar(ManyToManyBFooToBar o) {
        o.setGreenWithoutPercolation(null);
        this.removeGreenManyToManyBFooToBarWithoutPercolation(o);
    }

    protected void addGreenManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
        this.getChanged().record("greenManyToManyBFooToBars");
        this.greenManyToManyBFooToBars.add(o);
    }

    protected void removeGreenManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
        this.getChanged().record("greenManyToManyBFooToBars");
        this.greenManyToManyBFooToBars.remove(o);
    }

    public List<ManyToManyBFoo> getBlues() {
        List<ManyToManyBFoo> l = new ArrayList<ManyToManyBFoo>();
        for (ManyToManyBFooToBar o : this.getGreenManyToManyBFooToBars()) {
            l.add(o.getBlue());
        }
        return l;
    }

    public void addBlue(ManyToManyBFoo o) {
        ManyToManyBFooToBar a = new ManyToManyBFooToBar();
        a.setGreen((ManyToManyBBar) this);
        a.setBlue(o);
    }

    public void removeBlue(ManyToManyBFoo o) {
        for (ManyToManyBFooToBar a : Copy.shallow(this.getGreenManyToManyBFooToBars())) {
            if (a.getBlue().equals(o)) {
                a.setBlue(null);
                a.setGreen(null);
                UoW.delete(a);
            }
        }
    }

    public ManyToManyBBarChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ManyToManyBBarChanged((ManyToManyBBar) this);
        }
        return (ManyToManyBBarChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<ManyToManyBBar, java.lang.Integer> id = new Shim<ManyToManyBBar, java.lang.Integer>() {
            public void set(ManyToManyBBar instance, java.lang.Integer id) {
                ((ManyToManyBBarCodegen) instance).id = id;
            }
            public Integer get(ManyToManyBBar instance) {
                return ((ManyToManyBBarCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyBBar, java.lang.String> name = new Shim<ManyToManyBBar, java.lang.String>() {
            public void set(ManyToManyBBar instance, java.lang.String name) {
                ((ManyToManyBBarCodegen) instance).name = name;
            }
            public String get(ManyToManyBBar instance) {
                return ((ManyToManyBBarCodegen) instance).name;
            }
        };
        public static final Shim<ManyToManyBBar, java.lang.Integer> version = new Shim<ManyToManyBBar, java.lang.Integer>() {
            public void set(ManyToManyBBar instance, java.lang.Integer version) {
                ((ManyToManyBBarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyBBar instance) {
                return ((ManyToManyBBarCodegen) instance).version;
            }
        };
    }

    public static class ManyToManyBBarChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public ManyToManyBBarChanged(ManyToManyBBar instance) {
            super(instance);
        }
    }

}
