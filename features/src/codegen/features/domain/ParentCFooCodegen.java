package features.domain;

import features.domain.queries.ParentCFooQueries;
import java.util.List;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class ParentCFooCodegen extends AbstractDomainObject {

    protected static ParentCFooAlias alias;
    public static final ParentCFooQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<ParentCFoo, ParentCBar> firstParentParentCBars = new ForeignKeyListHolder<ParentCFoo, ParentCBar>((ParentCFoo) this, ParentCBarCodegen.alias, ParentCBarCodegen.alias.firstParent);
    private ForeignKeyListHolder<ParentCFoo, ParentCBar> secondParentParentCBars = new ForeignKeyListHolder<ParentCFoo, ParentCBar>((ParentCFoo) this, ParentCBarCodegen.alias, ParentCBarCodegen.alias.secondParent);
    protected Changed changed;

    static {
        alias = new ParentCFooAlias("a");
        AliasRegistry.register(ParentCFoo.class, alias);
        queries = new ParentCFooQueries();
    }

    protected ParentCFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentCFoo>(Shims.name));
        this.addRule(new MaxLength<ParentCFoo>(Shims.name, 100));
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

    public List<ParentCBar> getFirstParentParentCBars() {
        return this.firstParentParentCBars.get();
    }

    public void addFirstParentParentCBar(ParentCBar o) {
        o.setFirstParentWithoutPercolation((ParentCFoo) this);
        this.addFirstParentParentCBarWithoutPercolation(o);
    }

    public void removeFirstParentParentCBar(ParentCBar o) {
        o.setFirstParentWithoutPercolation(null);
        this.removeFirstParentParentCBarWithoutPercolation(o);
    }

    protected void addFirstParentParentCBarWithoutPercolation(ParentCBar o) {
        this.getChanged().record("firstParentParentCBars");
        this.firstParentParentCBars.add(o);
    }

    protected void removeFirstParentParentCBarWithoutPercolation(ParentCBar o) {
        this.getChanged().record("firstParentParentCBars");
        this.firstParentParentCBars.remove(o);
    }

    public List<ParentCBar> getSecondParentParentCBars() {
        return this.secondParentParentCBars.get();
    }

    public void addSecondParentParentCBar(ParentCBar o) {
        o.setSecondParentWithoutPercolation((ParentCFoo) this);
        this.addSecondParentParentCBarWithoutPercolation(o);
    }

    public void removeSecondParentParentCBar(ParentCBar o) {
        o.setSecondParentWithoutPercolation(null);
        this.removeSecondParentParentCBarWithoutPercolation(o);
    }

    protected void addSecondParentParentCBarWithoutPercolation(ParentCBar o) {
        this.getChanged().record("secondParentParentCBars");
        this.secondParentParentCBars.add(o);
    }

    protected void removeSecondParentParentCBarWithoutPercolation(ParentCBar o) {
        this.getChanged().record("secondParentParentCBars");
        this.secondParentParentCBars.remove(o);
    }

    public ParentCFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentCFooChanged((ParentCFoo) this);
        }
        return (ParentCFooChanged) this.changed;
    }

    static class Shims {
        protected static final Shim<ParentCFoo, Integer> id = new Shim<ParentCFoo, Integer>() {
            public void set(ParentCFoo instance, Integer id) {
                ((ParentCFooCodegen) instance).id = id;
            }
            public Integer get(ParentCFoo instance) {
                return ((ParentCFooCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentCFoo, String> name = new Shim<ParentCFoo, String>() {
            public void set(ParentCFoo instance, String name) {
                ((ParentCFooCodegen) instance).name = name;
            }
            public String get(ParentCFoo instance) {
                return ((ParentCFooCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<ParentCFoo, Integer> version = new Shim<ParentCFoo, Integer>() {
            public void set(ParentCFoo instance, Integer version) {
                ((ParentCFooCodegen) instance).version = version;
            }
            public Integer get(ParentCFoo instance) {
                return ((ParentCFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ParentCFooChanged extends joist.domain.AbstractChanged {
        public ParentCFooChanged(ParentCFoo instance) {
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
        public boolean hasFirstParentParentCBars() {
            return this.contains("firstParentParentCBars");
        }
        public boolean hasSecondParentParentCBars() {
            return this.contains("secondParentParentCBars");
        }
    }

}
