package features.domain;

import features.domain.queries.ParentCBarQueries;
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
public abstract class ParentCBarCodegen extends AbstractDomainObject {

  public static final ParentCBarQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentCBar, ParentCFoo> firstParent = new ForeignKeyHolder<ParentCBar, ParentCFoo>(ParentCBar.class, ParentCFoo.class, Aliases.parentCFoo(), Aliases.parentCBar().firstParent);
  private final ForeignKeyHolder<ParentCBar, ParentCFoo> secondParent = new ForeignKeyHolder<ParentCBar, ParentCFoo>(ParentCBar.class, ParentCFoo.class, Aliases.parentCFoo(), Aliases.parentCBar().secondParent);
  protected Changed changed;

  static {
    Aliases.parentCBar();
    queries = new ParentCBarQueries();
  }

  protected ParentCBarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentCBar>(Shims.name));
    this.addRule(new MaxLength<ParentCBar>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentCBar>(Shims.name));
    this.addRule(new NotNull<ParentCBar>(Shims.firstParentId));
    this.addRule(new NotNull<ParentCBar>(Shims.secondParentId));
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

  public ParentCFoo getFirstParent() {
    return this.firstParent.get();
  }

  public void setFirstParent(ParentCFoo firstParent) {
    if (firstParent == this.getFirstParent()) {
      return;
    }
    if (this.firstParent.get() != null) {
      this.firstParent.get().removeFirstParentParentCBarWithoutPercolation((ParentCBar) this);
    }
    this.setFirstParentWithoutPercolation(firstParent);
    if (this.firstParent.get() != null) {
      this.firstParent.get().addFirstParentParentCBarWithoutPercolation((ParentCBar) this);
    }
  }

  protected void setFirstParentWithoutPercolation(ParentCFoo firstParent) {
    this.getChanged().record("firstParent", this.firstParent.get(), firstParent);
    this.firstParent.set(firstParent);
  }

  public ParentCFoo getSecondParent() {
    return this.secondParent.get();
  }

  public void setSecondParent(ParentCFoo secondParent) {
    if (secondParent == this.getSecondParent()) {
      return;
    }
    if (this.secondParent.get() != null) {
      this.secondParent.get().removeSecondParentParentCBarWithoutPercolation((ParentCBar) this);
    }
    this.setSecondParentWithoutPercolation(secondParent);
    if (this.secondParent.get() != null) {
      this.secondParent.get().addSecondParentParentCBarWithoutPercolation((ParentCBar) this);
    }
  }

  protected void setSecondParentWithoutPercolation(ParentCFoo secondParent) {
    this.getChanged().record("secondParent", this.secondParent.get(), secondParent);
    this.secondParent.set(secondParent);
  }

  public ParentCBarChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentCBarChanged((ParentCBar) this);
    }
    return (ParentCBarChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setFirstParent(null);
    this.setSecondParent(null);
  }

  static class Shims {
    protected static final Shim<ParentCBar, Long> id = new Shim<ParentCBar, Long>() {
      public void set(ParentCBar instance, Long id) {
        ((ParentCBarCodegen) instance).id = id;
      }
      public Long get(ParentCBar instance) {
        return ((ParentCBarCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentCBar, String> name = new Shim<ParentCBar, String>() {
      public void set(ParentCBar instance, String name) {
        ((ParentCBarCodegen) instance).name = name;
      }
      public String get(ParentCBar instance) {
        return ((ParentCBarCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentCBar, Long> version = new Shim<ParentCBar, Long>() {
      public void set(ParentCBar instance, Long version) {
        ((ParentCBarCodegen) instance).version = version;
      }
      public Long get(ParentCBar instance) {
        return ((ParentCBarCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentCBar, Long> firstParentId = new Shim<ParentCBar, Long>() {
      public void set(ParentCBar instance, Long firstParentId) {
        ((ParentCBarCodegen) instance).firstParent.setId(firstParentId);
      }
      public Long get(ParentCBar instance) {
        return ((ParentCBarCodegen) instance).firstParent.getId();
      }
      public String getName() {
        return "firstParent";
      }
    };
    protected static final Shim<ParentCBar, Long> secondParentId = new Shim<ParentCBar, Long>() {
      public void set(ParentCBar instance, Long secondParentId) {
        ((ParentCBarCodegen) instance).secondParent.setId(secondParentId);
      }
      public Long get(ParentCBar instance) {
        return ((ParentCBarCodegen) instance).secondParent.getId();
      }
      public String getName() {
        return "secondParent";
      }
    };
  }

  public static class ParentCBarChanged extends AbstractChanged {
    public ParentCBarChanged(ParentCBar instance) {
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
    public boolean hasFirstParent() {
      return this.contains("firstParent");
    }
    public ParentCFoo getOriginalFirstParent() {
      return (ParentCFoo) this.getOriginal("firstParent");
    }
    public boolean hasSecondParent() {
      return this.contains("secondParent");
    }
    public ParentCFoo getOriginalSecondParent() {
      return (ParentCFoo) this.getOriginal("secondParent");
    }
  }

}
