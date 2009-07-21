package features.domain;

import bindgen.features.domain.ParentCBarBinding;
import features.domain.queries.ParentCBarQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.domain.validation.rules.Rule;

public abstract class ParentCBarCodegen extends AbstractDomainObject {

    private static ParentCBarBinding b = new ParentCBarBinding();
    protected static ParentCBarAlias alias;
    public static final ParentCBarQueries queries;
    private Integer id = null;
    private String name = null;
    private static Rule<ParentCBar> nameNotNull = new NotNull<ParentCBar>(b.name());
    private static Rule<ParentCBar> nameMaxLength = new MaxLength<ParentCBar>(b.name(), 100);
    private Integer version = null;
    private ForeignKeyHolder<ParentCFoo> firstParent = new ForeignKeyHolder<ParentCFoo>(ParentCFoo.class);
    private static Rule<ParentCBar> firstParentNotNull = new NotNull<ParentCBar>(b.firstParent());
    private ForeignKeyHolder<ParentCFoo> secondParent = new ForeignKeyHolder<ParentCFoo>(ParentCFoo.class);
    private static Rule<ParentCBar> secondParentNotNull = new NotNull<ParentCBar>(b.secondParent());
    protected Changed changed;

    static {
        alias = new ParentCBarAlias("a");
        AliasRegistry.register(ParentCBar.class, alias);
        queries = new ParentCBarQueries();
    }

    protected ParentCBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(nameNotNull);
        this.addRule(nameMaxLength);
        this.addRule(firstParentNotNull);
        this.addRule(secondParentNotNull);
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

    public ParentCFoo getFirstParent() {
        return this.firstParent.get();
    }

    public void setFirstParent(ParentCFoo firstParent) {
        if (this.firstParent.get() != null) {
           this.firstParent.get().removeFirstParentParentCBarWithoutPercolation((ParentCBar) this);
        }
        this.setFirstParentWithoutPercolation(firstParent);
        if (this.firstParent.get() != null) {
           this.firstParent.get().addFirstParentParentCBarWithoutPercolation((ParentCBar) this);
        }
    }

    protected void setFirstParentWithoutPercolation(ParentCFoo firstParent) {
        this.getChanged().record("firstParent", this.firstParent, firstParent);
        this.firstParent.set(firstParent);
    }

    public ParentCFoo getSecondParent() {
        return this.secondParent.get();
    }

    public void setSecondParent(ParentCFoo secondParent) {
        if (this.secondParent.get() != null) {
           this.secondParent.get().removeSecondParentParentCBarWithoutPercolation((ParentCBar) this);
        }
        this.setSecondParentWithoutPercolation(secondParent);
        if (this.secondParent.get() != null) {
           this.secondParent.get().addSecondParentParentCBarWithoutPercolation((ParentCBar) this);
        }
    }

    protected void setSecondParentWithoutPercolation(ParentCFoo secondParent) {
        this.getChanged().record("secondParent", this.secondParent, secondParent);
        this.secondParent.set(secondParent);
    }

    public ParentCBarChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentCBarChanged((ParentCBar) this);
        }
        return (ParentCBarChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        this.setFirstParent(null);
        this.setSecondParent(null);
    }

    static class Shims {
        protected static final Shim<ParentCBar, Integer> id = new Shim<ParentCBar, Integer>() {
            public void set(ParentCBar instance, Integer id) {
                ((ParentCBarCodegen) instance).id = id;
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentCBar, String> name = new Shim<ParentCBar, String>() {
            public void set(ParentCBar instance, String name) {
                ((ParentCBarCodegen) instance).name = name;
            }
            public String get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<ParentCBar, Integer> version = new Shim<ParentCBar, Integer>() {
            public void set(ParentCBar instance, Integer version) {
                ((ParentCBarCodegen) instance).version = version;
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
        protected static final Shim<ParentCBar, Integer> firstParentId = new Shim<ParentCBar, Integer>() {
            public void set(ParentCBar instance, Integer firstParentId) {
                ((ParentCBarCodegen) instance).firstParent.setId(firstParentId);
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).firstParent.getId();
            }
            public String getName() {
                return "firstParent";
            }
        };
        protected static final Shim<ParentCBar, Integer> secondParentId = new Shim<ParentCBar, Integer>() {
            public void set(ParentCBar instance, Integer secondParentId) {
                ((ParentCBarCodegen) instance).secondParent.setId(secondParentId);
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).secondParent.getId();
            }
            public String getName() {
                return "secondParent";
            }
        };
    }

    public static class ParentCBarChanged extends joist.domain.AbstractChanged {
        public ParentCBarChanged(ParentCBar instance) {
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
        public boolean hasFirstParent() {
            return this.contains("firstParent");
        }
        public ParentCFoo getOriginalFirstParent() {
            return (ParentCFoo) this.getOriginal("firstParent");
        }
        public boolean hasSecondParent() {
            return this.contains("secondParent");
        }
        public ParentCFoo getOriginalSecondParent() {
            return (ParentCFoo) this.getOriginal("secondParent");
        }
    }

}
