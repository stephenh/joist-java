package features.domain;

import features.domain.mappers.InheritanceBRootAlias;
import features.domain.mappers.InheritanceBRootChildAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;

public abstract class InheritanceBRootChildCodegen extends AbstractDomainObject {

    private Id<InheritanceBRootChild> id = null;
    private String name = null;
    private Integer version = null;
    private InheritanceBRoot inheritanceBRoot = null;
    private Integer inheritanceBRootId = null;

    public Alias<? extends InheritanceBRootChild> newAlias(String alias) {
        return new InheritanceBRootChildAlias(alias);
    }

    public Id<InheritanceBRootChild> getId() {
        return this.id;
    }

    public void setId(Id<InheritanceBRootChild> id) {
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

    public InheritanceBRoot getInheritanceBRoot() {
        if (this.inheritanceBRoot == null && this.inheritanceBRootId != null && UoW.isOpen()) {
            InheritanceBRootAlias a = new InheritanceBRootAlias("a");
            this.inheritanceBRoot = Select.from(a).where(a.id.equals(this.inheritanceBRootId)).unique();
        }
        return this.inheritanceBRoot;
    }

    public void setInheritanceBRoot(InheritanceBRoot inheritanceBRoot) {
        this.recordIfChanged("inheritanceBRoot", this.inheritanceBRoot, inheritanceBRoot);
        this.inheritanceBRoot = inheritanceBRoot;
        if (inheritanceBRoot == null) {
            this.inheritanceBRootId = null;
        }
    }

    public static class Shims {
        public static final Shim<InheritanceBRootChild, Id<InheritanceBRootChild>> id = new Shim<InheritanceBRootChild, Id<InheritanceBRootChild>>() {
            public void set(InheritanceBRootChild instance, Id<InheritanceBRootChild> id) {
                ((InheritanceBRootChildCodegen) instance).id = id;
            }
            public Id<InheritanceBRootChild> get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).id;
            }
        };
        public static final Shim<InheritanceBRootChild, String> name = new Shim<InheritanceBRootChild, String>() {
            public void set(InheritanceBRootChild instance, String name) {
                ((InheritanceBRootChildCodegen) instance).name = name;
            }
            public String get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceBRootChild, Integer> version = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer version) {
                ((InheritanceBRootChildCodegen) instance).version = version;
            }
            public Integer get(InheritanceBRootChild instance) {
                return ((InheritanceBRootChildCodegen) instance).version;
            }
        };
        public static final Shim<InheritanceBRootChild, Integer> inheritanceBRootId = new Shim<InheritanceBRootChild, Integer>() {
            public void set(InheritanceBRootChild instance, Integer inheritanceBRootId) {
                ((InheritanceBRootChildCodegen) instance).inheritanceBRootId = inheritanceBRootId;
            }
            public Integer get(InheritanceBRootChild instance) {
                InheritanceBRootChildCodegen instanceCodegen = instance;
                if (instanceCodegen.inheritanceBRoot != null) {
                    return instanceCodegen.inheritanceBRoot.getId().intValue();
                }
                return ((InheritanceBRootChildCodegen) instance).inheritanceBRootId;
            }
        };
    }

}
