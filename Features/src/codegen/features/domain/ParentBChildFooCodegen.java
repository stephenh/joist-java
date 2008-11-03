package features.domain;

import features.domain.mappers.ParentBChildFooAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class ParentBChildFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentBChildFoo.class, new ParentBChildFooAlias("a"));
    }

    private Id<ParentBChildFoo> id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<ParentBParent> parentBParent = new ForeignKeyHolder<ParentBParent>(ParentBParent.class);

    public Alias<? extends ParentBChildFoo> newAlias(String alias) {
        return new ParentBChildFooAlias(alias);
    }

    public Id<ParentBChildFoo> getId() {
        return this.id;
    }

    public void setId(Id<ParentBChildFoo> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public ParentBParent getParentBParent() {
        return this.parentBParent.get();
    }

    public void setParentBParent(ParentBParent parentBParent) {
        this.recordIfChanged("parentBParent", this.parentBParent, parentBParent);
        this.parentBParent.set(parentBParent);
    }

    public static class Shims {
        public static final Shim<ParentBChildFoo, Id<ParentBChildFoo>> id = new Shim<ParentBChildFoo, Id<ParentBChildFoo>>() {
            public void set(ParentBChildFoo instance, Id<ParentBChildFoo> id) {
                ((ParentBChildFooCodegen) instance).id = id;
            }
            public Id<ParentBChildFoo> get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).id;
            }
        };
        public static final Shim<ParentBChildFoo, String> name = new Shim<ParentBChildFoo, String>() {
            public void set(ParentBChildFoo instance, String name) {
                ((ParentBChildFooCodegen) instance).name = name;
            }
            public String get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).name;
            }
        };
        public static final Shim<ParentBChildFoo, Integer> version = new Shim<ParentBChildFoo, Integer>() {
            public void set(ParentBChildFoo instance, Integer version) {
                ((ParentBChildFooCodegen) instance).version = version;
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).version;
            }
        };
        public static final Shim<ParentBChildFoo, Integer> parentBParentId = new Shim<ParentBChildFoo, Integer>() {
            public void set(ParentBChildFoo instance, Integer parentBParentId) {
                ((ParentBChildFooCodegen) instance).parentBParent.setId(parentBParentId);
            }
            public Integer get(ParentBChildFoo instance) {
                return ((ParentBChildFooCodegen) instance).parentBParent.getId();
            }
        };
    }

}