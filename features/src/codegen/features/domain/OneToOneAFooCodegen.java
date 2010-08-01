package features.domain;

import features.domain.queries.OneToOneAFooQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class OneToOneAFooCodegen extends AbstractDomainObject {

    public static final OneToOneAFooQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<OneToOneAFoo, OneToOneABar> oneToOneABars = new ForeignKeyListHolder<OneToOneAFoo, OneToOneABar>((OneToOneAFoo) this, Aliases.oneToOneABar, Aliases.oneToOneABar.oneToOneAFoo);
    protected Changed changed;

    static {
        Aliases.init();
        queries = new OneToOneAFooQueries();
    }

    protected OneToOneAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneAFoo>(Shims.name));
        this.addRule(new MaxLength<OneToOneAFoo>(Shims.name, 100));
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

    public OneToOneABar getOneToOneABar() {
        return (this.oneToOneABars.get().size() == 0) ? null : this.oneToOneABars.get().get(0);
    }

    public void setOneToOneABar(OneToOneABar n) {
        OneToOneABar o = this.getOneToOneABar();
        if (o != null) {
            o.setOneToOneAFooWithoutPercolation(null);
            this.removeOneToOneABarWithoutPercolation(o);
        }
        if (n != null) {
            n.setOneToOneAFooWithoutPercolation((OneToOneAFoo) this);
            this.addOneToOneABarWithoutPercolation(n);
        }
    }

    protected void addOneToOneABarWithoutPercolation(OneToOneABar o) {
        this.getChanged().record("oneToOneABars");
        this.oneToOneABars.add(o);
    }

    protected void removeOneToOneABarWithoutPercolation(OneToOneABar o) {
        this.getChanged().record("oneToOneABars");
        this.oneToOneABars.remove(o);
    }

    public OneToOneAFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new OneToOneAFooChanged((OneToOneAFoo) this);
        }
        return (OneToOneAFooChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setOneToOneABar(null);
    }

    static class Shims {
        protected static final Shim<OneToOneAFoo, Integer> id = new Shim<OneToOneAFoo, Integer>() {
            public void set(OneToOneAFoo instance, Integer id) {
                ((OneToOneAFooCodegen) instance).id = id;
            }
            public Integer get(OneToOneAFoo instance) {
                return ((OneToOneAFooCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<OneToOneAFoo, String> name = new Shim<OneToOneAFoo, String>() {
            public void set(OneToOneAFoo instance, String name) {
                ((OneToOneAFooCodegen) instance).name = name;
            }
            public String get(OneToOneAFoo instance) {
                return ((OneToOneAFooCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<OneToOneAFoo, Integer> version = new Shim<OneToOneAFoo, Integer>() {
            public void set(OneToOneAFoo instance, Integer version) {
                ((OneToOneAFooCodegen) instance).version = version;
            }
            public Integer get(OneToOneAFoo instance) {
                return ((OneToOneAFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class OneToOneAFooChanged extends AbstractChanged {
        public OneToOneAFooChanged(OneToOneAFoo instance) {
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
        public boolean hasOneToOneABars() {
            return this.contains("oneToOneABars");
        }
    }

}
