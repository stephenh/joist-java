package features.domain;

import features.domain.queries.OneToOneBFooQueries;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Changed;
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
    protected Changed changed;

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

    public void setId(Integer id) {
        this.getChanged().record("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
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
        public static final Shim<OneToOneBFoo, Integer> id = new Shim<OneToOneBFoo, Integer>() {
            public void set(OneToOneBFoo instance, Integer id) {
                ((OneToOneBFooCodegen) instance).id = id;
            }
            public Integer get(OneToOneBFoo instance) {
                return ((OneToOneBFooCodegen) instance).id;
            }
        };
        public static final Shim<OneToOneBFoo, String> name = new Shim<OneToOneBFoo, String>() {
            public void set(OneToOneBFoo instance, String name) {
                ((OneToOneBFooCodegen) instance).name = name;
            }
            public String get(OneToOneBFoo instance) {
                return ((OneToOneBFooCodegen) instance).name;
            }
        };
        public static final Shim<OneToOneBFoo, Integer> version = new Shim<OneToOneBFoo, Integer>() {
            public void set(OneToOneBFoo instance, Integer version) {
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
        public boolean hasId() {
            return this.contains("id");
        }
        public Integer getOriginalId() {
            return (java.lang.Integer) this.getOriginal("id");
        }
        public boolean hasName() {
            return this.contains("name");
        }
        public String getOriginalName() {
            return (java.lang.String) this.getOriginal("name");
        }
        public boolean hasVersion() {
            return this.contains("version");
        }
        public Integer getOriginalVersion() {
            return (java.lang.Integer) this.getOriginal("version");
        }
        public boolean hasOneToOneBBars() {
            return this.contains("oneToOneBBars");
        }
    }

}
