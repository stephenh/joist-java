package features.domain;

import features.domain.ValidationAFooAlias;
import features.domain.queries.ValidationAFooQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class ValidationAFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ValidationAFoo.class, new ValidationAFooAlias("a"));
    }

    public static final ValidationAFooQueries queries = new ValidationAFooQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    protected org.exigencecorp.domainobjects.Changed changed;

    protected ValidationAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ValidationAFoo>("name", Shims.name));
        this.addRule(new MaxLength<ValidationAFoo>("name", 100, Shims.name));
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

    public ValidationAFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ValidationAFooChanged((ValidationAFoo) this);
        }
        return (ValidationAFooChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<ValidationAFoo, java.lang.Integer> id = new Shim<ValidationAFoo, java.lang.Integer>() {
            public void set(ValidationAFoo instance, java.lang.Integer id) {
                ((ValidationAFooCodegen) instance).id = id;
            }
            public Integer get(ValidationAFoo instance) {
                return ((ValidationAFooCodegen) instance).id;
            }
        };
        public static final Shim<ValidationAFoo, java.lang.String> name = new Shim<ValidationAFoo, java.lang.String>() {
            public void set(ValidationAFoo instance, java.lang.String name) {
                ((ValidationAFooCodegen) instance).name = name;
            }
            public String get(ValidationAFoo instance) {
                return ((ValidationAFooCodegen) instance).name;
            }
        };
        public static final Shim<ValidationAFoo, java.lang.Integer> version = new Shim<ValidationAFoo, java.lang.Integer>() {
            public void set(ValidationAFoo instance, java.lang.Integer version) {
                ((ValidationAFooCodegen) instance).version = version;
            }
            public Integer get(ValidationAFoo instance) {
                return ((ValidationAFooCodegen) instance).version;
            }
        };
    }

    public static class ValidationAFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
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
