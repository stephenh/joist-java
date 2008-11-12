package features.domain;

import features.domain.queries.PrimitivesAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class PrimitivesCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(Primitives.class, new PrimitivesAlias("a"));
    }

    private Id<Primitives> id = null;
    private boolean flag = false;
    private String name = null;
    private Integer version = null;

    protected PrimitivesCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<Primitives>("flag", Shims.flag));
        this.addRule(new NotNull<Primitives>("name", Shims.name));
        this.addRule(new MaxLength<Primitives>("name", 100, Shims.name));
    }

    public Id<Primitives> getId() {
        return this.id;
    }

    public void setId(Id<Primitives> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getCurrent().getIdentityMap().store(Primitives.class, this);
        }
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

    public void setName(java.lang.String name) {
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
        public static final Shim<Primitives, java.lang.String> name = new Shim<Primitives, java.lang.String>() {
            public void set(Primitives instance, java.lang.String name) {
                ((PrimitivesCodegen) instance).name = name;
            }
            public String get(Primitives instance) {
                return ((PrimitivesCodegen) instance).name;
            }
        };
        public static final Shim<Primitives, java.lang.Integer> version = new Shim<Primitives, java.lang.Integer>() {
            public void set(Primitives instance, java.lang.Integer version) {
                ((PrimitivesCodegen) instance).version = version;
            }
            public Integer get(Primitives instance) {
                return ((PrimitivesCodegen) instance).version;
            }
        };
    }

}
