package features.domain;

import features.domain.mappers.ParentBChildBarAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyHolder;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class ParentBChildBarCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(ParentBChildBar.class, new ParentBChildBarAlias("a"));
    }

    private Id<ParentBChildBar> id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyHolder<ParentBParent> parentBParent = new ForeignKeyHolder<ParentBParent>(ParentBParent.class);

    public Alias<? extends ParentBChildBar> newAlias(String alias) {
        return new ParentBChildBarAlias(alias);
    }

    public Id<ParentBChildBar> getId() {
        return this.id;
    }

    public void setId(Id<ParentBChildBar> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public ParentBParent getParentBParent() {
        return this.parentBParent.get();
    }

    public void setParentBParent(ParentBParent parentBParent) {
        if (this.parentBParent.get() != null) {
           this.parentBParent.get().removeParentBChildBarWithoutPercolation((ParentBChildBar) this);
        }
        this.setParentBParentWithoutPercolation(parentBParent);
        if (this.parentBParent.get() != null) {
           this.parentBParent.get().addParentBChildBarWithoutPercolation((ParentBChildBar) this);
        }
    }

    public void setParentBParentWithoutPercolation(ParentBParent parentBParent) {
        this.recordIfChanged("parentBParent", this.parentBParent, parentBParent);
        this.parentBParent.set(parentBParent);
    }

    public static class Shims {
        public static final Shim<ParentBChildBar, Id<ParentBChildBar>> id = new Shim<ParentBChildBar, Id<ParentBChildBar>>() {
            public void set(ParentBChildBar instance, Id<ParentBChildBar> id) {
                ((ParentBChildBarCodegen) instance).id = id;
            }
            public Id<ParentBChildBar> get(ParentBChildBar instance) {
                return ((ParentBChildBarCodegen) instance).id;
            }
        };
        public static final Shim<ParentBChildBar, String> name = new Shim<ParentBChildBar, String>() {
            public void set(ParentBChildBar instance, String name) {
                ((ParentBChildBarCodegen) instance).name = name;
            }
            public String get(ParentBChildBar instance) {
                return ((ParentBChildBarCodegen) instance).name;
            }
        };
        public static final Shim<ParentBChildBar, Integer> version = new Shim<ParentBChildBar, Integer>() {
            public void set(ParentBChildBar instance, Integer version) {
                ((ParentBChildBarCodegen) instance).version = version;
            }
            public Integer get(ParentBChildBar instance) {
                return ((ParentBChildBarCodegen) instance).version;
            }
        };
        public static final Shim<ParentBChildBar, Integer> parentBParentId = new Shim<ParentBChildBar, Integer>() {
            public void set(ParentBChildBar instance, Integer parentBParentId) {
                ((ParentBChildBarCodegen) instance).parentBParent.setId(parentBParentId);
            }
            public Integer get(ParentBChildBar instance) {
                return ((ParentBChildBarCodegen) instance).parentBParent.getId();
            }
        };
    }

}
