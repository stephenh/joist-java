package features.domain;

import features.domain.queries.OneToOneABarQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class OneToOneABarCodegen extends AbstractDomainObject {

    public static final OneToOneABarQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private final ForeignKeyHolder<OneToOneAFoo> oneToOneAFoo = new ForeignKeyHolder<OneToOneAFoo>(OneToOneAFoo.class);
    protected Changed changed;

    static {
        Aliases.init();
        queries = new OneToOneABarQueries();
    }

    protected OneToOneABarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneABar>(Shims.name));
        this.addRule(new MaxLength<OneToOneABar>(Shims.name, 100));
        this.addRule(new NotNull<OneToOneABar>(Shims.oneToOneAFooId));
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

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setOneToOneAFoo(null);
    }

    static class Shims {
        protected static final Shim<OneToOneABar, Integer> id = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer id) {
                ((OneToOneABarCodegen) instance).id = id;
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<OneToOneABar, String> name = new Shim<OneToOneABar, String>() {
            public void set(OneToOneABar instance, String name) {
                ((OneToOneABarCodegen) instance).name = name;
            }
            public String get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<OneToOneABar, Integer> version = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer version) {
                ((OneToOneABarCodegen) instance).version = version;
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<OneToOneABar, Integer> oneToOneAFooId = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer oneToOneAFooId) {
                ((OneToOneABarCodegen) instance).oneToOneAFoo.setId(oneToOneAFooId);
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).oneToOneAFoo.getId();
            }
            public String getName() {
                return "oneToOneAFoo";
            }
        };
    }

    public static class OneToOneABarChanged extends joist.domain.AbstractChanged {
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
