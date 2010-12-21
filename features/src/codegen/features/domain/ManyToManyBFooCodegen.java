package features.domain;

import features.domain.queries.ManyToManyBFooQueries;
import java.util.ArrayList;
import java.util.List;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;

@SuppressWarnings("all")
public abstract class ManyToManyBFooCodegen extends AbstractDomainObject {

    public static final ManyToManyBFooQueries queries;
    private Long id = null;
    private String name = null;
    private Long version = null;
    private ForeignKeyListHolder<ManyToManyBFoo, ManyToManyBFooToBar> blueManyToManyBFooToBars = new ForeignKeyListHolder<ManyToManyBFoo, ManyToManyBFooToBar>((ManyToManyBFoo) this, Aliases.manyToManyBFooToBar(), Aliases.manyToManyBFooToBar().blue);
    protected Changed changed;

    static {
        Aliases.manyToManyBFoo();
        queries = new ManyToManyBFooQueries();
    }

    protected ManyToManyBFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ManyToManyBFoo>(Shims.name));
        this.addRule(new MaxLength<ManyToManyBFoo>(Shims.name, 100));
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public Long getVersion() {
        return this.version;
    }

    public List<ManyToManyBFooToBar> getBlueManyToManyBFooToBars() {
        return this.blueManyToManyBFooToBars.get();
    }

    public void setBlueManyToManyBFooToBars(List<ManyToManyBFooToBar> blueManyToManyBFooToBars) {
        for (ManyToManyBFooToBar o : Copy.list(this.getBlueManyToManyBFooToBars())) {
            this.removeBlueManyToManyBFooToBar(o);
        }
        for (ManyToManyBFooToBar o : blueManyToManyBFooToBars) {
            this.addBlueManyToManyBFooToBar(o);
        }
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

    public void setGreens(List<ManyToManyBBar> greens) {
        for (ManyToManyBBar o : Copy.list(this.getGreens())) {
            this.removeGreen(o);
        }
        for (ManyToManyBBar o : greens) {
            this.addGreen(o);
        }
    }

    public void addGreen(ManyToManyBBar o) {
        ManyToManyBFooToBar a = new ManyToManyBFooToBar();
        a.setBlue((ManyToManyBFoo) this);
        a.setGreen(o);
    }

    public void removeGreen(ManyToManyBBar o) {
        for (ManyToManyBFooToBar a : Copy.list(this.getBlueManyToManyBFooToBars())) {
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

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        for (ManyToManyBFooToBar o : Copy.list(this.getBlueManyToManyBFooToBars())) {
            o.setBlueWithoutPercolation(null);
        }
    }

    static class Shims {
        protected static final Shim<ManyToManyBFoo, Long> id = new Shim<ManyToManyBFoo, Long>() {
            public void set(ManyToManyBFoo instance, Long id) {
                ((ManyToManyBFooCodegen) instance).id = id;
            }
            public Long get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ManyToManyBFoo, String> name = new Shim<ManyToManyBFoo, String>() {
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
        protected static final Shim<ManyToManyBFoo, Long> version = new Shim<ManyToManyBFoo, Long>() {
            public void set(ManyToManyBFoo instance, Long version) {
                ((ManyToManyBFooCodegen) instance).version = version;
            }
            public Long get(ManyToManyBFoo instance) {
                return ((ManyToManyBFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ManyToManyBFooChanged extends AbstractChanged {
        public ManyToManyBFooChanged(ManyToManyBFoo instance) {
            super(instance);
        }
        public boolean hasId() {
            return this.contains("id");
        }
        public Long getOriginalId() {
            return (Long) this.getOriginal("id");
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
        public Long getOriginalVersion() {
            return (Long) this.getOriginal("version");
        }
        public boolean hasBlueManyToManyBFooToBars() {
            return this.contains("blueManyToManyBFooToBars");
        }
    }

}
