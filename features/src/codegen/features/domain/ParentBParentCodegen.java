package features.domain;

import features.domain.queries.ParentBParentQueries;
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

public abstract class ParentBParentCodegen extends AbstractDomainObject {

    protected static ParentBParentAlias alias;
    public static final ParentBParentQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<ParentBParent, ParentBChildBar> parentBChildBars = new ForeignKeyListHolder<ParentBParent, ParentBChildBar>((ParentBParent) this, ParentBChildBarCodegen.alias, ParentBChildBarCodegen.alias.parentBParent);
    private ForeignKeyListHolder<ParentBParent, ParentBChildFoo> parentBChildFoos = new ForeignKeyListHolder<ParentBParent, ParentBChildFoo>((ParentBParent) this, ParentBChildFooCodegen.alias, ParentBChildFooCodegen.alias.parentBParent);
    protected Changed changed;

    static {
        alias = new ParentBParentAlias("a");
        AliasRegistry.register(ParentBParent.class, alias);
        queries = new ParentBParentQueries();
    }

    protected ParentBParentCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentBParent>(Shims.name));
        this.addRule(new MaxLength<ParentBParent>(Shims.name, 100));
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

    public List<ParentBChildBar> getParentBChildBars() {
        return this.parentBChildBars.get();
    }

    public void setParentBChildBars(List<ParentBChildBar> parentBChildBars) {
        for (ParentBChildBar o : Copy.list(this.getParentBChildBars())) {
            this.removeParentBChildBar(o);
        }
        for (ParentBChildBar o : parentBChildBars) {
            this.addParentBChildBar(o);
        }
    }

    public void addParentBChildBar(ParentBChildBar o) {
        o.setParentBParentWithoutPercolation((ParentBParent) this);
        this.addParentBChildBarWithoutPercolation(o);
    }

    public void removeParentBChildBar(ParentBChildBar o) {
        o.setParentBParentWithoutPercolation(null);
        this.removeParentBChildBarWithoutPercolation(o);
    }

    protected void addParentBChildBarWithoutPercolation(ParentBChildBar o) {
        this.getChanged().record("parentBChildBars");
        this.parentBChildBars.add(o);
    }

    protected void removeParentBChildBarWithoutPercolation(ParentBChildBar o) {
        this.getChanged().record("parentBChildBars");
        this.parentBChildBars.remove(o);
    }

    public List<ParentBChildFoo> getParentBChildFoos() {
        return this.parentBChildFoos.get();
    }

    public void setParentBChildFoos(List<ParentBChildFoo> parentBChildFoos) {
        for (ParentBChildFoo o : Copy.list(this.getParentBChildFoos())) {
            this.removeParentBChildFoo(o);
        }
        for (ParentBChildFoo o : parentBChildFoos) {
            this.addParentBChildFoo(o);
        }
    }

    public void addParentBChildFoo(ParentBChildFoo o) {
        o.setParentBParentWithoutPercolation((ParentBParent) this);
        this.addParentBChildFooWithoutPercolation(o);
    }

    public void removeParentBChildFoo(ParentBChildFoo o) {
        o.setParentBParentWithoutPercolation(null);
        this.removeParentBChildFooWithoutPercolation(o);
    }

    protected void addParentBChildFooWithoutPercolation(ParentBChildFoo o) {
        this.getChanged().record("parentBChildFoos");
        this.parentBChildFoos.add(o);
    }

    protected void removeParentBChildFooWithoutPercolation(ParentBChildFoo o) {
        this.getChanged().record("parentBChildFoos");
        this.parentBChildFoos.remove(o);
    }

    public ParentBParentChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentBParentChanged((ParentBParent) this);
        }
        return (ParentBParentChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        for (ParentBChildBar o : Copy.list(this.getParentBChildBars())) {
            o.setParentBParentWithoutPercolation(null);
        }
        for (ParentBChildFoo o : Copy.list(this.getParentBChildFoos())) {
            o.setParentBParentWithoutPercolation(null);
        }
    }

    static class Shims {
        protected static final Shim<ParentBParent, Integer> id = new Shim<ParentBParent, Integer>() {
            public void set(ParentBParent instance, Integer id) {
                ((ParentBParentCodegen) instance).id = id;
            }
            public Integer get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentBParent, String> name = new Shim<ParentBParent, String>() {
            public void set(ParentBParent instance, String name) {
                ((ParentBParentCodegen) instance).name = name;
            }
            public String get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<ParentBParent, Integer> version = new Shim<ParentBParent, Integer>() {
            public void set(ParentBParent instance, Integer version) {
                ((ParentBParentCodegen) instance).version = version;
            }
            public Integer get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ParentBParentChanged extends joist.domain.AbstractChanged {
        public ParentBParentChanged(ParentBParent instance) {
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
        public boolean hasParentBChildBars() {
            return this.contains("parentBChildBars");
        }
        public boolean hasParentBChildFoos() {
            return this.contains("parentBChildFoos");
        }
    }

}
