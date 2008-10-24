package features.domain;

import features.domain.mappers.ParentBChildBarAlias;
import features.domain.mappers.ParentBChildFooAlias;
import features.domain.mappers.ParentBParentAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;

public abstract class ParentBParentCodegen extends AbstractDomainObject {

    private Id<ParentBParent> id = null;
    private String name = null;
    private Integer version = null;
    private List<ParentBChildFoo> parentBChildFoos;
    private List<ParentBChildBar> parentBChildBars;

    public Alias<? extends ParentBParent> newAlias(String alias) {
        return new ParentBParentAlias(alias);
    }

    public Id<ParentBParent> getId() {
        return this.id;
    }

    public void setId(Id<ParentBParent> id) {
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

    public List<ParentBChildFoo> getParentBChildFoos() {
        if (this.parentBChildFoos == null) {
            if (UoW.isOpen() && this.getId() != null) {
                ParentBChildFooAlias a = new ParentBChildFooAlias("a");
                Select<ParentBChildFoo> q = Select.from(a);
                q.where(a.parentBParent.equals(this.getId().intValue()));
                q.orderBy(a.id.asc());
                this.parentBChildFoos = q.list();
            } else {
                this.parentBChildFoos = new ArrayList<ParentBChildFoo>();
            }
        }
        return this.parentBChildFoos;
    }

    public List<ParentBChildBar> getParentBChildBars() {
        if (this.parentBChildBars == null) {
            if (UoW.isOpen() && this.getId() != null) {
                ParentBChildBarAlias a = new ParentBChildBarAlias("a");
                Select<ParentBChildBar> q = Select.from(a);
                q.where(a.parentBParent.equals(this.getId().intValue()));
                q.orderBy(a.id.asc());
                this.parentBChildBars = q.list();
            } else {
                this.parentBChildBars = new ArrayList<ParentBChildBar>();
            }
        }
        return this.parentBChildBars;
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
        public static final Shim<ParentBParent, String> name = new Shim<ParentBParent, String>() {
            public void set(ParentBParent instance, String name) {
                ((ParentBParentCodegen) instance).name = name;
            }
            public String get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).name;
            }
        };
        public static final Shim<ParentBParent, Integer> version = new Shim<ParentBParent, Integer>() {
            public void set(ParentBParent instance, Integer version) {
                ((ParentBParentCodegen) instance).version = version;
            }
            public Integer get(ParentBParent instance) {
                return ((ParentBParentCodegen) instance).version;
            }
        };
    }

}
