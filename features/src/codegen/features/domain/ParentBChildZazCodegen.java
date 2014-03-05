package features.domain;

import features.domain.queries.ParentBChildZazQueries;
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
public abstract class ParentBChildZazCodegen extends AbstractDomainObject {

  public static final ParentBChildZazQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentBChildZaz, ParentBChildBar> parentBChildBar = new ForeignKeyHolder<ParentBChildZaz, ParentBChildBar>(ParentBChildZaz.class, ParentBChildBar.class, Aliases.parentBChildBar(), Aliases.parentBChildZaz().parentBChildBar);
  private final ForeignKeyHolder<ParentBChildZaz, ParentBParent> parentBParent = new ForeignKeyHolder<ParentBChildZaz, ParentBParent>(ParentBChildZaz.class, ParentBParent.class, Aliases.parentBParent(), Aliases.parentBChildZaz().parentBParent);
  protected Changed changed;

  static {
    Aliases.parentBChildZaz();
    queries = new ParentBChildZazQueries();
  }

  protected ParentBChildZazCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentBChildZaz>(Shims.name));
    this.addRule(new MaxLength<ParentBChildZaz>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentBChildZaz>(Shims.name));
    this.addRule(new NotNull<ParentBChildZaz>(Shims.parentBChildBarId));
    this.addRule(new NotNull<ParentBChildZaz>(Shims.parentBParentId));
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

  public ParentBChildBar getParentBChildBar() {
    return this.parentBChildBar.get();
  }

  public void setParentBChildBar(ParentBChildBar parentBChildBar) {
    if (parentBChildBar == this.getParentBChildBar()) {
      return;
    }
    if (this.parentBChildBar.get() != null) {
      this.parentBChildBar.get().removeParentBChildZazWithoutPercolation((ParentBChildZaz) this);
    }
    this.setParentBChildBarWithoutPercolation(parentBChildBar);
    if (this.parentBChildBar.get() != null) {
      this.parentBChildBar.get().addParentBChildZazWithoutPercolation((ParentBChildZaz) this);
    }
  }

  protected void setParentBChildBarWithoutPercolation(ParentBChildBar parentBChildBar) {
    this.getChanged().record("parentBChildBar", this.parentBChildBar.get(), parentBChildBar);
    this.parentBChildBar.set(parentBChildBar);
  }

  public ParentBParent getParentBParent() {
    return this.parentBParent.get();
  }

  public void setParentBParent(ParentBParent parentBParent) {
    if (parentBParent == this.getParentBParent()) {
      return;
    }
    if (this.parentBParent.get() != null) {
      this.parentBParent.get().removeParentBChildZazWithoutPercolation((ParentBChildZaz) this);
    }
    this.setParentBParentWithoutPercolation(parentBParent);
    if (this.parentBParent.get() != null) {
      this.parentBParent.get().addParentBChildZazWithoutPercolation((ParentBChildZaz) this);
    }
  }

  protected void setParentBParentWithoutPercolation(ParentBParent parentBParent) {
    this.getChanged().record("parentBParent", this.parentBParent.get(), parentBParent);
    this.parentBParent.set(parentBParent);
  }

  public ParentBChildZazChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentBChildZazChanged((ParentBChildZaz) this);
    }
    return (ParentBChildZazChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentBChildBar(null);
    this.setParentBParent(null);
  }

  static class Shims {
    protected static final Shim<ParentBChildZaz, Long> id = new Shim<ParentBChildZaz, Long>() {
      public void set(ParentBChildZaz instance, Long id) {
        ((ParentBChildZazCodegen) instance).id = id;
      }
      public Long get(ParentBChildZaz instance) {
        return ((ParentBChildZazCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentBChildZaz, String> name = new Shim<ParentBChildZaz, String>() {
      public void set(ParentBChildZaz instance, String name) {
        ((ParentBChildZazCodegen) instance).name = name;
      }
      public String get(ParentBChildZaz instance) {
        return ((ParentBChildZazCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentBChildZaz, Long> version = new Shim<ParentBChildZaz, Long>() {
      public void set(ParentBChildZaz instance, Long version) {
        ((ParentBChildZazCodegen) instance).version = version;
      }
      public Long get(ParentBChildZaz instance) {
        return ((ParentBChildZazCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentBChildZaz, Long> parentBChildBarId = new Shim<ParentBChildZaz, Long>() {
      public void set(ParentBChildZaz instance, Long parentBChildBarId) {
        ((ParentBChildZazCodegen) instance).parentBChildBar.setId(parentBChildBarId);
      }
      public Long get(ParentBChildZaz instance) {
        return ((ParentBChildZazCodegen) instance).parentBChildBar.getId();
      }
      public String getName() {
        return "parentBChildBar";
      }
    };
    protected static final Shim<ParentBChildZaz, Long> parentBParentId = new Shim<ParentBChildZaz, Long>() {
      public void set(ParentBChildZaz instance, Long parentBParentId) {
        ((ParentBChildZazCodegen) instance).parentBParent.setId(parentBParentId);
      }
      public Long get(ParentBChildZaz instance) {
        return ((ParentBChildZazCodegen) instance).parentBParent.getId();
      }
      public String getName() {
        return "parentBParent";
      }
    };
  }

  public static class ParentBChildZazChanged extends AbstractChanged {
    public ParentBChildZazChanged(ParentBChildZaz instance) {
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
    public boolean hasParentBChildBar() {
      return this.contains("parentBChildBar");
    }
    public ParentBChildBar getOriginalParentBChildBar() {
      return (ParentBChildBar) this.getOriginal("parentBChildBar");
    }
    public boolean hasParentBParent() {
      return this.contains("parentBParent");
    }
    public ParentBParent getOriginalParentBParent() {
      return (ParentBParent) this.getOriginal("parentBParent");
    }
  }

}
