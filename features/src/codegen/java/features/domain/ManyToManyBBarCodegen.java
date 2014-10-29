package features.domain;

import features.domain.queries.ManyToManyBBarQueries;
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
public abstract class ManyToManyBBarCodegen extends AbstractDomainObject {

  public static final ManyToManyBBarQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ManyToManyBBar, ManyToManyBFooToBar> manyToManyBFooToBars = new ForeignKeyListHolder<ManyToManyBBar, ManyToManyBFooToBar>((ManyToManyBBar) this, Aliases.manyToManyBFooToBar(), Aliases.manyToManyBFooToBar().owned, new ManyToManyBFooToBarsListDelegate());
  protected Changed changed;

  static {
    Aliases.manyToManyBBar();
    queries = new ManyToManyBBarQueries();
  }

  protected ManyToManyBBarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ManyToManyBBar>(Shims.name));
    this.addRule(new MaxLength<ManyToManyBBar>(Shims.name, 100));
    this.addRule(new NotEmpty<ManyToManyBBar>(Shims.name));
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

  private List<ManyToManyBFooToBar> getManyToManyBFooToBars() {
    return this.manyToManyBFooToBars.get();
  }

  private void setManyToManyBFooToBars(List<ManyToManyBFooToBar> manyToManyBFooToBars) {
    ListDiff<ManyToManyBFooToBar> diff = ListDiff.of(this.getManyToManyBFooToBars(), manyToManyBFooToBars);
    for (ManyToManyBFooToBar o : diff.removed) {
      this.removeManyToManyBFooToBar(o);
    }
    for (ManyToManyBFooToBar o : diff.added) {
      this.addManyToManyBFooToBar(o);
    }
    this.manyToManyBFooToBars.set(manyToManyBFooToBars);
  }

  private void addManyToManyBFooToBar(ManyToManyBFooToBar o) {
    if (o.getOwned() == this) {
      return;
    }
    o.setOwnedWithoutPercolation((ManyToManyBBar) this);
    this.addManyToManyBFooToBarWithoutPercolation(o);
  }

  private void removeManyToManyBFooToBar(ManyToManyBFooToBar o) {
    if (o.getOwned() != this) {
      return;
    }
    o.setOwnedWithoutPercolation(null);
    this.removeManyToManyBFooToBarWithoutPercolation(o);
    if (UoW.isOpen()) {
      ManyToManyBFooToBar.queries.delete(o);
    }
  }

  protected void addManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
    this.getChanged().record("manyToManyBFooToBars");
    this.manyToManyBFooToBars.add(o);
  }

  protected void removeManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
    this.getChanged().record("manyToManyBFooToBars");
    this.manyToManyBFooToBars.remove(o);
  }

  public List<ManyToManyBFoo> getOwnerManyToManyBFoos() {
    List<ManyToManyBFoo> l = new ArrayList<ManyToManyBFoo>();
    for (ManyToManyBFooToBar o : this.getManyToManyBFooToBars()) {
      l.add(o.getOwnerManyToManyBFoo());
    }
    return Collections.unmodifiableList(l);
  }

  public void setOwnerManyToManyBFoos(List<ManyToManyBFoo> ownerManyToManyBFoos) {
    ListDiff<ManyToManyBFoo> diff = ListDiff.of(this.getOwnerManyToManyBFoos(), ownerManyToManyBFoos);
    for (ManyToManyBFoo o : diff.removed) {
      this.removeOwnerManyToManyBFoo(o);
    }
    for (ManyToManyBFoo o : diff.added) {
      this.addOwnerManyToManyBFoo(o);
    }
  }

  public void addOwnerManyToManyBFoo(ManyToManyBFoo o) {
    ManyToManyBFooToBar a = new ManyToManyBFooToBar();
    a.setOwned((ManyToManyBBar) this);
    a.setOwnerManyToManyBFoo(o);
  }

  public void removeOwnerManyToManyBFoo(ManyToManyBFoo o) {
    for (ManyToManyBFooToBar a : Copy.list(this.getManyToManyBFooToBars())) {
      if (a.getOwnerManyToManyBFoo().equals(o)) {
        a.setOwnerManyToManyBFoo(null);
        a.setOwned(null);
        if (UoW.isOpen()) {
          UoW.delete(a);
        }
      }
    }
  }

  public ManyToManyBBarChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ManyToManyBBarChanged((ManyToManyBBar) this);
    }
    return (ManyToManyBBarChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ManyToManyBFooToBar o : Copy.list(this.getManyToManyBFooToBars())) {
      removeManyToManyBFooToBar(o);
    }
  }

  static class Shims {
    protected static final Shim<ManyToManyBBar, Long> id = new Shim<ManyToManyBBar, Long>() {
      public void set(ManyToManyBBar instance, Long id) {
        ((ManyToManyBBarCodegen) instance).id = id;
      }
      public Long get(ManyToManyBBar instance) {
        return ((ManyToManyBBarCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ManyToManyBBar, String> name = new Shim<ManyToManyBBar, String>() {
      public void set(ManyToManyBBar instance, String name) {
        ((ManyToManyBBarCodegen) instance).name = name;
      }
      public String get(ManyToManyBBar instance) {
        return ((ManyToManyBBarCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ManyToManyBBar, Long> version = new Shim<ManyToManyBBar, Long>() {
      public void set(ManyToManyBBar instance, Long version) {
        ((ManyToManyBBarCodegen) instance).version = version;
      }
      public Long get(ManyToManyBBar instance) {
        return ((ManyToManyBBarCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ManyToManyBFooToBarsListDelegate implements ListProxy.Delegate<ManyToManyBFooToBar> {
    public void doAdd(ManyToManyBFooToBar e) {
      addManyToManyBFooToBar(e);
    }
    public void doRemove(ManyToManyBFooToBar e) {
      removeManyToManyBFooToBar(e);
    }
  }

  public static class ManyToManyBBarChanged extends AbstractChanged {
    public ManyToManyBBarChanged(ManyToManyBBar instance) {
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
    public boolean hasManyToManyBFooToBars() {
      return this.contains("manyToManyBFooToBars");
    }
  }

}
