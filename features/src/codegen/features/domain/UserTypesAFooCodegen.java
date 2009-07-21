package features.domain;

import bindgen.features.domain.UserTypesAFooBinding;
import com.domainlanguage.time.CalendarDate;
import features.domain.queries.UserTypesAFooQueries;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.domain.validation.rules.Rule;

public abstract class UserTypesAFooCodegen extends AbstractDomainObject {

    private static UserTypesAFooBinding b = new UserTypesAFooBinding();
    protected static UserTypesAFooAlias alias;
    public static final UserTypesAFooQueries queries;
    private CalendarDate created = null;
    private static Rule<UserTypesAFoo> createdNotNull = new NotNull<UserTypesAFoo>(b.created());
    private Integer id = null;
    private String name = null;
    private static Rule<UserTypesAFoo> nameNotNull = new NotNull<UserTypesAFoo>(b.name());
    private static Rule<UserTypesAFoo> nameMaxLength = new MaxLength<UserTypesAFoo>(b.name(), 100);
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
        this.addRule(createdNotNull);
        this.addRule(nameNotNull);
        this.addRule(nameMaxLength);
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

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<UserTypesAFoo, CalendarDate> created = new Shim<UserTypesAFoo, CalendarDate>() {
            public void set(UserTypesAFoo instance, CalendarDate created) {
                ((UserTypesAFooCodegen) instance).created = created;
            }
            public CalendarDate get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).created;
            }
            public String getName() {
                return "created";
            }
        };
        protected static final Shim<UserTypesAFoo, Integer> id = new Shim<UserTypesAFoo, Integer>() {
            public void set(UserTypesAFoo instance, Integer id) {
                ((UserTypesAFooCodegen) instance).id = id;
            }
            public Integer get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<UserTypesAFoo, String> name = new Shim<UserTypesAFoo, String>() {
            public void set(UserTypesAFoo instance, String name) {
                ((UserTypesAFooCodegen) instance).name = name;
            }
            public String get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<UserTypesAFoo, Integer> version = new Shim<UserTypesAFoo, Integer>() {
            public void set(UserTypesAFoo instance, Integer version) {
                ((UserTypesAFooCodegen) instance).version = version;
            }
            public Integer get(UserTypesAFoo instance) {
                return ((UserTypesAFooCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class UserTypesAFooChanged extends joist.domain.AbstractChanged {
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
