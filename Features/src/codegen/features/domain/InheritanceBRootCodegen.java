package features.domain;

import features.domain.mappers.InheritanceBRootAlias;
import features.domain.mappers.InheritanceBRootChildAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;

public abstract class InheritanceBRootCodegen extends AbstractDomainObject {

    private Id<InheritanceBRoot> id = null;
    private String name = null;
    private Integer version = null;
    private List<InheritanceBRootChild> inheritanceBRootChilds;

    public Alias<? extends InheritanceBRoot> newAlias(String alias) {
        return new InheritanceBRootAlias(alias);
    }

    public Id<InheritanceBRoot> getId() {
        return this.id;
    }

    public void setId(Id<InheritanceBRoot> id) {
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

    public List<InheritanceBRootChild> getInheritanceBRootChilds() {
        if (this.inheritanceBRootChilds == null) {
            if (UoW.isOpen() && this.getId() != null) {
                InheritanceBRootChildAlias a = new InheritanceBRootChildAlias("a");
                this.inheritanceBRootChilds = Select.from(a).where(a.inheritanceBRoot.equals(this.getId())).orderBy(a.id.asc()).list();
            } else {
                this.inheritanceBRootChilds = new ArrayList<InheritanceBRootChild>();
            }
        }
        return this.inheritanceBRootChilds;
    }

    public static class Shims {
        public static final Shim<InheritanceBRoot, Id<InheritanceBRoot>> id = new Shim<InheritanceBRoot, Id<InheritanceBRoot>>() {
            public void set(InheritanceBRoot instance, Id<InheritanceBRoot> id) {
                ((InheritanceBRootCodegen) instance).id = id;
            }
            public Id<InheritanceBRoot> get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).id;
            }
        };
        public static final Shim<InheritanceBRoot, String> name = new Shim<InheritanceBRoot, String>() {
            public void set(InheritanceBRoot instance, String name) {
                ((InheritanceBRootCodegen) instance).name = name;
            }
            public String get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceBRoot, Integer> version = new Shim<InheritanceBRoot, Integer>() {
            public void set(InheritanceBRoot instance, Integer version) {
                ((InheritanceBRootCodegen) instance).version = version;
            }
            public Integer get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).version;
            }
        };
    }

}
