package features.domain;

import features.domain.queries.InheritanceABaseAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceABaseCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(InheritanceABase.class, new InheritanceABaseAlias("a"));
    }

    private Id<InheritanceABase> id = null;
    private String name = null;
    private Integer version = null;

    protected InheritanceABaseCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceABase>("name", Shims.name));
        this.addRule(new MaxLength<InheritanceABase>("name", 100, Shims.name));
    }

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

    public void setName(java.lang.String name) {
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
        public static final Shim<InheritanceABase, java.lang.String> name = new Shim<InheritanceABase, java.lang.String>() {
            public void set(InheritanceABase instance, java.lang.String name) {
                ((InheritanceABaseCodegen) instance).name = name;
            }
            public String get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceABase, java.lang.Integer> version = new Shim<InheritanceABase, java.lang.Integer>() {
            public void set(InheritanceABase instance, java.lang.Integer version) {
                ((InheritanceABaseCodegen) instance).version = version;
            }
            public Integer get(InheritanceABase instance) {
                return ((InheritanceABaseCodegen) instance).version;
            }
        };
    }

}
