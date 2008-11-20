package features.domain;

import features.domain.OneToOneBBarAlias;
import features.domain.OneToOneBFooAlias;
import features.domain.queries.OneToOneBFooQueries;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyListHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class OneToOneBFooCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(OneToOneBFoo.class, new OneToOneBFooAlias("a"));
    }

    public static final OneToOneBFooQueries queries = new OneToOneBFooQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private static final OneToOneBBarAlias oneToOneBBarsAlias = new OneToOneBBarAlias("a");
    private ForeignKeyListHolder<OneToOneBFoo, OneToOneBBar> oneToOneBBars = new ForeignKeyListHolder<OneToOneBFoo, OneToOneBBar>((OneToOneBFoo) this, oneToOneBBarsAlias, oneToOneBBarsAlias.oneToOneBFoo);
    protected org.exigencecorp.domainobjects.Changed changed;

    protected OneToOneBFooCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneBFoo>("name", Shims.name));
        this.addRule(new MaxLength<OneToOneBFoo>("name", 100, Shims.name));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.getChanged().record("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.getChanged().record("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public List<OneToOneBBar> getOneToOneBBars() {
        return this.oneToOneBBars.get();
    }

    public void addOneToOneBBar(OneToOneBBar o) {
        o.setOneToOneBFooWithoutPercolation((OneToOneBFoo) this);
        this.addOneToOneBBarWithoutPercolation(o);
    }

    public void removeOneToOneBBar(OneToOneBBar o) {
        o.setOneToOneBFooWithoutPercolation(null);
        this.removeOneToOneBBarWithoutPercolation(o);
    }

    protected void addOneToOneBBarWithoutPercolation(OneToOneBBar o) {
        this.getChanged().record("oneToOneBBars");
        this.oneToOneBBars.add(o);
    }

    protected void removeOneToOneBBarWithoutPercolation(OneToOneBBar o) {
        this.getChanged().record("oneToOneBBars");
        this.oneToOneBBars.remove(o);
    }

    public OneToOneBFooChanged getChanged() {
        if (this.changed == null) {
            this.changed = new OneToOneBFooChanged((OneToOneBFoo) this);
        }
        return (OneToOneBFooChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<OneToOneBFoo, java.lang.Integer> id = new Shim<OneToOneBFoo, java.lang.Integer>() {
            public void set(OneToOneBFoo instance, java.lang.Integer id) {
                ((OneToOneBFooCodegen) instance).id = id;
            }
            public Integer get(OneToOneBFoo instance) {
                return ((OneToOneBFooCodegen) instance).id;
            }
        };
        public static final Shim<OneToOneBFoo, java.lang.String> name = new Shim<OneToOneBFoo, java.lang.String>() {
            public void set(OneToOneBFoo instance, java.lang.String name) {
                ((OneToOneBFooCodegen) instance).name = name;
            }
            public String get(OneToOneBFoo instance) {
                return ((OneToOneBFooCodegen) instance).name;
            }
        };
        public static final Shim<OneToOneBFoo, java.lang.Integer> version = new Shim<OneToOneBFoo, java.lang.Integer>() {
            public void set(OneToOneBFoo instance, java.lang.Integer version) {
                ((OneToOneBFooCodegen) instance).version = version;
            }
            public Integer get(OneToOneBFoo instance) {
                return ((OneToOneBFooCodegen) instance).version;
            }
        };
    }

    public static class OneToOneBFooChanged extends org.exigencecorp.domainobjects.AbstractChanged {
        public OneToOneBFooChanged(OneToOneBFoo instance) {
            super(instance);
        }
    }

}
