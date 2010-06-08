package features.domain;

import features.domain.queries.ParentDChildCQueries;
import java.util.ArrayList;
import java.util.List;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;

public abstract class ParentDChildCCodegen extends AbstractDomainObject {

    public static final ParentDChildCQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<ParentDChildC, ParentDToChildC> parentDToChildCs = new ForeignKeyListHolder<ParentDChildC, ParentDToChildC>((ParentDChildC) this, Aliases.parentDToChildC, Aliases.parentDToChildC.parentDChildC);
    protected Changed changed;

    static {
        Aliases.init();
        queries = new ParentDChildCQueries();
    }

    protected ParentDChildCCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentDChildC>(Shims.name));
        this.addRule(new MaxLength<ParentDChildC>(Shims.name, 100));
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

    public List<ParentDToChildC> getParentDToChildCs() {
        return this.parentDToChildCs.get();
    }

    public void setParentDToChildCs(List<ParentDToChildC> parentDToChildCs) {
        for (ParentDToChildC o : Copy.list(this.getParentDToChildCs())) {
            this.removeParentDToChildC(o);
        }
        for (ParentDToChildC o : parentDToChildCs) {
            this.addParentDToChildC(o);
        }
    }

    public void addParentDToChildC(ParentDToChildC o) {
        o.setParentDChildCWithoutPercolation((ParentDChildC) this);
        this.addParentDToChildCWithoutPercolation(o);
    }

    public void removeParentDToChildC(ParentDToChildC o) {
        o.setParentDChildCWithoutPercolation(null);
        this.removeParentDToChildCWithoutPercolation(o);
    }

    protected void addParentDToChildCWithoutPercolation(ParentDToChildC o) {
        this.getChanged().record("parentDToChildCs");
        this.parentDToChildCs.add(o);
    }

    protected void removeParentDToChildCWithoutPercolation(ParentDToChildC o) {
        this.getChanged().record("parentDToChildCs");
        this.parentDToChildCs.remove(o);
    }

    public List<ParentD> getParentDs() {
        List<ParentD> l = new ArrayList<ParentD>();
        for (ParentDToChildC o : this.getParentDToChildCs()) {
            l.add(o.getParentD());
        }
        return l;
    }

    public void setParentDs(List<ParentD> parentDs) {
        for (ParentD o : Copy.list(this.getParentDs())) {
            this.removeParentD(o);
        }
        for (ParentD o : parentDs) {
            this.addParentD(o);
        }
    }

    public void addParentD(ParentD o) {
        ParentDToChildC a = new ParentDToChildC();
        a.setParentDChildC((ParentDChildC) this);
        a.setParentD(o);
    }

    public void removeParentD(ParentD o) {
        for (ParentDToChildC a : Copy.list(this.getParentDToChildCs())) {
            if (a.getParentD().equals(o)) {
                a.setParentD(null);
                a.setParentDChildC(null);
                if (UoW.isOpen()) {
                    UoW.delete(a);
                }
            }
        }
    }

    public ParentDChildCChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentDChildCChanged((ParentDChildC) this);
        }
        return (ParentDChildCChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        for (ParentDToChildC o : Copy.list(this.getParentDToChildCs())) {
            o.setParentDChildCWithoutPercolation(null);
        }
    }

    static class Shims {
        protected static final Shim<ParentDChildC, Integer> id = new Shim<ParentDChildC, Integer>() {
            public void set(ParentDChildC instance, Integer id) {
                ((ParentDChildCCodegen) instance).id = id;
            }
            public Integer get(ParentDChildC instance) {
                return ((ParentDChildCCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentDChildC, String> name = new Shim<ParentDChildC, String>() {
            public void set(ParentDChildC instance, String name) {
                ((ParentDChildCCodegen) instance).name = name;
            }
            public String get(ParentDChildC instance) {
                return ((ParentDChildCCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<ParentDChildC, Integer> version = new Shim<ParentDChildC, Integer>() {
            public void set(ParentDChildC instance, Integer version) {
                ((ParentDChildCCodegen) instance).version = version;
            }
            public Integer get(ParentDChildC instance) {
                return ((ParentDChildCCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ParentDChildCChanged extends AbstractChanged {
        public ParentDChildCChanged(ParentDChildC instance) {
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
        public boolean hasParentDToChildCs() {
            return this.contains("parentDToChildCs");
        }
    }

}
