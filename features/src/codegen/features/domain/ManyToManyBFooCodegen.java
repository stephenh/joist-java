package features.domain;

import features.domain.queries.ManyToManyBFooQueries;
import java.util.ArrayList;
import java.util.List;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;

public abstract class ManyToManyBFooCodegen extends AbstractDomainObject {

    protected static ManyToManyBFooAlias alias;
    public static final ManyToManyBFooQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<ManyToManyBFoo, ManyToManyBFooToBar> blueManyToManyBFooToBars = new ForeignKeyListHolder<ManyToManyBFoo, ManyToManyBFooToBar>((ManyToManyBFoo) this, ManyToManyBFooToBarCodegen.alias, ManyToManyBFooToBarCodegen.alias.blue);
    protected Changed changed;

    static {
        alias = new ManyToManyBFooAlias("a");
        AliasRegistry.register(ManyToManyBFoo.class, alias);
        queries = new ManyToManyBFooQueries();
    }

    protected ManyToManyBFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyBFoo>(Shims.name));
        this.addRule(new MaxLength<ManyToManyBFoo>(Shims.name, 100));
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
                if (UoW.isOpen()) {
                    UoW.delete(a);
                }
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
            public String getName() {
                return "id";
            }
        };
        public static final Shim<ManyToManyBFoo, String> name = new Shim<ManyToManyBFoo, String>() {
            public void set(ManyToManyBFoo instance, String name) {
                ((ManyToManyBFooCodegen) instance).name = name;
            }
            public String get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        public static final Shim<ManyToManyBFoo, Integer> version = new Shim<ManyToManyBFoo, Integer>() {
            public void set(ManyToManyBFoo instance, Integer version) {
                ((ManyToManyBFooCodegen) instance).version = version;
            }
            public Integer get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ManyToManyBFooChanged extends joist.domain.AbstractChanged {
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
