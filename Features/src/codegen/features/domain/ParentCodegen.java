package features.domain;

import features.domain.mappers.ParentAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class ParentCodegen extends AbstractDomainObject {

    private Id<Parent> id = null;
    private String name = null;
    private Integer version = null;

    public Alias<? extends Parent> newAlias(String alias) {
        return new ParentAlias(alias);
    }

    public Id<Parent> getId() {
        return this.id;
    }

    public void setId(Id<Parent> id) {
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

    public static class Shims {
        public static final Shim<Parent, Id<Parent>> id = new Shim<Parent, Id<Parent>>() {
            public void set(Parent instance, Id<Parent> id) {
                ((ParentCodegen) instance).id = id;
            }
            public Id<Parent> get(Parent instance) {
                return ((ParentCodegen) instance).id;
            }
        };
        public static final Shim<Parent, String> name = new Shim<Parent, String>() {
            public void set(Parent instance, String name) {
                ((ParentCodegen) instance).name = name;
            }
            public String get(Parent instance) {
                return ((ParentCodegen) instance).name;
            }
        };
        public static final Shim<Parent, Integer> version = new Shim<Parent, Integer>() {
            public void set(Parent instance, Integer version) {
                ((ParentCodegen) instance).version = version;
            }
            public Integer get(Parent instance) {
                return ((ParentCodegen) instance).version;
            }
        };
    }

}
