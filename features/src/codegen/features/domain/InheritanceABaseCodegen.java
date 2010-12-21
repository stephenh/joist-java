package features.domain;

import features.domain.queries.InheritanceABaseQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceABaseCodegen extends AbstractDomainObject {

    public static final InheritanceABaseQueries queries;
    private Long id = null;
    private String name = null;
    private Long version = null;
    protected Changed changed;

    static {
        Aliases.inheritanceABase();
        queries = new InheritanceABaseQueries();
    }

    protected InheritanceABaseCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceABase>(Shims.name));
        this.addRule(new MaxLength<InheritanceABase>(Shims.name, 100));
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public Long getVersion() {
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
        protected static final Shim<InheritanceABase, Long> id = new Shim<InheritanceABase, Long>() {
            public void set(InheritanceABase instance, Long id) {
                ((InheritanceABaseCodegen) instance).id = id;
            }
            public Long get(InheritanceABase instance) {
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
        protected static final Shim<InheritanceABase, Long> version = new Shim<InheritanceABase, Long>() {
            public void set(InheritanceABase instance, Long version) {
                ((InheritanceABaseCodegen) instance).version = version;
            }
            public Long get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class InheritanceABaseChanged extends AbstractChanged {
        public InheritanceABaseChanged(InheritanceABase instance) {
            super(instance);
        }
        public boolean hasId() {
            return this.contains("id");
        }
        public Long getOriginalId() {
            return (Long) this.getOriginal("id");
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
        public Long getOriginalVersion() {
            return (Long) this.getOriginal("version");
        }
    }

}
