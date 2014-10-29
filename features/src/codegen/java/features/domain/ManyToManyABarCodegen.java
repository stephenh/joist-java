package features.domain;

import features.domain.queries.ManyToManyABarQueries;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.util.ListProxy;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;
import joist.util.ListDiff;

@SuppressWarnings("all")
public abstract class ManyToManyABarCodegen extends AbstractDomainObject {

  public static final ManyToManyABarQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ManyToManyABar, ManyToManyAFooToBar> manyToManyAFooToBars = new ForeignKeyListHolder<ManyToManyABar, ManyToManyAFooToBar>((ManyToManyABar) this, Aliases.manyToManyAFooToBar(), Aliases.manyToManyAFooToBar().manyToManyABar, new ManyToManyAFooToBarsListDelegate());
  protected Changed changed;

  static {
    Aliases.manyToManyABar();
    queries = new ManyToManyABarQueries();
  }

  protected ManyToManyABarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ManyToManyABar>(Shims.name));
    this.addRule(new MaxLength<ManyToManyABar>(Shims.name, 100));
    this.addRule(new NotEmpty<ManyToManyABar>(Shims.name));
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

  private List<ManyToManyAFooToBar> getManyToManyAFooToBars() {
    return this.manyToManyAFooToBars.get();
  }

  private void setManyToManyAFooToBars(List<ManyToManyAFooToBar> manyToManyAFooToBars) {
    ListDiff<ManyToManyAFooToBar> diff = ListDiff.of(this.getManyToManyAFooToBars(), manyToManyAFooToBars);
    for (ManyToManyAFooToBar o : diff.removed) {
      this.removeManyToManyAFooToBar(o);
    }
    for (ManyToManyAFooToBar o : diff.added) {
      this.addManyToManyAFooToBar(o);
    }
    this.manyToManyAFooToBars.set(manyToManyAFooToBars);
  }

  private void addManyToManyAFooToBar(ManyToManyAFooToBar o) {
    if (o.getManyToManyABar() == this) {
      return;
    }
    o.setManyToManyABarWithoutPercolation((ManyToManyABar) this);
    this.addManyToManyAFooToBarWithoutPercolation(o);
  }

  private void removeManyToManyAFooToBar(ManyToManyAFooToBar o) {
    if (o.getManyToManyABar() != this) {
      return;
    }
    o.setManyToManyABarWithoutPercolation(null);
    this.removeManyToManyAFooToBarWithoutPercolation(o);
    if (UoW.isOpen()) {
      ManyToManyAFooToBar.queries.delete(o);
    }
  }

  protected void addManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
    this.getChanged().record("manyToManyAFooToBars");
    this.manyToManyAFooToBars.add(o);
  }

  protected void removeManyToManyAFooToBarWithoutPercolation(ManyToManyAFooToBar o) {
    this.getChanged().record("manyToManyAFooToBars");
    this.manyToManyAFooToBars.remove(o);
  }

  public List<ManyToManyAFoo> getManyToManyAFoos() {
    List<ManyToManyAFoo> l = new ArrayList<ManyToManyAFoo>();
    for (ManyToManyAFooToBar o : this.getManyToManyAFooToBars()) {
      l.add(o.getManyToManyAFoo());
    }
    return Collections.unmodifiableList(l);
  }

  public void setManyToManyAFoos(List<ManyToManyAFoo> manyToManyAFoos) {
    ListDiff<ManyToManyAFoo> diff = ListDiff.of(this.getManyToManyAFoos(), manyToManyAFoos);
    for (ManyToManyAFoo o : diff.removed) {
      this.removeManyToManyAFoo(o);
    }
    for (ManyToManyAFoo o : diff.added) {
      this.addManyToManyAFoo(o);
    }
  }

  public void addManyToManyAFoo(ManyToManyAFoo o) {
    ManyToManyAFooToBar a = new ManyToManyAFooToBar();
    a.setManyToManyABar((ManyToManyABar) this);
    a.setManyToManyAFoo(o);
  }

  public void removeManyToManyAFoo(ManyToManyAFoo o) {
    for (ManyToManyAFooToBar a : Copy.list(this.getManyToManyAFooToBars())) {
      if (a.getManyToManyAFoo().equals(o)) {
        a.setManyToManyAFoo(null);
        a.setManyToManyABar(null);
        if (UoW.isOpen()) {
          UoW.delete(a);
        }
      }
    }
  }

  public ManyToManyABarChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ManyToManyABarChanged((ManyToManyABar) this);
    }
    return (ManyToManyABarChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ManyToManyAFooToBar o : Copy.list(this.getManyToManyAFooToBars())) {
      removeManyToManyAFooToBar(o);
    }
  }

  static class Shims {
    protected static final Shim<ManyToManyABar, Long> id = new Shim<ManyToManyABar, Long>() {
      public void set(ManyToManyABar instance, Long id) {
        ((ManyToManyABarCodegen) instance).id = id;
      }
      public Long get(ManyToManyABar instance) {
        return ((ManyToManyABarCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ManyToManyABar, String> name = new Shim<ManyToManyABar, String>() {
      public void set(ManyToManyABar instance, String name) {
        ((ManyToManyABarCodegen) instance).name = name;
      }
      public String get(ManyToManyABar instance) {
        return ((ManyToManyABarCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ManyToManyABar, Long> version = new Shim<ManyToManyABar, Long>() {
      public void set(ManyToManyABar instance, Long version) {
        ((ManyToManyABarCodegen) instance).version = version;
      }
      public Long get(ManyToManyABar instance) {
        return ((ManyToManyABarCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ManyToManyAFooToBarsListDelegate implements ListProxy.Delegate<ManyToManyAFooToBar> {
    public void doAdd(ManyToManyAFooToBar e) {
      addManyToManyAFooToBar(e);
    }
    public void doRemove(ManyToManyAFooToBar e) {
      removeManyToManyAFooToBar(e);
    }
  }

  public static class ManyToManyABarChanged extends AbstractChanged {
    public ManyToManyABarChanged(ManyToManyABar instance) {
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
    public boolean hasManyToManyAFooToBars() {
      return this.contains("manyToManyAFooToBars");
    }
  }

}
