package features.domain;

import features.domain.queries.ValidationAFooAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class ValidationAFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ValidationAFoo.class, new ValidationAFooAlias("a"));
    }

    private Id<ValidationAFoo> id = null;
    private String name = null;
    private Integer version = null;

    protected ValidationAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ValidationAFoo>("name", Shims.name));
        this.addRule(new MaxLength<ValidationAFoo>("name", 100, Shims.name));
    }

    public Id<ValidationAFoo> getId() {
        return this.id;
    }

    public void setId(Id<ValidationAFoo> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getCurrent().getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public static class Shims {
        public static final Shim<ValidationAFoo, Id<ValidationAFoo>> id = new Shim<ValidationAFoo, Id<ValidationAFoo>>() {
            public void set(ValidationAFoo instance, Id<ValidationAFoo> id) {
                ((ValidationAFooCodegen) instance).id = id;
            }
            public Id<ValidationAFoo> get(ValidationAFoo instance) {
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

}
