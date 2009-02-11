package features.domain;

import features.domain.queries.InheritanceABaseQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Changed;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceABaseCodegen extends AbstractDomainObject {

    protected static InheritanceABaseAlias alias;
    public static final InheritanceABaseQueries queries;
    private Integer id = null;
    private String name = null;
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
        this.addRule(new NotNull<InheritanceABase>("name", Shims.name));
        this.addRule(new MaxLength<InheritanceABase>("name", 100, Shims.name));
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

    public static class Shims {
        public static final Shim<InheritanceABase, Integer> id = new Shim<InheritanceABase, Integer>() {
            public void set(InheritanceABase instance, Integer id) {
                ((InheritanceABaseCodegen) instance).id = id;
            }
            public Integer get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).id;
            }
        };
        public static final Shim<InheritanceABase, String> name = new Shim<InheritanceABase, String>() {
            public void set(InheritanceABase instance, String name) {
                ((InheritanceABaseCodegen) instance).name = name;
            }
            public String get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceABase, Integer> version = new Shim<InheritanceABase, Integer>() {
            public void set(InheritanceABase instance, Integer version) {
                ((InheritanceABaseCodegen) instance).version = version;
            }
            public Integer get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).version;
            }
        };
    }

    public static class InheritanceABaseChanged extends org.exigencecorp.domainobjects.AbstractChanged {
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
