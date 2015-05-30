package features.domain;

import features.domain.queries.ParentDChildBQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ParentDChildBCodegen extends AbstractDomainObject {

  public static final ParentDChildBQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentDChildB, ParentD> parentD = new ForeignKeyHolder<ParentDChildB, ParentD>(ParentDChildB.class, ParentD.class, Aliases.parentD(), Aliases.parentDChildB().parentD);
  protected Changed changed;

  static {
    Aliases.parentDChildB();
    queries = new ParentDChildBQueries();
  }

  protected ParentDChildBCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentDChildB>(Shims.name));
    this.addRule(new MaxLength<ParentDChildB>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentDChildB>(Shims.name));
    this.addRule(new NotNull<ParentDChildB>(Shims.parentDId));
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

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.getChanged().record("name", this.name, name);
    this.name = name;
  }

  protected void defaultName(String name) {
    this.name = name;
  }

  public Long getVersion() {
    return this.version;
  }

  public ParentD getParentD() {
    return this.parentD.get();
  }

  public void setParentD(ParentD parentD) {
    if (parentD == this.getParentD()) {
      return;
    }
    if (this.parentD.get() != null) {
      this.parentD.get().removeParentDChildBWithoutPercolation((ParentDChildB) this);
    }
    this.setParentDWithoutPercolation(parentD);
    if (this.parentD.get() != null) {
      this.parentD.get().addParentDChildBWithoutPercolation((ParentDChildB) this);
    }
  }

  protected void setParentDWithoutPercolation(ParentD parentD) {
    this.getChanged().record("parentD", this.parentD.get(), parentD);
    this.parentD.set(parentD);
  }

  public ParentDChildBChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentDChildBChanged((ParentDChildB) this);
    }
    return (ParentDChildBChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentD(null);
  }

  static class Shims {
    protected static final Shim<ParentDChildB, Long> id = new Shim<ParentDChildB, Long>() {
      public void set(ParentDChildB instance, Long id) {
        ((ParentDChildBCodegen) instance).id = id;
      }
      public Long get(ParentDChildB instance) {
        return ((ParentDChildBCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentDChildB, String> name = new Shim<ParentDChildB, String>() {
      public void set(ParentDChildB instance, String name) {
        ((ParentDChildBCodegen) instance).name = name;
      }
      public String get(ParentDChildB instance) {
        return ((ParentDChildBCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentDChildB, Long> version = new Shim<ParentDChildB, Long>() {
      public void set(ParentDChildB instance, Long version) {
        ((ParentDChildBCodegen) instance).version = version;
      }
      public Long get(ParentDChildB instance) {
        return ((ParentDChildBCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentDChildB, Long> parentDId = new Shim<ParentDChildB, Long>() {
      public void set(ParentDChildB instance, Long parentDId) {
        ((ParentDChildBCodegen) instance).parentD.setId(parentDId);
      }
      public Long get(ParentDChildB instance) {
        return ((ParentDChildBCodegen) instance).parentD.getId();
      }
      public String getName() {
        return "parentD";
      }
    };
  }

  public static class ParentDChildBChanged extends AbstractChanged {
    public ParentDChildBChanged(ParentDChildB instance) {
      super(instance);
    }
    public boolean hasId() {
      return this.contains("id");
    }
    public Long getOriginalId() {
      return (Long) this.getOriginal("id");
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
    public Long getOriginalVersion() {
      return (Long) this.getOriginal("version");
    }
    public boolean hasParentD() {
      return this.contains("parentD");
    }
    public ParentD getOriginalParentD() {
      return (ParentD) this.getOriginal("parentD");
    }
  }

}
