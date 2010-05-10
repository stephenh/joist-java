package features.domain;

import features.domain.queries.OneToOneBBarQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class OneToOneBBarCodegen extends AbstractDomainObject {

    public static final OneToOneBBarQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private final ForeignKeyHolder<OneToOneBFoo> oneToOneBFoo = new ForeignKeyHolder<OneToOneBFoo>(OneToOneBFoo.class);
    protected Changed changed;

    static {
        Aliases.init();
        queries = new OneToOneBBarQueries();
    }

    protected OneToOneBBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneBBar>(Shims.name));
        this.addRule(new MaxLength<OneToOneBBar>(Shims.name, 100));
        this.addRule(new NotNull<OneToOneBBar>(Shims.oneToOneBFooId));
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

    public OneToOneBFoo getOneToOneBFoo() {
        return this.oneToOneBFoo.get();
    }

    public void setOneToOneBFoo(OneToOneBFoo oneToOneBFoo) {
        if (this.oneToOneBFoo.get() != null) {
           this.oneToOneBFoo.get().removeOneToOneBBarWithoutPercolation((OneToOneBBar) this);
        }
        this.setOneToOneBFooWithoutPercolation(oneToOneBFoo);
        if (this.oneToOneBFoo.get() != null) {
           this.oneToOneBFoo.get().addOneToOneBBarWithoutPercolation((OneToOneBBar) this);
        }
    }

    protected void setOneToOneBFooWithoutPercolation(OneToOneBFoo oneToOneBFoo) {
        this.getChanged().record("oneToOneBFoo", this.oneToOneBFoo, oneToOneBFoo);
        this.oneToOneBFoo.set(oneToOneBFoo);
    }

    public OneToOneBBarChanged getChanged() {
        if (this.changed == null) {
            this.changed = new OneToOneBBarChanged((OneToOneBBar) this);
        }
        return (OneToOneBBarChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setOneToOneBFoo(null);
    }

    static class Shims {
        protected static final Shim<OneToOneBBar, Integer> id = new Shim<OneToOneBBar, Integer>() {
            public void set(OneToOneBBar instance, Integer id) {
                ((OneToOneBBarCodegen) instance).id = id;
            }
            public Integer get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<OneToOneBBar, String> name = new Shim<OneToOneBBar, String>() {
            public void set(OneToOneBBar instance, String name) {
                ((OneToOneBBarCodegen) instance).name = name;
            }
            public String get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<OneToOneBBar, Integer> version = new Shim<OneToOneBBar, Integer>() {
            public void set(OneToOneBBar instance, Integer version) {
                ((OneToOneBBarCodegen) instance).version = version;
            }
            public Integer get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<OneToOneBBar, Integer> oneToOneBFooId = new Shim<OneToOneBBar, Integer>() {
            public void set(OneToOneBBar instance, Integer oneToOneBFooId) {
                ((OneToOneBBarCodegen) instance).oneToOneBFoo.setId(oneToOneBFooId);
            }
            public Integer get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).oneToOneBFoo.getId();
            }
            public String getName() {
                return "oneToOneBFoo";
            }
        };
    }

    public static class OneToOneBBarChanged extends AbstractChanged {
        public OneToOneBBarChanged(OneToOneBBar instance) {
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
        public boolean hasOneToOneBFoo() {
            return this.contains("oneToOneBFoo");
        }
        public OneToOneBFoo getOriginalOneToOneBFoo() {
            return (OneToOneBFoo) this.getOriginal("oneToOneBFoo");
        }
    }

}
