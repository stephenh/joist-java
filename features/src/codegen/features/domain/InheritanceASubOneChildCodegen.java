package features.domain;

import features.domain.queries.InheritanceASubOneChildQueries;
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
public abstract class InheritanceASubOneChildCodegen extends AbstractDomainObject {

  public static final InheritanceASubOneChildQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<InheritanceASubOneChild, InheritanceASubOne> sub = new ForeignKeyHolder<InheritanceASubOneChild, InheritanceASubOne>(InheritanceASubOneChild.class, InheritanceASubOne.class, Aliases.inheritanceASubOne(), Aliases.inheritanceASubOneChild().sub);
  protected Changed changed;

  static {
    Aliases.inheritanceASubOneChild();
    queries = new InheritanceASubOneChildQueries();
  }

  protected InheritanceASubOneChildCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceASubOneChild>(Shims.name));
    this.addRule(new MaxLength<InheritanceASubOneChild>(Shims.name, 100));
    this.addRule(new NotEmpty<InheritanceASubOneChild>(Shims.name));
    this.addRule(new NotNull<InheritanceASubOneChild>(Shims.subId));
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

  public InheritanceASubOne getSub() {
    return this.sub.get();
  }

  public void setSub(InheritanceASubOne sub) {
    if (sub == this.getSub()) {
      return;
    }
    if (this.sub.get() != null) {
      this.sub.get().removeInheritanceASubOneChildWithoutPercolation((InheritanceASubOneChild) this);
    }
    this.setSubWithoutPercolation(sub);
    if (this.sub.get() != null) {
      this.sub.get().addInheritanceASubOneChildWithoutPercolation((InheritanceASubOneChild) this);
    }
  }

  protected void setSubWithoutPercolation(InheritanceASubOne sub) {
    this.getChanged().record("sub", this.sub.get(), sub);
    this.sub.set(sub);
  }

  public InheritanceASubOneChildChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceASubOneChildChanged((InheritanceASubOneChild) this);
    }
    return (InheritanceASubOneChildChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setSub(null);
  }

  static class Shims {
    protected static final Shim<InheritanceASubOneChild, Long> id = new Shim<InheritanceASubOneChild, Long>() {
      public void set(InheritanceASubOneChild instance, Long id) {
        ((InheritanceASubOneChildCodegen) instance).id = id;
      }
      public Long get(InheritanceASubOneChild instance) {
        return ((InheritanceASubOneChildCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<InheritanceASubOneChild, String> name = new Shim<InheritanceASubOneChild, String>() {
      public void set(InheritanceASubOneChild instance, String name) {
        ((InheritanceASubOneChildCodegen) instance).name = name;
      }
      public String get(InheritanceASubOneChild instance) {
        return ((InheritanceASubOneChildCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<InheritanceASubOneChild, Long> version = new Shim<InheritanceASubOneChild, Long>() {
      public void set(InheritanceASubOneChild instance, Long version) {
        ((InheritanceASubOneChildCodegen) instance).version = version;
      }
      public Long get(InheritanceASubOneChild instance) {
        return ((InheritanceASubOneChildCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<InheritanceASubOneChild, Long> subId = new Shim<InheritanceASubOneChild, Long>() {
      public void set(InheritanceASubOneChild instance, Long subId) {
        ((InheritanceASubOneChildCodegen) instance).sub.setId(subId);
      }
      public Long get(InheritanceASubOneChild instance) {
        return ((InheritanceASubOneChildCodegen) instance).sub.getId();
      }
      public String getName() {
        return "sub";
      }
    };
  }

  public static class InheritanceASubOneChildChanged extends AbstractChanged {
    public InheritanceASubOneChildChanged(InheritanceASubOneChild instance) {
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
    public boolean hasSub() {
      return this.contains("sub");
    }
    public InheritanceASubOne getOriginalSub() {
      return (InheritanceASubOne) this.getOriginal("sub");
    }
  }

}
