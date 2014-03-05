package features.domain;

import features.domain.queries.ManyToManyBFooToBarQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ManyToManyBFooToBarCodegen extends AbstractDomainObject {

  public static final ManyToManyBFooToBarQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBBar> owned = new ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBBar>(ManyToManyBFooToBar.class, ManyToManyBBar.class, Aliases.manyToManyBBar(), Aliases.manyToManyBFooToBar().owned);
  private final ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBFoo> ownerManyToManyBFoo = new ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBFoo>(ManyToManyBFooToBar.class, ManyToManyBFoo.class, Aliases.manyToManyBFoo(), Aliases.manyToManyBFooToBar().ownerManyToManyBFoo);
  protected Changed changed;

  static {
    Aliases.manyToManyBFooToBar();
    queries = new ManyToManyBFooToBarQueries();
  }

  protected ManyToManyBFooToBarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ManyToManyBFooToBar>(Shims.ownedId));
    this.addRule(new NotNull<ManyToManyBFooToBar>(Shims.ownerManyToManyBFooId));
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

  public ManyToManyBBar getOwned() {
    return this.owned.get();
  }

  public void setOwned(ManyToManyBBar owned) {
    if (owned == this.getOwned()) {
      return;
    }
    if (this.owned.get() != null) {
      this.owned.get().removeManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
    this.setOwnedWithoutPercolation(owned);
    if (this.owned.get() != null) {
      this.owned.get().addManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
  }

  protected void setOwnedWithoutPercolation(ManyToManyBBar owned) {
    this.getChanged().record("owned", this.owned.get(), owned);
    this.owned.set(owned);
  }

  public ManyToManyBFoo getOwnerManyToManyBFoo() {
    return this.ownerManyToManyBFoo.get();
  }

  public void setOwnerManyToManyBFoo(ManyToManyBFoo ownerManyToManyBFoo) {
    if (ownerManyToManyBFoo == this.getOwnerManyToManyBFoo()) {
      return;
    }
    if (this.ownerManyToManyBFoo.get() != null) {
      this.ownerManyToManyBFoo.get().removeManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
    this.setOwnerManyToManyBFooWithoutPercolation(ownerManyToManyBFoo);
    if (this.ownerManyToManyBFoo.get() != null) {
      this.ownerManyToManyBFoo.get().addManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
  }

  protected void setOwnerManyToManyBFooWithoutPercolation(ManyToManyBFoo ownerManyToManyBFoo) {
    this.getChanged().record("ownerManyToManyBFoo", this.ownerManyToManyBFoo.get(), ownerManyToManyBFoo);
    this.ownerManyToManyBFoo.set(ownerManyToManyBFoo);
  }

  public ManyToManyBFooToBarChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ManyToManyBFooToBarChanged((ManyToManyBFooToBar) this);
    }
    return (ManyToManyBFooToBarChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setOwned(null);
    this.setOwnerManyToManyBFoo(null);
  }

  static class Shims {
    protected static final Shim<ManyToManyBFooToBar, Long> id = new Shim<ManyToManyBFooToBar, Long>() {
      public void set(ManyToManyBFooToBar instance, Long id) {
        ((ManyToManyBFooToBarCodegen) instance).id = id;
      }
      public Long get(ManyToManyBFooToBar instance) {
        return ((ManyToManyBFooToBarCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ManyToManyBFooToBar, Long> version = new Shim<ManyToManyBFooToBar, Long>() {
      public void set(ManyToManyBFooToBar instance, Long version) {
        ((ManyToManyBFooToBarCodegen) instance).version = version;
      }
      public Long get(ManyToManyBFooToBar instance) {
        return ((ManyToManyBFooToBarCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ManyToManyBFooToBar, Long> ownedId = new Shim<ManyToManyBFooToBar, Long>() {
      public void set(ManyToManyBFooToBar instance, Long ownedId) {
        ((ManyToManyBFooToBarCodegen) instance).owned.setId(ownedId);
      }
      public Long get(ManyToManyBFooToBar instance) {
        return ((ManyToManyBFooToBarCodegen) instance).owned.getId();
      }
      public String getName() {
        return "owned";
      }
    };
    protected static final Shim<ManyToManyBFooToBar, Long> ownerManyToManyBFooId = new Shim<ManyToManyBFooToBar, Long>() {
      public void set(ManyToManyBFooToBar instance, Long ownerManyToManyBFooId) {
        ((ManyToManyBFooToBarCodegen) instance).ownerManyToManyBFoo.setId(ownerManyToManyBFooId);
      }
      public Long get(ManyToManyBFooToBar instance) {
        return ((ManyToManyBFooToBarCodegen) instance).ownerManyToManyBFoo.getId();
      }
      public String getName() {
        return "ownerManyToManyBFoo";
      }
    };
  }

  public static class ManyToManyBFooToBarChanged extends AbstractChanged {
    public ManyToManyBFooToBarChanged(ManyToManyBFooToBar instance) {
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
    public boolean hasOwned() {
      return this.contains("owned");
    }
    public ManyToManyBBar getOriginalOwned() {
      return (ManyToManyBBar) this.getOriginal("owned");
    }
    public boolean hasOwnerManyToManyBFoo() {
      return this.contains("ownerManyToManyBFoo");
    }
    public ManyToManyBFoo getOriginalOwnerManyToManyBFoo() {
      return (ManyToManyBFoo) this.getOriginal("ownerManyToManyBFoo");
    }
  }

}
