package features.domain;

import features.domain.queries.ManyToManyBBarQueries;
import java.util.ArrayList;
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
  private final ForeignKeyListHolder<ManyToManyBBar, ManyToManyBFooToBar> ownedManyToManyBFooToBars = new ForeignKeyListHolder<ManyToManyBBar, ManyToManyBFooToBar>((ManyToManyBBar) this, Aliases.manyToManyBFooToBar(), Aliases.manyToManyBFooToBar().owned, new OwnedManyToManyBFooToBarsListDelegate());
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

  private List<ManyToManyBFooToBar> getOwnedManyToManyBFooToBars() {
    return this.ownedManyToManyBFooToBars.get();
  }

  private void setOwnedManyToManyBFooToBars(List<ManyToManyBFooToBar> ownedManyToManyBFooToBars) {
    ListDiff<ManyToManyBFooToBar> diff = ListDiff.of(this.getOwnedManyToManyBFooToBars(), ownedManyToManyBFooToBars);
    for (ManyToManyBFooToBar o : diff.removed) {
      this.removeOwnedManyToManyBFooToBar(o);
    }
    for (ManyToManyBFooToBar o : diff.added) {
      this.addOwnedManyToManyBFooToBar(o);
    }
  }

  private void addOwnedManyToManyBFooToBar(ManyToManyBFooToBar o) {
    if (o.getOwned() == this) {
      return;
    }
    o.setOwnedWithoutPercolation((ManyToManyBBar) this);
    this.addOwnedManyToManyBFooToBarWithoutPercolation(o);
  }

  private void removeOwnedManyToManyBFooToBar(ManyToManyBFooToBar o) {
    if (o.getOwned() != this) {
      return;
    }
    o.setOwnedWithoutPercolation(null);
    this.removeOwnedManyToManyBFooToBarWithoutPercolation(o);
    if (UoW.isOpen()) {
      ManyToManyBFooToBar.queries.delete(o);
    }
  }

  protected void addOwnedManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
    this.getChanged().record("ownedManyToManyBFooToBars");
    this.ownedManyToManyBFooToBars.add(o);
  }

  protected void removeOwnedManyToManyBFooToBarWithoutPercolation(ManyToManyBFooToBar o) {
    this.getChanged().record("ownedManyToManyBFooToBars");
    this.ownedManyToManyBFooToBars.remove(o);
  }

  public List<ManyToManyBFoo> getOwnerManyToManyBFoos() {
    List<ManyToManyBFoo> l = new ArrayList<ManyToManyBFoo>();
    for (ManyToManyBFooToBar o : this.getOwnedManyToManyBFooToBars()) {
      l.add(o.getOwnerManyToManyBFoo());
    }
    return l;
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
    for (ManyToManyBFooToBar a : Copy.list(this.getOwnedManyToManyBFooToBars())) {
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
    for (ManyToManyBFooToBar o : Copy.list(this.getOwnedManyToManyBFooToBars())) {
      removeOwnedManyToManyBFooToBar(o);
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

  private class OwnedManyToManyBFooToBarsListDelegate implements ListProxy.Delegate<ManyToManyBFooToBar> {
    public void doAdd(ManyToManyBFooToBar e) {
      addOwnedManyToManyBFooToBar(e);
    }
    public void doRemove(ManyToManyBFooToBar e) {
      removeOwnedManyToManyBFooToBar(e);
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
    public boolean hasOwnedManyToManyBFooToBars() {
      return this.contains("ownedManyToManyBFooToBars");
    }
  }

}
