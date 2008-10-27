package features.domain;

import features.domain.mappers.ManyToManyABarAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class ManyToManyABarCodegen extends AbstractDomainObject {

    private Id<ManyToManyABar> id = null;
    private String name = null;
    private Integer version = null;
    private List<ManyToManyAFoo> manyToManyAFoos;

    public Alias<? extends ManyToManyABar> newAlias(String alias) {
        return new ManyToManyABarAlias(alias);
    }

    public Id<ManyToManyABar> getId() {
        return this.id;
    }

    public void setId(Id<ManyToManyABar> id) {
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
        public static final Shim<ManyToManyABar, Id<ManyToManyABar>> id = new Shim<ManyToManyABar, Id<ManyToManyABar>>() {
            public void set(ManyToManyABar instance, Id<ManyToManyABar> id) {
                ((ManyToManyABarCodegen) instance).id = id;
            }
            public Id<ManyToManyABar> get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyABar, String> name = new Shim<ManyToManyABar, String>() {
            public void set(ManyToManyABar instance, String name) {
                ((ManyToManyABarCodegen) instance).name = name;
            }
            public String get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).name;
            }
        };
        public static final Shim<ManyToManyABar, Integer> version = new Shim<ManyToManyABar, Integer>() {
            public void set(ManyToManyABar instance, Integer version) {
                ((ManyToManyABarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyABar instance) {
                return ((ManyToManyABarCodegen) instance).version;
            }
        };
    }

}
