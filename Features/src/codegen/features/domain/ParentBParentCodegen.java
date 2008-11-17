package features.domain;

import features.domain.ParentBChildBarAlias;
import features.domain.ParentBChildFooAlias;
import features.domain.ParentBParentAlias;
import features.domain.queries.ParentBParentQueries;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class ParentBParentCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentBParent.class, new ParentBParentAlias("a"));
    }

    public static final ParentBParentQueries queries = new ParentBParentQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final ParentBChildBarAlias parentBChildBarsAlias = new ParentBChildBarAlias("a");
    private ForeignKeyListHolder<ParentBParent, ParentBChildBar> parentBChildBars = new ForeignKeyListHolder<ParentBParent, ParentBChildBar>((ParentBParent) this, parentBChildBarsAlias, parentBChildBarsAlias.parentBParent);
    private static final ParentBChildFooAlias parentBChildFoosAlias = new ParentBChildFooAlias("a");
    private ForeignKeyListHolder<ParentBParent, ParentBChildFoo> parentBChildFoos = new ForeignKeyListHolder<ParentBParent, ParentBChildFoo>((ParentBParent) this, parentBChildFoosAlias, parentBChildFoosAlias.parentBParent);

    protected ParentBParentCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentBParent>("name", Shims.name));
        this.addRule(new MaxLength<ParentBParent>("name", 100, Shims.name));
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

    public List<ParentBChildBar> getParentBChildBars() {
        return this.parentBChildBars.get();
    }

    public void addParentBChildBar(ParentBChildBar o) {
        o.setParentBParentWithoutPercolation((ParentBParent) this);
        this.addParentBChildBarWithoutPercolation(o);
    }

    public void removeParentBChildBar(ParentBChildBar o) {
        o.setParentBParentWithoutPercolation(null);
        this.removeParentBChildBarWithoutPercolation(o);
    }

    protected void addParentBChildBarWithoutPercolation(ParentBChildBar o) {
        this.recordIfChanged("parentBChildBars");
        this.parentBChildBars.add(o);
    }

    protected void removeParentBChildBarWithoutPercolation(ParentBChildBar o) {
        this.recordIfChanged("parentBChildBars");
        this.parentBChildBars.remove(o);
    }

    public List<ParentBChildFoo> getParentBChildFoos() {
        return this.parentBChildFoos.get();
    }

    public void addParentBChildFoo(ParentBChildFoo o) {
        o.setParentBParentWithoutPercolation((ParentBParent) this);
        this.addParentBChildFooWithoutPercolation(o);
    }

    public void removeParentBChildFoo(ParentBChildFoo o) {
        o.setParentBParentWithoutPercolation(null);
        this.removeParentBChildFooWithoutPercolation(o);
    }

    protected void addParentBChildFooWithoutPercolation(ParentBChildFoo o) {
        this.recordIfChanged("parentBChildFoos");
        this.parentBChildFoos.add(o);
    }

    protected void removeParentBChildFooWithoutPercolation(ParentBChildFoo o) {
        this.recordIfChanged("parentBChildFoos");
        this.parentBChildFoos.remove(o);
    }

    public static class Shims {
        public static final Shim<ParentBParent, java.lang.Integer> id = new Shim<ParentBParent, java.lang.Integer>() {
            public void set(ParentBParent instance, java.lang.Integer id) {
                ((ParentBParentCodegen) instance).id = id;
            }
            public Integer get(ParentBParent instance) {
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
