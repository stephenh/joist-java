package features.domain;

import features.domain.queries.ParentFQueries;
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
public abstract class ParentFCodegen extends AbstractDomainObject {

  public static final ParentFQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentF, ChildF> childOne = new ForeignKeyHolder<ParentF, ChildF>(ParentF.class, ChildF.class, Aliases.childF(), Aliases.parentF().childOne);
  private final ForeignKeyHolder<ParentF, ChildF> childTwo = new ForeignKeyHolder<ParentF, ChildF>(ParentF.class, ChildF.class, Aliases.childF(), Aliases.parentF().childTwo);
  protected Changed changed;

  static {
    Aliases.parentF();
    queries = new ParentFQueries();
  }

  protected ParentFCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentF>(Shims.name));
    this.addRule(new MaxLength<ParentF>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentF>(Shims.name));
    this.addRule(new NotNull<ParentF>(Shims.childOneId));
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

  public ChildF getChildOne() {
    return this.childOne.get();
  }

  public void setChildOne(ChildF childOne) {
    if (childOne == this.getChildOne()) {
      return;
    }
    if (this.childOne.get() != null) {
      this.childOne.get().removeChildOneParentFWithoutPercolation((ParentF) this);
    }
    this.setChildOneWithoutPercolation(childOne);
    if (this.childOne.get() != null) {
      this.childOne.get().addChildOneParentFWithoutPercolation((ParentF) this);
    }
  }

  protected void setChildOneWithoutPercolation(ChildF childOne) {
    this.getChanged().record("childOne", this.childOne.get(), childOne);
    this.childOne.set(childOne);
  }

  public ChildF getChildTwo() {
    return this.childTwo.get();
  }

  public void setChildTwo(ChildF childTwo) {
    if (childTwo == this.getChildTwo()) {
      return;
    }
    if (this.childTwo.get() != null) {
      this.childTwo.get().removeChildTwoParentFWithoutPercolation((ParentF) this);
    }
    this.setChildTwoWithoutPercolation(childTwo);
    if (this.childTwo.get() != null) {
      this.childTwo.get().addChildTwoParentFWithoutPercolation((ParentF) this);
    }
  }

  protected void setChildTwoWithoutPercolation(ChildF childTwo) {
    this.getChanged().record("childTwo", this.childTwo.get(), childTwo);
    this.childTwo.set(childTwo);
  }

  public ParentFChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentFChanged((ParentF) this);
    }
    return (ParentFChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setChildOne(null);
    this.setChildTwo(null);
  }

  static class Shims {
    protected static final Shim<ParentF, Long> id = new Shim<ParentF, Long>() {
      public void set(ParentF instance, Long id) {
        ((ParentFCodegen) instance).id = id;
      }
      public Long get(ParentF instance) {
        return ((ParentFCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentF, String> name = new Shim<ParentF, String>() {
      public void set(ParentF instance, String name) {
        ((ParentFCodegen) instance).name = name;
      }
      public String get(ParentF instance) {
        return ((ParentFCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentF, Long> version = new Shim<ParentF, Long>() {
      public void set(ParentF instance, Long version) {
        ((ParentFCodegen) instance).version = version;
      }
      public Long get(ParentF instance) {
        return ((ParentFCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentF, Long> childOneId = new Shim<ParentF, Long>() {
      public void set(ParentF instance, Long childOneId) {
        ((ParentFCodegen) instance).childOne.setId(childOneId);
      }
      public Long get(ParentF instance) {
        return ((ParentFCodegen) instance).childOne.getId();
      }
      public String getName() {
        return "childOne";
      }
    };
    protected static final Shim<ParentF, Long> childTwoId = new Shim<ParentF, Long>() {
      public void set(ParentF instance, Long childTwoId) {
        ((ParentFCodegen) instance).childTwo.setId(childTwoId);
      }
      public Long get(ParentF instance) {
        return ((ParentFCodegen) instance).childTwo.getId();
      }
      public String getName() {
        return "childTwo";
      }
    };
  }

  public static class ParentFChanged extends AbstractChanged {
    public ParentFChanged(ParentF instance) {
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
    public boolean hasChildOne() {
      return this.contains("childOne");
    }
    public ChildF getOriginalChildOne() {
      return (ChildF) this.getOriginal("childOne");
    }
    public boolean hasChildTwo() {
      return this.contains("childTwo");
    }
    public ChildF getOriginalChildTwo() {
      return (ChildF) this.getOriginal("childTwo");
    }
  }

}
