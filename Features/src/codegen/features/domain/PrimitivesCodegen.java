package features.domain;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

import features.domain.mappers.PrimitivesAlias;

public abstract class PrimitivesCodegen extends AbstractDomainObject {

    private Id<Primitives> id = null;
    private boolean flag = false;
    private String name = null;
    private Integer version = null;

    public Alias<Primitives> newAlias(String alias) {
        return new PrimitivesAlias(alias);
    }

    public Id<Primitives> getId() {
        return this.id;
    }

    public void setId(Id<Primitives> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.recordIfChanged("flag", this.flag, flag);
        this.flag = flag;
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
        public static final Shim<Primitives, Id<Primitives>> id = new Shim<Primitives, Id<Primitives>>() {
            public void set(Primitives instance, Id<Primitives> id) {
                ((PrimitivesCodegen) instance).id = id;
            }

            public Id<Primitives> get(Primitives instance) {
                return ((PrimitivesCodegen) instance).id;
            }
        };
        public static final Shim<Primitives, Boolean> flag = new Shim<Primitives, Boolean>() {
            public void set(Primitives instance, Boolean flag) {
                ((PrimitivesCodegen) instance).flag = flag;
            }

            public Boolean get(Primitives instance) {
                return ((PrimitivesCodegen) instance).flag;
            }
        };
        public static final Shim<Primitives, String> name = new Shim<Primitives, String>() {
            public void set(Primitives instance, String name) {
                ((PrimitivesCodegen) instance).name = name;
            }

            public String get(Primitives instance) {
                return ((PrimitivesCodegen) instance).name;
            }
        };
        public static final Shim<Primitives, Integer> version = new Shim<Primitives, Integer>() {
            public void set(Primitives instance, Integer version) {
                ((PrimitivesCodegen) instance).version = version;
            }

            public Integer get(Primitives instance) {
                return ((PrimitivesCodegen) instance).version;
            }
        };
    }

}
