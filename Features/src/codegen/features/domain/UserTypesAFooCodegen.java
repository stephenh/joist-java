package features.domain;

import features.domain.mappers.UserTypesAFooAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;

public abstract class UserTypesAFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(UserTypesAFoo.class, new UserTypesAFooAlias("a"));
    }

    private Id<UserTypesAFoo> id = null;
    private String name = null;
    private com.domainlanguage.time.CalendarDate created = null;
    private Integer version = null;

    public Id<UserTypesAFoo> getId() {
        return this.id;
    }

    public void setId(Id<UserTypesAFoo> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public com.domainlanguage.time.CalendarDate getCreated() {
        return this.created;
    }

    public void setCreated(com.domainlanguage.time.CalendarDate created) {
        this.recordIfChanged("created", this.created, created);
        this.created = created;
    }

    public Integer getVersion() {
        return this.version;
    }

    public static class Shims {
        public static final Shim<UserTypesAFoo, Id<UserTypesAFoo>> id = new Shim<UserTypesAFoo, Id<UserTypesAFoo>>() {
            public void set(UserTypesAFoo instance, Id<UserTypesAFoo> id) {
                ((UserTypesAFooCodegen) instance).id = id;
            }
            public Id<UserTypesAFoo> get(UserTypesAFoo instance) {
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
        public static final Shim<UserTypesAFoo, com.domainlanguage.time.CalendarDate> created = new Shim<UserTypesAFoo, com.domainlanguage.time.CalendarDate>() {
            public void set(UserTypesAFoo instance, com.domainlanguage.time.CalendarDate created) {
                ((UserTypesAFooCodegen) instance).created = created;
            }
            public com.domainlanguage.time.CalendarDate get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).created;
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
