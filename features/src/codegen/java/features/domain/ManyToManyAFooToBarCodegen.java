package features.domain;

import features.domain.queries.ManyToManyAFooToBarQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ManyToManyAFooToBarCodegen extends AbstractDomainObject {

  public static final ManyToManyAFooToBarQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyHolder<ManyToManyAFooToBar, ManyToManyABar> manyToManyABar = new ForeignKeyHolder<ManyToManyAFooToBar, ManyToManyABar>(ManyToManyAFooToBar.class, ManyToManyABar.class, Aliases.manyToManyABar(), Aliases.manyToManyAFooToBar().manyToManyABar);
  private final ForeignKeyHolder<ManyToManyAFooToBar, ManyToManyAFoo> manyToManyAFoo = new ForeignKeyHolder<ManyToManyAFooToBar, ManyToManyAFoo>(ManyToManyAFooToBar.class, ManyToManyAFoo.class, Aliases.manyToManyAFoo(), Aliases.manyToManyAFooToBar().manyToManyAFoo);
  protected Changed changed;

  static {
    Aliases.manyToManyAFooToBar();
    queries = new ManyToManyAFooToBarQueries();
  }

  protected ManyToManyAFooToBarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ManyToManyAFooToBar>(Shims.manyToManyABarId));
    this.addRule(new NotNull<ManyToManyAFooToBar>(Shims.manyToManyAFooId));
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

  public ManyToManyABar getManyToManyABar() {
    return this.manyToManyABar.get();
  }

  public void setManyToManyABar(ManyToManyABar manyToManyABar) {
    if (manyToManyABar == this.getManyToManyABar()) {
      return;
    }
    if (this.manyToManyABar.get() != null) {
      this.manyToManyABar.get().removeManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
    }
    this.setManyToManyABarWithoutPercolation(manyToManyABar);
    if (this.manyToManyABar.get() != null) {
      this.manyToManyABar.get().addManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
    }
  }

  protected void setManyToManyABarWithoutPercolation(ManyToManyABar manyToManyABar) {
    this.getChanged().record("manyToManyABar", this.manyToManyABar.get(), manyToManyABar);
    this.manyToManyABar.set(manyToManyABar);
  }

  public ManyToManyAFoo getManyToManyAFoo() {
    return this.manyToManyAFoo.get();
  }

  public void setManyToManyAFoo(ManyToManyAFoo manyToManyAFoo) {
    if (manyToManyAFoo == this.getManyToManyAFoo()) {
      return;
    }
    if (this.manyToManyAFoo.get() != null) {
      this.manyToManyAFoo.get().removeManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
    }
    this.setManyToManyAFooWithoutPercolation(manyToManyAFoo);
    if (this.manyToManyAFoo.get() != null) {
      this.manyToManyAFoo.get().addManyToManyAFooToBarWithoutPercolation((ManyToManyAFooToBar) this);
    }
  }

  protected void setManyToManyAFooWithoutPercolation(ManyToManyAFoo manyToManyAFoo) {
    this.getChanged().record("manyToManyAFoo", this.manyToManyAFoo.get(), manyToManyAFoo);
    this.manyToManyAFoo.set(manyToManyAFoo);
  }

  public ManyToManyAFooToBarChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ManyToManyAFooToBarChanged((ManyToManyAFooToBar) this);
    }
    return (ManyToManyAFooToBarChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setManyToManyABar(null);
    this.setManyToManyAFoo(null);
  }

  static class Shims {
    protected static final Shim<ManyToManyAFooToBar, Long> id = new Shim<ManyToManyAFooToBar, Long>() {
      public void set(ManyToManyAFooToBar instance, Long id) {
        ((ManyToManyAFooToBarCodegen) instance).id = id;
      }
      public Long get(ManyToManyAFooToBar instance) {
        return ((ManyToManyAFooToBarCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ManyToManyAFooToBar, Long> version = new Shim<ManyToManyAFooToBar, Long>() {
      public void set(ManyToManyAFooToBar instance, Long version) {
        ((ManyToManyAFooToBarCodegen) instance).version = version;
      }
      public Long get(ManyToManyAFooToBar instance) {
        return ((ManyToManyAFooToBarCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ManyToManyAFooToBar, Long> manyToManyABarId = new Shim<ManyToManyAFooToBar, Long>() {
      public void set(ManyToManyAFooToBar instance, Long manyToManyABarId) {
        ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.setId(manyToManyABarId);
      }
      public Long get(ManyToManyAFooToBar instance) {
        return ((ManyToManyAFooToBarCodegen) instance).manyToManyABar.getId();
      }
      public String getName() {
        return "manyToManyABar";
      }
    };
    protected static final Shim<ManyToManyAFooToBar, Long> manyToManyAFooId = new Shim<ManyToManyAFooToBar, Long>() {
      public void set(ManyToManyAFooToBar instance, Long manyToManyAFooId) {
        ((ManyToManyAFooToBarCodegen) instance).manyToManyAFoo.setId(manyToManyAFooId);
      }
      public Long get(ManyToManyAFooToBar instance) {
        return ((ManyToManyAFooToBarCodegen) instance).manyToManyAFoo.getId();
      }
      public String getName() {
        return "manyToManyAFoo";
      }
    };
  }

  public static class ManyToManyAFooToBarChanged extends AbstractChanged {
    public ManyToManyAFooToBarChanged(ManyToManyAFooToBar instance) {
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
    public boolean hasManyToManyABar() {
      return this.contains("manyToManyABar");
    }
    public ManyToManyABar getOriginalManyToManyABar() {
      return (ManyToManyABar) this.getOriginal("manyToManyABar");
    }
    public boolean hasManyToManyAFoo() {
      return this.contains("manyToManyAFoo");
    }
    public ManyToManyAFoo getOriginalManyToManyAFoo() {
      return (ManyToManyAFoo) this.getOriginal("manyToManyAFoo");
    }
  }

}
