package features.domain;

import features.domain.mappers.ManyToManyAFooAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class ManyToManyAFooCodegen extends AbstractDomainObject {

    private Id<ManyToManyAFoo> id = null;
    private String name = null;
    private Integer version = null;
    private List<ManyToManyABar> manyToManyABars;

    public Alias<? extends ManyToManyAFoo> newAlias(String alias) {
        return new ManyToManyAFooAlias(alias);
    }

    public Id<ManyToManyAFoo> getId() {
        return this.id;
    }

    public void setId(Id<ManyToManyAFoo> id) {
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
        public static final Shim<ManyToManyAFoo, Id<ManyToManyAFoo>> id = new Shim<ManyToManyAFoo, Id<ManyToManyAFoo>>() {
            public void set(ManyToManyAFoo instance, Id<ManyToManyAFoo> id) {
                ((ManyToManyAFooCodegen) instance).id = id;
            }
            public Id<ManyToManyAFoo> get(ManyToManyAFoo instance) {
                return ((ManyToManyAFooCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyAFoo, String> name = new Shim<ManyToManyAFoo, String>() {
            public void set(ManyToManyAFoo instance, String name) {
                ((ManyToManyAFooCodegen) instance).name = name;
            }
            public String get(ManyToManyAFoo instance) {
                return ((ManyToManyAFooCodegen) instance).name;
            }
        };
        public static final Shim<ManyToManyAFoo, Integer> version = new Shim<ManyToManyAFoo, Integer>() {
            public void set(ManyToManyAFoo instance, Integer version) {
                ((ManyToManyAFooCodegen) instance).version = version;
            }
            public Integer get(ManyToManyAFoo instance) {
                return ((ManyToManyAFooCodegen) instance).version;
            }
        };
    }

}
