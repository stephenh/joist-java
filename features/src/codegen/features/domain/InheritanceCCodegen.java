package features.domain;

import features.domain.queries.InheritanceCQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class InheritanceCCodegen extends AbstractDomainObject {

    public static final InheritanceCQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    protected Changed changed;

    static {
        Aliases.init();
        queries = new InheritanceCQueries();
    }

    protected InheritanceCCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceC>(Shims.name));
        this.addRule(new MaxLength<InheritanceC>(Shims.name, 100));
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

    public InheritanceCChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceCChanged((InheritanceC) this);
        }
        return (InheritanceCChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<InheritanceC, Integer> id = new Shim<InheritanceC, Integer>() {
            public void set(InheritanceC instance, Integer id) {
                ((InheritanceCCodegen) instance).id = id;
            }
            public Integer get(InheritanceC instance) {
                return ((InheritanceCCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<InheritanceC, String> name = new Shim<InheritanceC, String>() {
            public void set(InheritanceC instance, String name) {
                ((InheritanceCCodegen) instance).name = name;
            }
            public String get(InheritanceC instance) {
                return ((InheritanceCCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<InheritanceC, Integer> version = new Shim<InheritanceC, Integer>() {
            public void set(InheritanceC instance, Integer version) {
                ((InheritanceCCodegen) instance).version = version;
            }
            public Integer get(InheritanceC instance) {
                return ((InheritanceCCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class InheritanceCChanged extends AbstractChanged {
        public InheritanceCChanged(InheritanceC instance) {
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
