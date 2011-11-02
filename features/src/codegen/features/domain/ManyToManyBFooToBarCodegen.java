package features.domain;

import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;
import features.domain.queries.ManyToManyBFooToBarQueries;

@SuppressWarnings("all")
public abstract class ManyToManyBFooToBarCodegen extends AbstractDomainObject {

  public static final ManyToManyBFooToBarQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBFoo> blue = new ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBFoo>(ManyToManyBFooToBar.class, ManyToManyBFoo.class, Aliases.manyToManyBFoo(), Aliases.manyToManyBFooToBar().blue);
  private final ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBBar> green = new ForeignKeyHolder<ManyToManyBFooToBar, ManyToManyBBar>(ManyToManyBFooToBar.class, ManyToManyBBar.class, Aliases.manyToManyBBar(), Aliases.manyToManyBFooToBar().green);
  protected Changed changed;

  static {
    Aliases.manyToManyBFooToBar();
    queries = new ManyToManyBFooToBarQueries();
  }

  protected ManyToManyBFooToBarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ManyToManyBFooToBar>(Shims.blueId));
    this.addRule(new NotNull<ManyToManyBFooToBar>(Shims.greenId));
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.getChanged().record("id", this.id, id);
    this.id = id;
    if (UoW.isOpen()) {
      UoW.getIdentityMap().store(this);
    }
  }

  public Long getVersion() {
    return this.version;
  }

  public ManyToManyBFoo getBlue() {
    return this.blue.get();
  }

  public void setBlue(ManyToManyBFoo blue) {
    if (this.blue.get() != null) {
       this.blue.get().removeBlueManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
    this.setBlueWithoutPercolation(blue);
    if (this.blue.get() != null) {
       this.blue.get().addBlueManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
  }

  protected void setBlueWithoutPercolation(ManyToManyBFoo blue) {
    this.getChanged().record("blue", this.blue, blue);
    this.blue.set(blue);
  }

  public ManyToManyBBar getGreen() {
    return this.green.get();
  }

  public void setGreen(ManyToManyBBar green) {
    if (this.green.get() != null) {
       this.green.get().removeGreenManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
    this.setGreenWithoutPercolation(green);
    if (this.green.get() != null) {
       this.green.get().addGreenManyToManyBFooToBarWithoutPercolation((ManyToManyBFooToBar) this);
    }
  }

  protected void setGreenWithoutPercolation(ManyToManyBBar green) {
    this.getChanged().record("green", this.green, green);
    this.green.set(green);
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
    this.setBlue(null);
    this.setGreen(null);
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
    protected static final Shim<ManyToManyBFooToBar, Long> blueId = new Shim<ManyToManyBFooToBar, Long>() {
      public void set(ManyToManyBFooToBar instance, Long blueId) {
        ((ManyToManyBFooToBarCodegen) instance).blue.setId(blueId);
      }
      public Long get(ManyToManyBFooToBar instance) {
        return ((ManyToManyBFooToBarCodegen) instance).blue.getId();
      }
      public String getName() {
        return "blue";
      }
    };
    protected static final Shim<ManyToManyBFooToBar, Long> greenId = new Shim<ManyToManyBFooToBar, Long>() {
      public void set(ManyToManyBFooToBar instance, Long greenId) {
        ((ManyToManyBFooToBarCodegen) instance).green.setId(greenId);
      }
      public Long get(ManyToManyBFooToBar instance) {
        return ((ManyToManyBFooToBarCodegen) instance).green.getId();
      }
      public String getName() {
        return "green";
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
    public boolean hasBlue() {
      return this.contains("blue");
    }
    public ManyToManyBFoo getOriginalBlue() {
      return (ManyToManyBFoo) this.getOriginal("blue");
    }
    public boolean hasGreen() {
      return this.contains("green");
    }
    public ManyToManyBBar getOriginalGreen() {
      return (ManyToManyBBar) this.getOriginal("green");
    }
  }

}
