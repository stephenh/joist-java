package features.domain;

import features.domain.queries.ChildHQueries;
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
public abstract class ChildHCodegen extends AbstractDomainObject {

  public static final ChildHQueries queries;
  private Long id = null;
  private String name = null;
  private Long quantity = null;
  private Long version = null;
  private final ForeignKeyHolder<ChildH, ParentH> parent = new ForeignKeyHolder<ChildH, ParentH>(ChildH.class, ParentH.class, Aliases.parentH(), Aliases.childH().parent);
  protected Changed changed;

  static {
    Aliases.childH();
    queries = new ChildHQueries();
  }

  protected ChildHCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ChildH>(Shims.name));
    this.addRule(new MaxLength<ChildH>(Shims.name, 100));
    this.addRule(new NotEmpty<ChildH>(Shims.name));
    this.addRule(new NotNull<ChildH>(Shims.quantity));
    this.addRule(new NotNull<ChildH>(Shims.parentId));
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

  public Long getQuantity() {
    return this.quantity;
  }

  public void setQuantity(Long quantity) {
    this.getChanged().record("quantity", this.quantity, quantity);
    this.quantity = quantity;
  }

  protected void defaultQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Long getVersion() {
    return this.version;
  }

  public ParentH getParent() {
    return this.parent.get();
  }

  public void setParent(ParentH parent) {
    if (parent == this.getParent()) {
      return;
    }
    if (this.parent.get() != null) {
      this.parent.get().removeChildHWithoutPercolation((ChildH) this);
    }
    this.setParentWithoutPercolation(parent);
    if (this.parent.get() != null) {
      this.parent.get().addChildHWithoutPercolation((ChildH) this);
    }
  }

  protected void setParentWithoutPercolation(ParentH parent) {
    this.getChanged().record("parent", this.parent.get(), parent);
    this.parent.set(parent);
  }

  public ChildHChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ChildHChanged((ChildH) this);
    }
    return (ChildHChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParent(null);
  }

  static class Shims {
    protected static final Shim<ChildH, Long> id = new Shim<ChildH, Long>() {
      public void set(ChildH instance, Long id) {
        ((ChildHCodegen) instance).id = id;
      }
      public Long get(ChildH instance) {
        return ((ChildHCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ChildH, String> name = new Shim<ChildH, String>() {
      public void set(ChildH instance, String name) {
        ((ChildHCodegen) instance).name = name;
      }
      public String get(ChildH instance) {
        return ((ChildHCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ChildH, Long> quantity = new Shim<ChildH, Long>() {
      public void set(ChildH instance, Long quantity) {
        ((ChildHCodegen) instance).quantity = quantity;
      }
      public Long get(ChildH instance) {
        return ((ChildHCodegen) instance).quantity;
      }
      public String getName() {
        return "quantity";
      }
    };
    protected static final Shim<ChildH, Long> version = new Shim<ChildH, Long>() {
      public void set(ChildH instance, Long version) {
        ((ChildHCodegen) instance).version = version;
      }
      public Long get(ChildH instance) {
        return ((ChildHCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ChildH, Long> parentId = new Shim<ChildH, Long>() {
      public void set(ChildH instance, Long parentId) {
        ((ChildHCodegen) instance).parent.setId(parentId);
      }
      public Long get(ChildH instance) {
        return ((ChildHCodegen) instance).parent.getId();
      }
      public String getName() {
        return "parent";
      }
    };
  }

  public static class ChildHChanged extends AbstractChanged {
    public ChildHChanged(ChildH instance) {
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
    public boolean hasQuantity() {
      return this.contains("quantity");
    }
    public Long getOriginalQuantity() {
      return (java.lang.Long) this.getOriginal("quantity");
    }
    public boolean hasVersion() {
      return this.contains("version");
    }
    public Long getOriginalVersion() {
      return (Long) this.getOriginal("version");
    }
    public boolean hasParent() {
      return this.contains("parent");
    }
    public ParentH getOriginalParent() {
      return (ParentH) this.getOriginal("parent");
    }
  }

}
