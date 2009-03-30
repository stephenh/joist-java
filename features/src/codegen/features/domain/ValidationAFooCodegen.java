package features.domain;

import features.domain.queries.ValidationAFooQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class ValidationAFooCodegen extends AbstractDomainObject {

    protected static ValidationAFooAlias alias;
    public static final ValidationAFooQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    protected Changed changed;

    static {
        alias = new ValidationAFooAlias("a");
        AliasRegistry.register(ValidationAFoo.class, alias);
        queries = new ValidationAFooQueries();
    }

    protected ValidationAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ValidationAFoo>(Shims.name));
        this.addRule(new MaxLength<ValidationAFoo>(Shims.name, 100));
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

    public ValidationAFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ValidationAFooChanged((ValidationAFoo) this);
        }
        return (ValidationAFooChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<ValidationAFoo, Integer> id = new Shim<ValidationAFoo, Integer>() {
            public void set(ValidationAFoo instance, Integer id) {
                ((ValidationAFooCodegen) instance).id = id;
            }
            public Integer get(ValidationAFoo instance) {
                return ((ValidationAFooCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        public static final Shim<ValidationAFoo, String> name = new Shim<ValidationAFoo, String>() {
            public void set(ValidationAFoo instance, String name) {
                ((ValidationAFooCodegen) instance).name = name;
            }
            public String get(ValidationAFoo instance) {
                return ((ValidationAFooCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        public static final Shim<ValidationAFoo, Integer> version = new Shim<ValidationAFoo, Integer>() {
            public void set(ValidationAFoo instance, Integer version) {
                ((ValidationAFooCodegen) instance).version = version;
            }
            public Integer get(ValidationAFoo instance) {
                return ((ValidationAFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ValidationAFooChanged extends joist.domain.AbstractChanged {
        public ValidationAFooChanged(ValidationAFoo instance) {
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
