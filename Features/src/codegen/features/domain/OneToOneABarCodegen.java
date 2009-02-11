package features.domain;

import features.domain.queries.OneToOneABarQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Changed;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class OneToOneABarCodegen extends AbstractDomainObject {

    protected static OneToOneABarAlias alias;
    public static final OneToOneABarQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<OneToOneAFoo> oneToOneAFoo = new ForeignKeyHolder<OneToOneAFoo>(OneToOneAFoo.class);
    protected Changed changed;

    static {
        alias = new OneToOneABarAlias("a");
        AliasRegistry.register(OneToOneABar.class, alias);
        queries = new OneToOneABarQueries();
    }

    protected OneToOneABarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneABar>("name", Shims.name));
        this.addRule(new MaxLength<OneToOneABar>("name", 100, Shims.name));
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

    public OneToOneAFoo getOneToOneAFoo() {
        return this.oneToOneAFoo.get();
    }

    public void setOneToOneAFoo(OneToOneAFoo oneToOneAFoo) {
        if (this.oneToOneAFoo.get() != null) {
           this.oneToOneAFoo.get().removeOneToOneABarWithoutPercolation((OneToOneABar) this);
        }
        oneToOneAFoo.setOneToOneABar(null);
        this.setOneToOneAFooWithoutPercolation(oneToOneAFoo);
        if (this.oneToOneAFoo.get() != null) {
           this.oneToOneAFoo.get().addOneToOneABarWithoutPercolation((OneToOneABar) this);
        }
    }

    protected void setOneToOneAFooWithoutPercolation(OneToOneAFoo oneToOneAFoo) {
        this.getChanged().record("oneToOneAFoo", this.oneToOneAFoo, oneToOneAFoo);
        this.oneToOneAFoo.set(oneToOneAFoo);
    }

    public OneToOneABarChanged getChanged() {
        if (this.changed == null) {
            this.changed = new OneToOneABarChanged((OneToOneABar) this);
        }
        return (OneToOneABarChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<OneToOneABar, Integer> id = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer id) {
                ((OneToOneABarCodegen) instance).id = id;
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).id;
            }
        };
        public static final Shim<OneToOneABar, String> name = new Shim<OneToOneABar, String>() {
            public void set(OneToOneABar instance, String name) {
                ((OneToOneABarCodegen) instance).name = name;
            }
            public String get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).name;
            }
        };
        public static final Shim<OneToOneABar, Integer> version = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer version) {
                ((OneToOneABarCodegen) instance).version = version;
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).version;
            }
        };
        public static final Shim<OneToOneABar, Integer> oneToOneAFooId = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer oneToOneAFooId) {
                ((OneToOneABarCodegen) instance).oneToOneAFoo.setId(oneToOneAFooId);
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).oneToOneAFoo.getId();
            }
        };
    }

    public static class OneToOneABarChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public OneToOneABarChanged(OneToOneABar instance) {
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
        public boolean hasOneToOneAFoo() {
            return this.contains("oneToOneAFoo");
        }
        public OneToOneAFoo getOriginalOneToOneAFoo() {
            return (OneToOneAFoo) this.getOriginal("oneToOneAFoo");
        }
    }

}
