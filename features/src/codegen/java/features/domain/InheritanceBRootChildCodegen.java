package features.domain;

import features.domain.queries.InheritanceBRootChildQueries;
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
public abstract class InheritanceBRootChildCodegen extends AbstractDomainObject {

  public static final InheritanceBRootChildQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<InheritanceBRootChild, InheritanceBRoot> inheritanceBRoot = new ForeignKeyHolder<InheritanceBRootChild, InheritanceBRoot>(InheritanceBRootChild.class, InheritanceBRoot.class, Aliases.inheritanceBRoot(), Aliases.inheritanceBRootChild().inheritanceBRoot);
  protected Changed changed;

  static {
    Aliases.inheritanceBRootChild();
    queries = new InheritanceBRootChildQueries();
  }

  protected InheritanceBRootChildCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceBRootChild>(Shims.name));
    this.addRule(new MaxLength<InheritanceBRootChild>(Shims.name, 100));
    this.addRule(new NotEmpty<InheritanceBRootChild>(Shims.name));
    this.addRule(new NotNull<InheritanceBRootChild>(Shims.inheritanceBRootId));
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

  public InheritanceBRoot getInheritanceBRoot() {
    return this.inheritanceBRoot.get();
  }

  public void setInheritanceBRoot(InheritanceBRoot inheritanceBRoot) {
    if (inheritanceBRoot == this.getInheritanceBRoot()) {
      return;
    }
    if (this.inheritanceBRoot.get() != null) {
      this.inheritanceBRoot.get().removeInheritanceBRootChildWithoutPercolation((InheritanceBRootChild) this);
    }
    this.setInheritanceBRootWithoutPercolation(inheritanceBRoot);
    if (this.inheritanceBRoot.get() != null) {
      this.inheritanceBRoot.get().addInheritanceBRootChildWithoutPercolation((InheritanceBRootChild) this);
    }
  }

  protected void setInheritanceBRootWithoutPercolation(InheritanceBRoot inheritanceBRoot) {
    this.getChanged().record("inheritanceBRoot", this.inheritanceBRoot.get(), inheritanceBRoot);
    this.inheritanceBRoot.set(inheritanceBRoot);
  }

  public InheritanceBRootChildChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceBRootChildChanged((InheritanceBRootChild) this);
    }
    return (InheritanceBRootChildChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setInheritanceBRoot(null);
  }

  static class Shims {
    protected static final Shim<InheritanceBRootChild, Long> id = new Shim<InheritanceBRootChild, Long>() {
      public void set(InheritanceBRootChild instance, Long id) {
        ((InheritanceBRootChildCodegen) instance).id = id;
      }
      public Long get(InheritanceBRootChild instance) {
        return ((InheritanceBRootChildCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<InheritanceBRootChild, String> name = new Shim<InheritanceBRootChild, String>() {
      public void set(InheritanceBRootChild instance, String name) {
        ((InheritanceBRootChildCodegen) instance).name = name;
      }
      public String get(InheritanceBRootChild instance) {
        return ((InheritanceBRootChildCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<InheritanceBRootChild, Long> version = new Shim<InheritanceBRootChild, Long>() {
      public void set(InheritanceBRootChild instance, Long version) {
        ((InheritanceBRootChildCodegen) instance).version = version;
      }
      public Long get(InheritanceBRootChild instance) {
        return ((InheritanceBRootChildCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<InheritanceBRootChild, Long> inheritanceBRootId = new Shim<InheritanceBRootChild, Long>() {
      public void set(InheritanceBRootChild instance, Long inheritanceBRootId) {
        ((InheritanceBRootChildCodegen) instance).inheritanceBRoot.setId(inheritanceBRootId);
      }
      public Long get(InheritanceBRootChild instance) {
        return ((InheritanceBRootChildCodegen) instance).inheritanceBRoot.getId();
      }
      public String getName() {
        return "inheritanceBRoot";
      }
    };
  }

  public static class InheritanceBRootChildChanged extends AbstractChanged {
    public InheritanceBRootChildChanged(InheritanceBRootChild instance) {
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
    public boolean hasInheritanceBRoot() {
      return this.contains("inheritanceBRoot");
    }
    public InheritanceBRoot getOriginalInheritanceBRoot() {
      return (InheritanceBRoot) this.getOriginal("inheritanceBRoot");
    }
  }

}
