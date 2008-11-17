package features.domain;

import features.domain.OneToOneABarAlias;
import features.domain.queries.OneToOneABarQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class OneToOneABarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(OneToOneABar.class, new OneToOneABarAlias("a"));
    }

    public static final OneToOneABarQueries queries = new OneToOneABarQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<OneToOneAFoo> oneToOneAFoo = new ForeignKeyHolder<OneToOneAFoo>(OneToOneAFoo.class);

    protected OneToOneABarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneABar>("name", Shims.name));
        this.addRule(new MaxLength<OneToOneABar>("name", 100, Shims.name));
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

    public OneToOneAFoo getOneToOneAFoo() {
        return this.oneToOneAFoo.get();
    }

    public void setOneToOneAFoo(OneToOneAFoo oneToOneAFoo) {
        if (this.oneToOneAFoo.get() != null) {
           this.oneToOneAFoo.get().removeOneToOneABarWithoutPercolation((OneToOneABar) this);
        }
        oneToOneAFoo.setOneToOneABar(null);
        this.setOneToOneAFooWithoutPercolation(oneToOneAFoo);
        if (this.oneToOneAFoo.get() != null) {
           this.oneToOneAFoo.get().addOneToOneABarWithoutPercolation((OneToOneABar) this);
        }
    }

    public void setOneToOneAFooWithoutPercolation(OneToOneAFoo oneToOneAFoo) {
        this.recordIfChanged("oneToOneAFoo", this.oneToOneAFoo, oneToOneAFoo);
        this.oneToOneAFoo.set(oneToOneAFoo);
    }

    public static class Shims {
        public static final Shim<OneToOneABar, java.lang.Integer> id = new Shim<OneToOneABar, java.lang.Integer>() {
            public void set(OneToOneABar instance, java.lang.Integer id) {
                ((OneToOneABarCodegen) instance).id = id;
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).id;
            }
        };
        public static final Shim<OneToOneABar, java.lang.String> name = new Shim<OneToOneABar, java.lang.String>() {
            public void set(OneToOneABar instance, java.lang.String name) {
                ((OneToOneABarCodegen) instance).name = name;
            }
            public String get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).name;
            }
        };
        public static final Shim<OneToOneABar, java.lang.Integer> version = new Shim<OneToOneABar, java.lang.Integer>() {
            public void set(OneToOneABar instance, java.lang.Integer version) {
                ((OneToOneABarCodegen) instance).version = version;
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).version;
            }
        };
        public static final Shim<OneToOneABar, Integer> oneToOneAFooId = new Shim<OneToOneABar, Integer>() {
            public void set(OneToOneABar instance, Integer oneToOneAFooId) {
                ((OneToOneABarCodegen) instance).oneToOneAFoo.setId(oneToOneAFooId);
            }
            public Integer get(OneToOneABar instance) {
                return ((OneToOneABarCodegen) instance).oneToOneAFoo.getId();
            }
        };
    }

}
