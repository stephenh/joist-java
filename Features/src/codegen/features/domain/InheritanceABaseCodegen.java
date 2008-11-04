package features.domain;

import features.domain.mappers.InheritanceABaseAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;

public abstract class InheritanceABaseCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(InheritanceABase.class, new InheritanceABaseAlias("a"));
    }

    private Id<InheritanceABase> id = null;
    private String name = null;
    private Integer version = null;

    public Id<InheritanceABase> getId() {
        return this.id;
    }

    public void setId(Id<InheritanceABase> id) {
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
        public static final Shim<InheritanceABase, Id<InheritanceABase>> id = new Shim<InheritanceABase, Id<InheritanceABase>>() {
            public void set(InheritanceABase instance, Id<InheritanceABase> id) {
                ((InheritanceABaseCodegen) instance).id = id;
            }
            public Id<InheritanceABase> get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).id;
            }
        };
        public static final Shim<InheritanceABase, String> name = new Shim<InheritanceABase, String>() {
            public void set(InheritanceABase instance, String name) {
                ((InheritanceABaseCodegen) instance).name = name;
            }
            public String get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceABase, Integer> version = new Shim<InheritanceABase, Integer>() {
            public void set(InheritanceABase instance, Integer version) {
                ((InheritanceABaseCodegen) instance).version = version;
            }
            public Integer get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).version;
            }
        };
    }

}
