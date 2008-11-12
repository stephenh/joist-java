package features.domain;

import features.domain.queries.InheritanceBRootAlias;
import features.domain.queries.InheritanceBRootChildAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceBRootCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(InheritanceBRoot.class, new InheritanceBRootAlias("a"));
    }

    private Id<InheritanceBRoot> id = null;
    private String name = null;
    private Integer version = null;
    private List<InheritanceBRootChild> inheritanceBRootChilds;

    protected InheritanceBRootCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBRoot>("name", Shims.name));
        this.addRule(new MaxLength<InheritanceBRoot>("name", 100, Shims.name));
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

    public void setName(java.lang.String name) {
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

    public void addInheritanceBRootChild(InheritanceBRootChild o) {
        o.setInheritanceBRootWithoutPercolation((InheritanceBRoot) this);
        this.addInheritanceBRootChildWithoutPercolation(o);
    }

    public void addInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
        this.getInheritanceBRootChilds(); // hack
        this.recordIfChanged("inheritanceBRootChilds");
        this.inheritanceBRootChilds.add(o);
    }

    public void removeInheritanceBRootChild(InheritanceBRootChild o) {
        o.setInheritanceBRootWithoutPercolation(null);
        this.removeInheritanceBRootChildWithoutPercolation(o);
    }

    public void removeInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
        this.getInheritanceBRootChilds(); // hack
        this.recordIfChanged("inheritanceBRootChilds");
        this.inheritanceBRootChilds.remove(o);
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
        public static final Shim<InheritanceBRoot, java.lang.String> name = new Shim<InheritanceBRoot, java.lang.String>() {
            public void set(InheritanceBRoot instance, java.lang.String name) {
                ((InheritanceBRootCodegen) instance).name = name;
            }
            public String get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).name;
            }
        };
        public static final Shim<InheritanceBRoot, java.lang.Integer> version = new Shim<InheritanceBRoot, java.lang.Integer>() {
            public void set(InheritanceBRoot instance, java.lang.Integer version) {
                ((InheritanceBRootCodegen) instance).version = version;
            }
            public Integer get(InheritanceBRoot instance) {
                return ((InheritanceBRootCodegen) instance).version;
            }
        };
    }

}
