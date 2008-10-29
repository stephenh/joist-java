package features.domain;

import features.domain.mappers.ParentBChildFooAlias;
import features.domain.mappers.ParentBParentAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;

public abstract class ParentBChildFooCodegen extends AbstractDomainObject {

    private Id<ParentBChildFoo> id = null;
    private String name = null;
    private Integer version = null;
    private ParentBParent parentBParent = null;
    private Integer parentBParentId = null;

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
        if (this.parentBParent == null && this.parentBParentId != null && UoW.isOpen()) {
            ParentBParentAlias a = new ParentBParentAlias("a");
            this.parentBParent = Select.from(a).where(a.id.equals(this.parentBParentId)).unique();
        }
        return this.parentBParent;
    }

    public void setParentBParent(ParentBParent parentBParent) {
        this.recordIfChanged("parentBParent", this.parentBParent, parentBParent);
        this.parentBParent = parentBParent;
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
                ((ParentBChildFooCodegen) instance).parentBParentId = parentBParentId;
            }
            public Integer get(ParentBChildFoo instance) {
                ParentBChildFooCodegen instanceCodegen = instance;
                if (instanceCodegen.parentBParent != null) {
                    return instanceCodegen.parentBParent.getId().intValue();
                }
                return ((ParentBChildFooCodegen) instance).parentBParentId;
            }
        };
    }

}
