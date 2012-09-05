package features.domain;

import features.domain.queries.ParentDToChildCQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ParentDToChildCCodegen extends AbstractDomainObject {

  public static final ParentDToChildCQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentDToChildC, ParentDChildC> parentDChildC = new ForeignKeyHolder<ParentDToChildC, ParentDChildC>(ParentDToChildC.class, ParentDChildC.class, Aliases.parentDChildC(), Aliases.parentDToChildC().parentDChildC);
  private final ForeignKeyHolder<ParentDToChildC, ParentD> parentD = new ForeignKeyHolder<ParentDToChildC, ParentD>(ParentDToChildC.class, ParentD.class, Aliases.parentD(), Aliases.parentDToChildC().parentD);
  protected Changed changed;

  static {
    Aliases.parentDToChildC();
    queries = new ParentDToChildCQueries();
  }

  protected ParentDToChildCCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentDToChildC>(Shims.parentDChildCId));
    this.addRule(new NotNull<ParentDToChildC>(Shims.parentDId));
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    if (this.id != null) {
      throw new IllegalStateException(this + " id cannot be changed");
    }
    this.getChanged().record("id", this.id, id);
    this.id = id;
    if (UoW.isOpen()) {
      UoW.getIdentityMap().store(this);
    }
  }

  public Long getVersion() {
    return this.version;
  }

  public ParentDChildC getParentDChildC() {
    return this.parentDChildC.get();
  }

  public void setParentDChildC(ParentDChildC parentDChildC) {
    if (parentDChildC == this.getParentDChildC()) {
      return;
    }
    if (this.parentDChildC.get() != null) {
      this.parentDChildC.get().removeParentDToChildCWithoutPercolation((ParentDToChildC) this);
    }
    this.setParentDChildCWithoutPercolation(parentDChildC);
    if (this.parentDChildC.get() != null) {
      this.parentDChildC.get().addParentDToChildCWithoutPercolation((ParentDToChildC) this);
    }
  }

  protected void setParentDChildCWithoutPercolation(ParentDChildC parentDChildC) {
    this.getChanged().record("parentDChildC", this.parentDChildC.get(), parentDChildC);
    this.parentDChildC.set(parentDChildC);
  }

  public ParentD getParentD() {
    return this.parentD.get();
  }

  public void setParentD(ParentD parentD) {
    this.setParentDWithoutPercolation(parentD);
  }

  protected void setParentDWithoutPercolation(ParentD parentD) {
    this.getChanged().record("parentD", this.parentD.get(), parentD);
    this.parentD.set(parentD);
  }

  public ParentDToChildCChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentDToChildCChanged((ParentDToChildC) this);
    }
    return (ParentDToChildCChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentDChildC(null);
    this.setParentD(null);
  }

  static class Shims {
    protected static final Shim<ParentDToChildC, Long> id = new Shim<ParentDToChildC, Long>() {
      public void set(ParentDToChildC instance, Long id) {
        ((ParentDToChildCCodegen) instance).id = id;
      }
      public Long get(ParentDToChildC instance) {
        return ((ParentDToChildCCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentDToChildC, Long> version = new Shim<ParentDToChildC, Long>() {
      public void set(ParentDToChildC instance, Long version) {
        ((ParentDToChildCCodegen) instance).version = version;
      }
      public Long get(ParentDToChildC instance) {
        return ((ParentDToChildCCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentDToChildC, Long> parentDChildCId = new Shim<ParentDToChildC, Long>() {
      public void set(ParentDToChildC instance, Long parentDChildCId) {
        ((ParentDToChildCCodegen) instance).parentDChildC.setId(parentDChildCId);
      }
      public Long get(ParentDToChildC instance) {
        return ((ParentDToChildCCodegen) instance).parentDChildC.getId();
      }
      public String getName() {
        return "parentDChildC";
      }
    };
    protected static final Shim<ParentDToChildC, Long> parentDId = new Shim<ParentDToChildC, Long>() {
      public void set(ParentDToChildC instance, Long parentDId) {
        ((ParentDToChildCCodegen) instance).parentD.setId(parentDId);
      }
      public Long get(ParentDToChildC instance) {
        return ((ParentDToChildCCodegen) instance).parentD.getId();
      }
      public String getName() {
        return "parentD";
      }
    };
  }

  public static class ParentDToChildCChanged extends AbstractChanged {
    public ParentDToChildCChanged(ParentDToChildC instance) {
      super(instance);
    }
    public boolean hasId() {
      return this.contains("id");
    }
    public Long getOriginalId() {
      return (Long) this.getOriginal("id");
    }
    public boolean hasVersion() {
      return this.contains("version");
    }
    public Long getOriginalVersion() {
      return (Long) this.getOriginal("version");
    }
    public boolean hasParentDChildC() {
      return this.contains("parentDChildC");
    }
    public ParentDChildC getOriginalParentDChildC() {
      return (ParentDChildC) this.getOriginal("parentDChildC");
    }
    public boolean hasParentD() {
      return this.contains("parentD");
    }
    public ParentD getOriginalParentD() {
      return (ParentD) this.getOriginal("parentD");
    }
  }

}
