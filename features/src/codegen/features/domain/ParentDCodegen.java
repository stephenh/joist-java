package features.domain;

import features.domain.queries.ParentDQueries;
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

@SuppressWarnings("all")
public abstract class ParentDCodegen extends AbstractDomainObject {

    public static final ParentDQueries queries;
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyListHolder<ParentD, ParentDChildB> parentDChildBs = new ForeignKeyListHolder<ParentD, ParentDChildB>((ParentD) this, Aliases.parentDChildB(), Aliases.parentDChildB().parentD);
    protected Changed changed;

    static {
        Aliases.parentD();
        queries = new ParentDQueries();
    }

    protected ParentDCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<ParentD>(Shims.name));
        this.addRule(new MaxLength<ParentD>(Shims.name, 100));
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

    public List<ParentDChildB> getParentDChildBs() {
        return this.parentDChildBs.get();
    }

    public void setParentDChildBs(List<ParentDChildB> parentDChildBs) {
        for (ParentDChildB o : Copy.list(this.getParentDChildBs())) {
            this.removeParentDChildB(o);
        }
        for (ParentDChildB o : parentDChildBs) {
            this.addParentDChildB(o);
        }
    }

    public void addParentDChildB(ParentDChildB o) {
        o.setParentDWithoutPercolation((ParentD) this);
        this.addParentDChildBWithoutPercolation(o);
    }

    public void removeParentDChildB(ParentDChildB o) {
        o.setParentDWithoutPercolation(null);
        this.removeParentDChildBWithoutPercolation(o);
    }

    protected void addParentDChildBWithoutPercolation(ParentDChildB o) {
        this.getChanged().record("parentDChildBs");
        this.parentDChildBs.add(o);
    }

    protected void removeParentDChildBWithoutPercolation(ParentDChildB o) {
        this.getChanged().record("parentDChildBs");
        this.parentDChildBs.remove(o);
    }

    public ParentDChanged getChanged() {
        if (this.changed == null) {
            this.changed = new ParentDChanged((ParentD) this);
        }
        return (ParentDChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
        for (ParentDChildB o : Copy.list(this.getParentDChildBs())) {
            o.setParentDWithoutPercolation(null);
        }
    }

    static class Shims {
        protected static final Shim<ParentD, Integer> id = new Shim<ParentD, Integer>() {
            public void set(ParentD instance, Integer id) {
                ((ParentDCodegen) instance).id = id;
            }
            public Integer get(ParentD instance) {
                return ((ParentDCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<ParentD, String> name = new Shim<ParentD, String>() {
            public void set(ParentD instance, String name) {
                ((ParentDCodegen) instance).name = name;
            }
            public String get(ParentD instance) {
                return ((ParentDCodegen) instance).name;
            }
            public String getName() {
                return "name";
            }
        };
        protected static final Shim<ParentD, Integer> version = new Shim<ParentD, Integer>() {
            public void set(ParentD instance, Integer version) {
                ((ParentDCodegen) instance).version = version;
            }
            public Integer get(ParentD instance) {
                return ((ParentDCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class ParentDChanged extends AbstractChanged {
        public ParentDChanged(ParentD instance) {
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
        public boolean hasParentDChildBs() {
            return this.contains("parentDChildBs");
        }
    }

}
