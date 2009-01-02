package features.domain;

import features.domain.queries.ManyToManyBFooQueries;
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

abstract class ManyToManyBFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyBFoo.class, new ManyToManyBFooAlias("a"));
    }

    public static final ManyToManyBFooQueries queries = new ManyToManyBFooQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final ManyToManyBFooToBarAlias blueManyToManyBFooToBarsAlias = new ManyToManyBFooToBarAlias("a");
    private ForeignKeyListHolder<ManyToManyBFoo, ManyToManyBFooToBar> blueManyToManyBFooToBars = new ForeignKeyListHolder<ManyToManyBFoo, ManyToManyBFooToBar>((ManyToManyBFoo) this, blueManyToManyBFooToBarsAlias, blueManyToManyBFooToBarsAlias.blue);
    protected Changed changed;

    protected ManyToManyBFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyBFoo>("name", Shims.name));
        this.addRule(new MaxLength<ManyToManyBFoo>("name", 100, Shims.name));
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

    public List<ManyToManyBFooToBar> getBlueManyToManyBFooToBars() {
        return this.blueManyToManyBFooToBars.get();
    }

    public void addBlueManyToManyBFooToBar(ManyToManyBFooToBar o) {
        o.setBlueWithoutPercolation((ManyToManyBFoo) this);
        this.addBlueManyToManyBFooToBarWithoutPercolation(o);
    }

    public void removeBlueManyToManyBFooToBar(ManyToManyBFooToBar o) {
        o.setBlueWithoutPercolation(null);
        this.removeBlueManyToManyBFooToBarWithoutPercolation(o);
    }

    protected void addBlueManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
        this.getChanged().record("blueManyToManyBFooToBars");
        this.blueManyToManyBFooToBars.add(o);
    }

    protected void removeBlueManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
        this.getChanged().record("blueManyToManyBFooToBars");
        this.blueManyToManyBFooToBars.remove(o);
    }

    public List<ManyToManyBBar> getGreens() {
        List<ManyToManyBBar> l = new ArrayList<ManyToManyBBar>();
        for (ManyToManyBFooToBar o : this.getBlueManyToManyBFooToBars()) {
            l.add(o.getGreen());
        }
        return l;
    }

    public void addGreen(ManyToManyBBar o) {
        ManyToManyBFooToBar a = new ManyToManyBFooToBar();
        a.setBlue((ManyToManyBFoo) this);
        a.setGreen(o);
    }

    public void removeGreen(ManyToManyBBar o) {
        for (ManyToManyBFooToBar a : Copy.shallow(this.getBlueManyToManyBFooToBars())) {
            if (a.getGreen().equals(o)) {
                a.setGreen(null);
                a.setBlue(null);
                UoW.delete(a);
            }
        }
    }

    public ManyToManyBFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ManyToManyBFooChanged((ManyToManyBFoo) this);
        }
        return (ManyToManyBFooChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<ManyToManyBFoo, Integer> id = new Shim<ManyToManyBFoo, Integer>() {
            public void set(ManyToManyBFoo instance, Integer id) {
                ((ManyToManyBFooCodegen) instance).id = id;
            }
            public Integer get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyBFoo, String> name = new Shim<ManyToManyBFoo, String>() {
            public void set(ManyToManyBFoo instance, String name) {
                ((ManyToManyBFooCodegen) instance).name = name;
            }
            public String get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).name;
            }
        };
        public static final Shim<ManyToManyBFoo, Integer> version = new Shim<ManyToManyBFoo, Integer>() {
            public void set(ManyToManyBFoo instance, Integer version) {
                ((ManyToManyBFooCodegen) instance).version = version;
            }
            public Integer get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).version;
            }
        };
    }

    public static class ManyToManyBFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public ManyToManyBFooChanged(ManyToManyBFoo instance) {
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
        public boolean hasBlueManyToManyBFooToBars() {
            return this.contains("blueManyToManyBFooToBars");
        }
    }

}
