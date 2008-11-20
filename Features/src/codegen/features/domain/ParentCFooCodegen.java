package features.domain;

import features.domain.ParentCBarAlias;
import features.domain.ParentCFooAlias;
import features.domain.queries.ParentCFooQueries;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class ParentCFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentCFoo.class, new ParentCFooAlias("a"));
    }

    public static final ParentCFooQueries queries = new ParentCFooQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final ParentCBarAlias firstParentParentCBarsAlias = new ParentCBarAlias("a");
    private ForeignKeyListHolder<ParentCFoo, ParentCBar> firstParentParentCBars = new ForeignKeyListHolder<ParentCFoo, ParentCBar>((ParentCFoo) this, firstParentParentCBarsAlias, firstParentParentCBarsAlias.firstParent);
    private static final ParentCBarAlias secondParentParentCBarsAlias = new ParentCBarAlias("a");
    private ForeignKeyListHolder<ParentCFoo, ParentCBar> secondParentParentCBars = new ForeignKeyListHolder<ParentCFoo, ParentCBar>((ParentCFoo) this, secondParentParentCBarsAlias, secondParentParentCBarsAlias.secondParent);
    protected org.exigencecorp.domainobjects.Changed changed;

    protected ParentCFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentCFoo>("name", Shims.name));
        this.addRule(new MaxLength<ParentCFoo>("name", 100, Shims.name));
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

    public static class Shims {
        public static final Shim<ParentCFoo, java.lang.Integer> id = new Shim<ParentCFoo, java.lang.Integer>() {
            public void set(ParentCFoo instance, java.lang.Integer id) {
                ((ParentCFooCodegen) instance).id = id;
            }
            public Integer get(ParentCFoo instance) {
                return ((ParentCFooCodegen) instance).id;
            }
        };
        public static final Shim<ParentCFoo, java.lang.String> name = new Shim<ParentCFoo, java.lang.String>() {
            public void set(ParentCFoo instance, java.lang.String name) {
                ((ParentCFooCodegen) instance).name = name;
            }
            public String get(ParentCFoo instance) {
                return ((ParentCFooCodegen) instance).name;
            }
        };
        public static final Shim<ParentCFoo, java.lang.Integer> version = new Shim<ParentCFoo, java.lang.Integer>() {
            public void set(ParentCFoo instance, java.lang.Integer version) {
                ((ParentCFooCodegen) instance).version = version;
            }
            public Integer get(ParentCFoo instance) {
                return ((ParentCFooCodegen) instance).version;
            }
        };
    }

    public static class ParentCFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
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
