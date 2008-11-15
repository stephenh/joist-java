package features.domain;

import features.domain.ParentCBarAlias;
import features.domain.queries.ParentCBarQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class ParentCBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentCBar.class, new ParentCBarAlias("a"));
    }

    public static final ParentCBarQueries queries = new ParentCBarQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<ParentCFoo> firstParent = new ForeignKeyHolder<ParentCFoo>(ParentCFoo.class);
    private ForeignKeyHolder<ParentCFoo> secondParent = new ForeignKeyHolder<ParentCFoo>(ParentCFoo.class);

    protected ParentCBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentCBar>("name", Shims.name));
        this.addRule(new MaxLength<ParentCBar>("name", 100, Shims.name));
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

    public ParentCFoo getFirstParent() {
        return this.firstParent.get();
    }

    public void setFirstParent(ParentCFoo firstParent) {
        if (this.firstParent.get() != null) {
           this.firstParent.get().removeFirstParentParentCBarWithoutPercolation((ParentCBar) this);
        }
        this.setFirstParentWithoutPercolation(firstParent);
        if (this.firstParent.get() != null) {
           this.firstParent.get().addFirstParentParentCBarWithoutPercolation((ParentCBar) this);
        }
    }

    public void setFirstParentWithoutPercolation(ParentCFoo firstParent) {
        this.recordIfChanged("firstParent", this.firstParent, firstParent);
        this.firstParent.set(firstParent);
    }

    public ParentCFoo getSecondParent() {
        return this.secondParent.get();
    }

    public void setSecondParent(ParentCFoo secondParent) {
        if (this.secondParent.get() != null) {
           this.secondParent.get().removeSecondParentParentCBarWithoutPercolation((ParentCBar) this);
        }
        this.setSecondParentWithoutPercolation(secondParent);
        if (this.secondParent.get() != null) {
           this.secondParent.get().addSecondParentParentCBarWithoutPercolation((ParentCBar) this);
        }
    }

    public void setSecondParentWithoutPercolation(ParentCFoo secondParent) {
        this.recordIfChanged("secondParent", this.secondParent, secondParent);
        this.secondParent.set(secondParent);
    }

    public static class Shims {
        public static final Shim<ParentCBar, java.lang.Integer> id = new Shim<ParentCBar, java.lang.Integer>() {
            public void set(ParentCBar instance, java.lang.Integer id) {
                ((ParentCBarCodegen) instance).id = id;
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).id;
            }
        };
        public static final Shim<ParentCBar, java.lang.String> name = new Shim<ParentCBar, java.lang.String>() {
            public void set(ParentCBar instance, java.lang.String name) {
                ((ParentCBarCodegen) instance).name = name;
            }
            public String get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).name;
            }
        };
        public static final Shim<ParentCBar, java.lang.Integer> version = new Shim<ParentCBar, java.lang.Integer>() {
            public void set(ParentCBar instance, java.lang.Integer version) {
                ((ParentCBarCodegen) instance).version = version;
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).version;
            }
        };
        public static final Shim<ParentCBar, Integer> firstParentId = new Shim<ParentCBar, Integer>() {
            public void set(ParentCBar instance, Integer firstParentId) {
                ((ParentCBarCodegen) instance).firstParent.setId(firstParentId);
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).firstParent.getId();
            }
        };
        public static final Shim<ParentCBar, Integer> secondParentId = new Shim<ParentCBar, Integer>() {
            public void set(ParentCBar instance, Integer secondParentId) {
                ((ParentCBarCodegen) instance).secondParent.setId(secondParentId);
            }
            public Integer get(ParentCBar instance) {
                return ((ParentCBarCodegen) instance).secondParent.getId();
            }
        };
    }

}
