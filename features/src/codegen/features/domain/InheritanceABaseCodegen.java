package features.domain;

import bindgen.features.domain.InheritanceABaseBinding;
import features.domain.queries.InheritanceABaseQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.domain.validation.rules.Rule;

public abstract class InheritanceABaseCodegen extends AbstractDomainObject {

    private static InheritanceABaseBinding b = new InheritanceABaseBinding();
    protected static InheritanceABaseAlias alias;
    public static final InheritanceABaseQueries queries;
    private Integer id = null;
    private String name = null;
    private static Rule<InheritanceABase> nameNotNull = new NotNull<InheritanceABase>(b.name());
    private static Rule<InheritanceABase> nameMaxLength = new MaxLength<InheritanceABase>(b.name(), 100);
    private Integer version = null;
    protected Changed changed;

    static {
        alias = new InheritanceABaseAlias("a");
        AliasRegistry.register(InheritanceABase.class, alias);
        queries = new InheritanceABaseQueries();
    }

    protected InheritanceABaseCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(nameNotNull);
        this.addRule(nameMaxLength);
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

    public InheritanceABaseChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceABaseChanged((InheritanceABase) this);
        }
        return (InheritanceABaseChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<InheritanceABase, Integer> id = new Shim<InheritanceABase, Integer>() {
            public void set(InheritanceABase instance, Integer id) {
                ((InheritanceABaseCodegen) instance).id = id;
            }
            public Integer get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<InheritanceABase, String> name = new Shim<InheritanceABase, String>() {
            public void set(InheritanceABase instance, String name) {
                ((InheritanceABaseCodegen) instance).name = name;
            }
            public String get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<InheritanceABase, Integer> version = new Shim<InheritanceABase, Integer>() {
            public void set(InheritanceABase instance, Integer version) {
                ((InheritanceABaseCodegen) instance).version = version;
            }
            public Integer get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class InheritanceABaseChanged extends joist.domain.AbstractChanged {
        public InheritanceABaseChanged(InheritanceABase instance) {
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
    }

}
