package features.domain;

import features.domain.InheritanceBRootAlias;
import features.domain.InheritanceBRootChildAlias;
import features.domain.queries.InheritanceBRootQueries;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class InheritanceBRootCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(InheritanceBRoot.class, new InheritanceBRootAlias("a"));
    }

    public static final InheritanceBRootQueries queries = new InheritanceBRootQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final InheritanceBRootChildAlias inheritanceBRootChildsAlias = new InheritanceBRootChildAlias("a");
    private ForeignKeyListHolder<InheritanceBRoot, InheritanceBRootChild> inheritanceBRootChilds = new ForeignKeyListHolder<InheritanceBRoot, InheritanceBRootChild>((InheritanceBRoot) this, inheritanceBRootChildsAlias, inheritanceBRootChildsAlias.inheritanceBRoot);

    protected InheritanceBRootCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBRoot>("name", Shims.name));
        this.addRule(new MaxLength<InheritanceBRoot>("name", 100, Shims.name));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
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
        return this.inheritanceBRootChilds.get();
    }

    public void addInheritanceBRootChild(InheritanceBRootChild o) {
        o.setInheritanceBRootWithoutPercolation((InheritanceBRoot) this);
        this.addInheritanceBRootChildWithoutPercolation(o);
    }

    public void addInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
        this.recordIfChanged("inheritanceBRootChilds");
        this.inheritanceBRootChilds.add(o);
    }

    public void removeInheritanceBRootChild(InheritanceBRootChild o) {
        o.setInheritanceBRootWithoutPercolation(null);
        this.removeInheritanceBRootChildWithoutPercolation(o);
    }

    public void removeInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
        this.recordIfChanged("inheritanceBRootChilds");
        this.inheritanceBRootChilds.remove(o);
    }

    public static class Shims {
        public static final Shim<InheritanceBRoot, java.lang.Integer> id = new Shim<InheritanceBRoot, java.lang.Integer>() {
            public void set(InheritanceBRoot instance, java.lang.Integer id) {
                ((InheritanceBRootCodegen) instance).id = id;
            }
            public Integer get(InheritanceBRoot instance) {
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
