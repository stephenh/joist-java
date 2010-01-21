package features.domain;

import features.domain.queries.InheritanceBRootQueries;
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

public abstract class InheritanceBRootCodegen extends AbstractDomainObject {

    protected static InheritanceBRootAlias alias;
    public static final InheritanceBRootQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<InheritanceBRoot, InheritanceBRootChild> inheritanceBRootChilds = new ForeignKeyListHolder<InheritanceBRoot, InheritanceBRootChild>((InheritanceBRoot) this, InheritanceBRootChildCodegen.alias, InheritanceBRootChildCodegen.alias.inheritanceBRoot);
    protected Changed changed;

    static {
        alias = new InheritanceBRootAlias("a");
        AliasRegistry.register(InheritanceBRoot.class, alias);
        queries = new InheritanceBRootQueries();
        try {
           Class.forName("features.domain.InheritanceBRootChild");
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }

    protected InheritanceBRootCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBRoot>(Shims.name));
        this.addRule(new MaxLength<InheritanceBRoot>(Shims.name, 100));
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

    public List<InheritanceBRootChild> getInheritanceBRootChilds() {
        return this.inheritanceBRootChilds.get();
    }

    public void setInheritanceBRootChilds(List<InheritanceBRootChild> inheritanceBRootChilds) {
        for (InheritanceBRootChild o : Copy.list(this.getInheritanceBRootChilds())) {
            this.removeInheritanceBRootChild(o);
        }
        for (InheritanceBRootChild o : inheritanceBRootChilds) {
            this.addInheritanceBRootChild(o);
        }
    }

    public void addInheritanceBRootChild(InheritanceBRootChild o) {
        o.setInheritanceBRootWithoutPercolation((InheritanceBRoot) this);
        this.addInheritanceBRootChildWithoutPercolation(o);
    }

    public void removeInheritanceBRootChild(InheritanceBRootChild o) {
        o.setInheritanceBRootWithoutPercolation(null);
        this.removeInheritanceBRootChildWithoutPercolation(o);
    }

    protected void addInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
        this.getChanged().record("inheritanceBRootChilds");
        this.inheritanceBRootChilds.add(o);
    }

    protected void removeInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
        this.getChanged().record("inheritanceBRootChilds");
        this.inheritanceBRootChilds.remove(o);
    }

    public InheritanceBRootChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceBRootChanged((InheritanceBRoot) this);
        }
        return (InheritanceBRootChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        for (InheritanceBRootChild o : Copy.list(this.getInheritanceBRootChilds())) {
            o.setInheritanceBRootWithoutPercolation(null);
        }
    }

    static class Shims {
        protected static final Shim<InheritanceBRoot, Integer> id = new Shim<InheritanceBRoot, Integer>() {
            public void set(InheritanceBRoot instance, Integer id) {
                ((InheritanceBRootCodegen) instance).id = id;
            }
            public Integer get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<InheritanceBRoot, String> name = new Shim<InheritanceBRoot, String>() {
            public void set(InheritanceBRoot instance, String name) {
                ((InheritanceBRootCodegen) instance).name = name;
            }
            public String get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<InheritanceBRoot, Integer> version = new Shim<InheritanceBRoot, Integer>() {
            public void set(InheritanceBRoot instance, Integer version) {
                ((InheritanceBRootCodegen) instance).version = version;
            }
            public Integer get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class InheritanceBRootChanged extends joist.domain.AbstractChanged {
        public InheritanceBRootChanged(InheritanceBRoot instance) {
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
        public boolean hasInheritanceBRootChilds() {
            return this.contains("inheritanceBRootChilds");
        }
    }

}
