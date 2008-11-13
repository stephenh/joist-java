package features.domain;

import features.domain.ParentBChildBarAlias;
import features.domain.ParentBChildFooAlias;
import features.domain.ParentBParentAlias;
import features.domain.queries.ParentBParentQueries;
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

abstract class ParentBParentCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentBParent.class, new ParentBParentAlias("a"));
    }

    public static final ParentBParentQueries queries = new ParentBParentQueries();
    private Id<ParentBParent> id = null;
    private String name = null;
    private Integer version = null;
    private List<ParentBChildFoo> parentBChildFoos;
    private List<ParentBChildBar> parentBChildBars;

    protected ParentBParentCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentBParent>("name", Shims.name));
        this.addRule(new MaxLength<ParentBParent>("name", 100, Shims.name));
    }

    public Id<ParentBParent> getId() {
        return this.id;
    }

    public void setId(Id<ParentBParent> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getCurrent().getIdentityMap().store(this);
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

    public List<ParentBChildFoo> getParentBChildFoos() {
        if (this.parentBChildFoos == null) {
            if (UoW.isOpen() && this.getId() != null) {
                ParentBChildFooAlias a = new ParentBChildFooAlias("a");
                this.parentBChildFoos = Select.from(a).where(a.parentBParent.equals(this.getId())).orderBy(a.id.asc()).list();
            } else {
                this.parentBChildFoos = new ArrayList<ParentBChildFoo>();
            }
        }
        return this.parentBChildFoos;
    }

    public void addParentBChildFoo(ParentBChildFoo o) {
        o.setParentBParentWithoutPercolation((ParentBParent) this);
        this.addParentBChildFooWithoutPercolation(o);
    }

    public void addParentBChildFooWithoutPercolation(ParentBChildFoo o) {
        this.getParentBChildFoos(); // hack
        this.recordIfChanged("parentBChildFoos");
        this.parentBChildFoos.add(o);
    }

    public void removeParentBChildFoo(ParentBChildFoo o) {
        o.setParentBParentWithoutPercolation(null);
        this.removeParentBChildFooWithoutPercolation(o);
    }

    public void removeParentBChildFooWithoutPercolation(ParentBChildFoo o) {
        this.getParentBChildFoos(); // hack
        this.recordIfChanged("parentBChildFoos");
        this.parentBChildFoos.remove(o);
    }

    public List<ParentBChildBar> getParentBChildBars() {
        if (this.parentBChildBars == null) {
            if (UoW.isOpen() && this.getId() != null) {
                ParentBChildBarAlias a = new ParentBChildBarAlias("a");
                this.parentBChildBars = Select.from(a).where(a.parentBParent.equals(this.getId())).orderBy(a.id.asc()).list();
            } else {
                this.parentBChildBars = new ArrayList<ParentBChildBar>();
            }
        }
        return this.parentBChildBars;
    }

    public void addParentBChildBar(ParentBChildBar o) {
        o.setParentBParentWithoutPercolation((ParentBParent) this);
        this.addParentBChildBarWithoutPercolation(o);
    }

    public void addParentBChildBarWithoutPercolation(ParentBChildBar o) {
        this.getParentBChildBars(); // hack
        this.recordIfChanged("parentBChildBars");
        this.parentBChildBars.add(o);
    }

    public void removeParentBChildBar(ParentBChildBar o) {
        o.setParentBParentWithoutPercolation(null);
        this.removeParentBChildBarWithoutPercolation(o);
    }

    public void removeParentBChildBarWithoutPercolation(ParentBChildBar o) {
        this.getParentBChildBars(); // hack
        this.recordIfChanged("parentBChildBars");
        this.parentBChildBars.remove(o);
    }

    public static class Shims {
        public static final Shim<ParentBParent, Id<ParentBParent>> id = new Shim<ParentBParent, Id<ParentBParent>>() {
            public void set(ParentBParent instance, Id<ParentBParent> id) {
                ((ParentBParentCodegen) instance).id = id;
            }
            public Id<ParentBParent> get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).id;
            }
        };
        public static final Shim<ParentBParent, java.lang.String> name = new Shim<ParentBParent, java.lang.String>() {
            public void set(ParentBParent instance, java.lang.String name) {
                ((ParentBParentCodegen) instance).name = name;
            }
            public String get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).name;
            }
        };
        public static final Shim<ParentBParent, java.lang.Integer> version = new Shim<ParentBParent, java.lang.Integer>() {
            public void set(ParentBParent instance, java.lang.Integer version) {
                ((ParentBParentCodegen) instance).version = version;
            }
            public Integer get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).version;
            }
        };
    }

}
