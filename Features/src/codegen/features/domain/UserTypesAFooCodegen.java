package features.domain;

import com.domainlanguage.time.CalendarDate;
import features.domain.queries.UserTypesAFooQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Changed;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class UserTypesAFooCodegen extends AbstractDomainObject {

    protected static UserTypesAFooAlias alias;
    public static final UserTypesAFooQueries queries;
    private CalendarDate created = null;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    protected Changed changed;

    static {
        alias = new UserTypesAFooAlias("a");
        AliasRegistry.register(UserTypesAFoo.class, alias);
        queries = new UserTypesAFooQueries();
    }

    protected UserTypesAFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<UserTypesAFoo>("created", Shims.created));
        this.addRule(new NotNull<UserTypesAFoo>("name", Shims.name));
        this.addRule(new MaxLength<UserTypesAFoo>("name", 100, Shims.name));
    }

    public CalendarDate getCreated() {
        return this.created;
    }

    public void setCreated(CalendarDate created) {
        this.getChanged().record("created", this.created, created);
        this.created = created;
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

    public UserTypesAFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new UserTypesAFooChanged((UserTypesAFoo) this);
        }
        return (UserTypesAFooChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<UserTypesAFoo, CalendarDate> created = new Shim<UserTypesAFoo, CalendarDate>() {
            public void set(UserTypesAFoo instance, CalendarDate created) {
                ((UserTypesAFooCodegen) instance).created = created;
            }
            public CalendarDate get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).created;
            }
        };
        public static final Shim<UserTypesAFoo, Integer> id = new Shim<UserTypesAFoo, Integer>() {
            public void set(UserTypesAFoo instance, Integer id) {
                ((UserTypesAFooCodegen) instance).id = id;
            }
            public Integer get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).id;
            }
        };
        public static final Shim<UserTypesAFoo, String> name = new Shim<UserTypesAFoo, String>() {
            public void set(UserTypesAFoo instance, String name) {
                ((UserTypesAFooCodegen) instance).name = name;
            }
            public String get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).name;
            }
        };
        public static final Shim<UserTypesAFoo, Integer> version = new Shim<UserTypesAFoo, Integer>() {
            public void set(UserTypesAFoo instance, Integer version) {
                ((UserTypesAFooCodegen) instance).version = version;
            }
            public Integer get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).version;
            }
        };
    }

    public static class UserTypesAFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public UserTypesAFooChanged(UserTypesAFoo instance) {
            super(instance);
        }
        public boolean hasCreated() {
            return this.contains("created");
        }
        public CalendarDate getOriginalCreated() {
            return (com.domainlanguage.time.CalendarDate) this.getOriginal("created");
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
