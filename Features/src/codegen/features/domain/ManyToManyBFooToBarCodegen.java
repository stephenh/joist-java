package features.domain;

import features.domain.ManyToManyBFooToBarAlias;
import features.domain.queries.ManyToManyBFooToBarQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.uow.UoW;

abstract class ManyToManyBFooToBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ManyToManyBFooToBar.class, new ManyToManyBFooToBarAlias("a"));
    }

    public static final ManyToManyBFooToBarQueries queries = new ManyToManyBFooToBarQueries();
    private Id<ManyToManyBFooToBar> id = null;
    private Integer version = null;
    private ForeignKeyHolder<ManyToManyBFoo> blue = new ForeignKeyHolder<ManyToManyBFoo>(ManyToManyBFoo.class);
    private ForeignKeyHolder<ManyToManyBBar> green = new ForeignKeyHolder<ManyToManyBBar>(ManyToManyBBar.class);

    protected ManyToManyBFooToBarCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
    }

    public Id<ManyToManyBFooToBar> getId() {
        return this.id;
    }

    public void setId(Id<ManyToManyBFooToBar> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public Integer getVersion() {
        return this.version;
    }

    public ManyToManyBFoo getBlue() {
        return this.blue.get();
    }

    public void setBlue(ManyToManyBFoo blue) {
        if (this.blue.get() != null) {
           this.blue.get().removeBlueManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
        }
        this.setBlueWithoutPercolation(blue);
        if (this.blue.get() != null) {
           this.blue.get().addBlueManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
        }
    }

    public void setBlueWithoutPercolation(ManyToManyBFoo blue) {
        this.recordIfChanged("blue", this.blue, blue);
        this.blue.set(blue);
    }

    public ManyToManyBBar getGreen() {
        return this.green.get();
    }

    public void setGreen(ManyToManyBBar green) {
        if (this.green.get() != null) {
           this.green.get().removeGreenManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
        }
        this.setGreenWithoutPercolation(green);
        if (this.green.get() != null) {
           this.green.get().addGreenManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
        }
    }

    public void setGreenWithoutPercolation(ManyToManyBBar green) {
        this.recordIfChanged("green", this.green, green);
        this.green.set(green);
    }

    public static class Shims {
        public static final Shim<ManyToManyBFooToBar, Id<ManyToManyBFooToBar>> id = new Shim<ManyToManyBFooToBar, Id<ManyToManyBFooToBar>>() {
            public void set(ManyToManyBFooToBar instance, Id<ManyToManyBFooToBar> id) {
                ((ManyToManyBFooToBarCodegen) instance).id = id;
            }
            public Id<ManyToManyBFooToBar> get(ManyToManyBFooToBar instance) {
                return ((ManyToManyBFooToBarCodegen) instance).id;
            }
        };
        public static final Shim<ManyToManyBFooToBar, java.lang.Integer> version = new Shim<ManyToManyBFooToBar, java.lang.Integer>() {
            public void set(ManyToManyBFooToBar instance, java.lang.Integer version) {
                ((ManyToManyBFooToBarCodegen) instance).version = version;
            }
            public Integer get(ManyToManyBFooToBar instance) {
                return ((ManyToManyBFooToBarCodegen) instance).version;
            }
        };
        public static final Shim<ManyToManyBFooToBar, Integer> blueId = new Shim<ManyToManyBFooToBar, Integer>() {
            public void set(ManyToManyBFooToBar instance, Integer blueId) {
                ((ManyToManyBFooToBarCodegen) instance).blue.setId(blueId);
            }
            public Integer get(ManyToManyBFooToBar instance) {
                return ((ManyToManyBFooToBarCodegen) instance).blue.getId();
            }
        };
        public static final Shim<ManyToManyBFooToBar, Integer> greenId = new Shim<ManyToManyBFooToBar, Integer>() {
            public void set(ManyToManyBFooToBar instance, Integer greenId) {
                ((ManyToManyBFooToBarCodegen) instance).green.setId(greenId);
            }
            public Integer get(ManyToManyBFooToBar instance) {
                return ((ManyToManyBFooToBarCodegen) instance).green.getId();
            }
        };
    }

}
