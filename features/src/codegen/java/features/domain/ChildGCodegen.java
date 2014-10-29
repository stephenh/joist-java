package features.domain;

import features.domain.queries.ChildGQueries;
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
public abstract class ChildGCodegen extends AbstractDomainObject {

  public static final ChildGQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ChildG, ParentG> parentOne = new ForeignKeyHolder<ChildG, ParentG>(ChildG.class, ParentG.class, Aliases.parentG(), Aliases.childG().parentOne);
  private final ForeignKeyHolder<ChildG, ParentG> parentTwo = new ForeignKeyHolder<ChildG, ParentG>(ChildG.class, ParentG.class, Aliases.parentG(), Aliases.childG().parentTwo);
  protected Changed changed;

  static {
    Aliases.childG();
    queries = new ChildGQueries();
  }

  protected ChildGCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ChildG>(Shims.name));
    this.addRule(new MaxLength<ChildG>(Shims.name, 100));
    this.addRule(new NotEmpty<ChildG>(Shims.name));
    this.addRule(new NotNull<ChildG>(Shims.parentOneId));
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

  public ParentG getParentOne() {
    return this.parentOne.get();
  }

  public void setParentOne(ParentG parentOne) {
    if (parentOne == this.getParentOne()) {
      return;
    }
    if (this.parentOne.get() != null) {
      this.parentOne.get().removeParentOneChildGWithoutPercolation((ChildG) this);
    }
    if (parentOne != null) {
      parentOne.setParentOneChildG(null);
    }
    this.setParentOneWithoutPercolation(parentOne);
    if (this.parentOne.get() != null) {
      this.parentOne.get().addParentOneChildGWithoutPercolation((ChildG) this);
    }
  }

  protected void setParentOneWithoutPercolation(ParentG parentOne) {
    this.getChanged().record("parentOne", this.parentOne.get(), parentOne);
    this.parentOne.set(parentOne);
  }

  public ParentG getParentTwo() {
    return this.parentTwo.get();
  }

  public void setParentTwo(ParentG parentTwo) {
    if (parentTwo == this.getParentTwo()) {
      return;
    }
    if (this.parentTwo.get() != null) {
      this.parentTwo.get().removeParentTwoChildGWithoutPercolation((ChildG) this);
    }
    if (parentTwo != null) {
      parentTwo.setParentTwoChildG(null);
    }
    this.setParentTwoWithoutPercolation(parentTwo);
    if (this.parentTwo.get() != null) {
      this.parentTwo.get().addParentTwoChildGWithoutPercolation((ChildG) this);
    }
  }

  protected void setParentTwoWithoutPercolation(ParentG parentTwo) {
    this.getChanged().record("parentTwo", this.parentTwo.get(), parentTwo);
    this.parentTwo.set(parentTwo);
  }

  public ChildGChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ChildGChanged((ChildG) this);
    }
    return (ChildGChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentOne(null);
    this.setParentTwo(null);
  }

  static class Shims {
    protected static final Shim<ChildG, Long> id = new Shim<ChildG, Long>() {
      public void set(ChildG instance, Long id) {
        ((ChildGCodegen) instance).id = id;
      }
      public Long get(ChildG instance) {
        return ((ChildGCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ChildG, String> name = new Shim<ChildG, String>() {
      public void set(ChildG instance, String name) {
        ((ChildGCodegen) instance).name = name;
      }
      public String get(ChildG instance) {
        return ((ChildGCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ChildG, Long> version = new Shim<ChildG, Long>() {
      public void set(ChildG instance, Long version) {
        ((ChildGCodegen) instance).version = version;
      }
      public Long get(ChildG instance) {
        return ((ChildGCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ChildG, Long> parentOneId = new Shim<ChildG, Long>() {
      public void set(ChildG instance, Long parentOneId) {
        ((ChildGCodegen) instance).parentOne.setId(parentOneId);
      }
      public Long get(ChildG instance) {
        return ((ChildGCodegen) instance).parentOne.getId();
      }
      public String getName() {
        return "parentOne";
      }
    };
    protected static final Shim<ChildG, Long> parentTwoId = new Shim<ChildG, Long>() {
      public void set(ChildG instance, Long parentTwoId) {
        ((ChildGCodegen) instance).parentTwo.setId(parentTwoId);
      }
      public Long get(ChildG instance) {
        return ((ChildGCodegen) instance).parentTwo.getId();
      }
      public String getName() {
        return "parentTwo";
      }
    };
  }

  public static class ChildGChanged extends AbstractChanged {
    public ChildGChanged(ChildG instance) {
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
    public boolean hasParentOne() {
      return this.contains("parentOne");
    }
    public ParentG getOriginalParentOne() {
      return (ParentG) this.getOriginal("parentOne");
    }
    public boolean hasParentTwo() {
      return this.contains("parentTwo");
    }
    public ParentG getOriginalParentTwo() {
      return (ParentG) this.getOriginal("parentTwo");
    }
  }

}
