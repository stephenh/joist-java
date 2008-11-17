package features.domain;

import features.domain.UserTypesAFooAlias;
import features.domain.queries.UserTypesAFooQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class UserTypesAFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(UserTypesAFoo.class, new UserTypesAFooAlias("a"));
    }

    public static final UserTypesAFooQueries queries = new UserTypesAFooQueries();
    private com.domainlanguage.time.CalendarDate created = null;
    private Integer id = null;
    private String name = null;
    private Integer version = null;

    protected UserTypesAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<UserTypesAFoo>("created", Shims.created));
        this.addRule(new NotNull<UserTypesAFoo>("name", Shims.name));
        this.addRule(new MaxLength<UserTypesAFoo>("name", 100, Shims.name));
    }

    public com.domainlanguage.time.CalendarDate getCreated() {
        return this.created;
    }

    public void setCreated(com.domainlanguage.time.CalendarDate created) {
        this.recordIfChanged("created", this.created, created);
        this.created = created;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
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
        public static final Shim<UserTypesAFoo, com.domainlanguage.time.CalendarDate> created = new Shim<UserTypesAFoo, com.domainlanguage.time.CalendarDate>() {
            public void set(UserTypesAFoo instance, com.domainlanguage.time.CalendarDate created) {
                ((UserTypesAFooCodegen) instance).created = created;
            }
            public com.domainlanguage.time.CalendarDate get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).created;
            }
        };
        public static final Shim<UserTypesAFoo, java.lang.Integer> id = new Shim<UserTypesAFoo, java.lang.Integer>() {
            public void set(UserTypesAFoo instance, java.lang.Integer id) {
                ((UserTypesAFooCodegen) instance).id = id;
            }
            public Integer get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).id;
            }
        };
        public static final Shim<UserTypesAFoo, java.lang.String> name = new Shim<UserTypesAFoo, java.lang.String>() {
            public void set(UserTypesAFoo instance, java.lang.String name) {
                ((UserTypesAFooCodegen) instance).name = name;
            }
            public String get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).name;
            }
        };
        public static final Shim<UserTypesAFoo, java.lang.Integer> version = new Shim<UserTypesAFoo, java.lang.Integer>() {
            public void set(UserTypesAFoo instance, java.lang.Integer version) {
                ((UserTypesAFooCodegen) instance).version = version;
            }
            public Integer get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).version;
            }
        };
    }

}
