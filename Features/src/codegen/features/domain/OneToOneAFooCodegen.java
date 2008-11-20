package features.domain;

import features.domain.OneToOneABarAlias;
import features.domain.OneToOneAFooAlias;
import features.domain.queries.OneToOneAFooQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class OneToOneAFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(OneToOneAFoo.class, new OneToOneAFooAlias("a"));
    }

    public static final OneToOneAFooQueries queries = new OneToOneAFooQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final OneToOneABarAlias oneToOneABarsAlias = new OneToOneABarAlias("a");
    private ForeignKeyListHolder<OneToOneAFoo, OneToOneABar> oneToOneABars = new ForeignKeyListHolder<OneToOneAFoo, OneToOneABar>((OneToOneAFoo) this, oneToOneABarsAlias, oneToOneABarsAlias.oneToOneAFoo);
    protected org.exigencecorp.domainobjects.Changed changed;

    protected OneToOneAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneAFoo>("name", Shims.name));
        this.addRule(new MaxLength<OneToOneAFoo>("name", 100, Shims.name));
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

    public static class Shims {
        public static final Shim<OneToOneAFoo, java.lang.Integer> id = new Shim<OneToOneAFoo, java.lang.Integer>() {
            public void set(OneToOneAFoo instance, java.lang.Integer id) {
                ((OneToOneAFooCodegen) instance).id = id;
            }
            public Integer get(OneToOneAFoo instance) {
                return ((OneToOneAFooCodegen) instance).id;
            }
        };
        public static final Shim<OneToOneAFoo, java.lang.String> name = new Shim<OneToOneAFoo, java.lang.String>() {
            public void set(OneToOneAFoo instance, java.lang.String name) {
                ((OneToOneAFooCodegen) instance).name = name;
            }
            public String get(OneToOneAFoo instance) {
                return ((OneToOneAFooCodegen) instance).name;
            }
        };
        public static final Shim<OneToOneAFoo, java.lang.Integer> version = new Shim<OneToOneAFoo, java.lang.Integer>() {
            public void set(OneToOneAFoo instance, java.lang.Integer version) {
                ((OneToOneAFooCodegen) instance).version = version;
            }
            public Integer get(OneToOneAFoo instance) {
                return ((OneToOneAFooCodegen) instance).version;
            }
        };
    }

    public static class OneToOneAFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
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
