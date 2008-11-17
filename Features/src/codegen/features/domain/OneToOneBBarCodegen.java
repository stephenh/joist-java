package features.domain;

import features.domain.OneToOneBBarAlias;
import features.domain.queries.OneToOneBBarQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class OneToOneBBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(OneToOneBBar.class, new OneToOneBBarAlias("a"));
    }

    public static final OneToOneBBarQueries queries = new OneToOneBBarQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<OneToOneBFoo> oneToOneBFoo = new ForeignKeyHolder<OneToOneBFoo>(OneToOneBFoo.class);

    protected OneToOneBBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<OneToOneBBar>("name", Shims.name));
        this.addRule(new MaxLength<OneToOneBBar>("name", 100, Shims.name));
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

    public OneToOneBFoo getOneToOneBFoo() {
        return this.oneToOneBFoo.get();
    }

    public void setOneToOneBFoo(OneToOneBFoo oneToOneBFoo) {
        if (this.oneToOneBFoo.get() != null) {
           this.oneToOneBFoo.get().removeOneToOneBBarWithoutPercolation((OneToOneBBar) this);
        }
        this.setOneToOneBFooWithoutPercolation(oneToOneBFoo);
        if (this.oneToOneBFoo.get() != null) {
           this.oneToOneBFoo.get().addOneToOneBBarWithoutPercolation((OneToOneBBar) this);
        }
    }

    public void setOneToOneBFooWithoutPercolation(OneToOneBFoo oneToOneBFoo) {
        this.recordIfChanged("oneToOneBFoo", this.oneToOneBFoo, oneToOneBFoo);
        this.oneToOneBFoo.set(oneToOneBFoo);
    }

    public static class Shims {
        public static final Shim<OneToOneBBar, java.lang.Integer> id = new Shim<OneToOneBBar, java.lang.Integer>() {
            public void set(OneToOneBBar instance, java.lang.Integer id) {
                ((OneToOneBBarCodegen) instance).id = id;
            }
            public Integer get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).id;
            }
        };
        public static final Shim<OneToOneBBar, java.lang.String> name = new Shim<OneToOneBBar, java.lang.String>() {
            public void set(OneToOneBBar instance, java.lang.String name) {
                ((OneToOneBBarCodegen) instance).name = name;
            }
            public String get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).name;
            }
        };
        public static final Shim<OneToOneBBar, java.lang.Integer> version = new Shim<OneToOneBBar, java.lang.Integer>() {
            public void set(OneToOneBBar instance, java.lang.Integer version) {
                ((OneToOneBBarCodegen) instance).version = version;
            }
            public Integer get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).version;
            }
        };
        public static final Shim<OneToOneBBar, Integer> oneToOneBFooId = new Shim<OneToOneBBar, Integer>() {
            public void set(OneToOneBBar instance, Integer oneToOneBFooId) {
                ((OneToOneBBarCodegen) instance).oneToOneBFoo.setId(oneToOneBFooId);
            }
            public Integer get(OneToOneBBar instance) {
                return ((OneToOneBBarCodegen) instance).oneToOneBFoo.getId();
            }
        };
    }

}
