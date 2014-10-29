package features.domain;

import features.domain.queries.ParentBParentQueries;
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
public abstract class ParentBParentCodegen extends AbstractDomainObject {

  public static final ParentBParentQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ParentBParent, ParentBChildBar> parentBChildBars = new ForeignKeyListHolder<ParentBParent, ParentBChildBar>((ParentBParent) this, Aliases.parentBChildBar(), Aliases.parentBChildBar().parentBParent, new ParentBChildBarsListDelegate());
  private final ForeignKeyListHolder<ParentBParent, ParentBChildFoo> parentBChildFoos = new ForeignKeyListHolder<ParentBParent, ParentBChildFoo>((ParentBParent) this, Aliases.parentBChildFoo(), Aliases.parentBChildFoo().parentBParent, new ParentBChildFoosListDelegate());
  private final ForeignKeyListHolder<ParentBParent, ParentBChildZaz> parentBChildZazs = new ForeignKeyListHolder<ParentBParent, ParentBChildZaz>((ParentBParent) this, Aliases.parentBChildZaz(), Aliases.parentBChildZaz().parentBParent, new ParentBChildZazsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentBParent();
    queries = new ParentBParentQueries();
  }

  protected ParentBParentCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentBParent>(Shims.name));
    this.addRule(new MaxLength<ParentBParent>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentBParent>(Shims.name));
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

  public List<ParentBChildBar> getParentBChildBars() {
    return this.parentBChildBars.get();
  }

  public void setParentBChildBars(List<ParentBChildBar> parentBChildBars) {
    ListDiff<ParentBChildBar> diff = ListDiff.of(this.getParentBChildBars(), parentBChildBars);
    for (ParentBChildBar o : diff.removed) {
      this.removeParentBChildBar(o);
    }
    for (ParentBChildBar o : diff.added) {
      this.addParentBChildBar(o);
    }
    this.parentBChildBars.set(parentBChildBars);
  }

  public void addParentBChildBar(ParentBChildBar o) {
    if (o.getParentBParent() == this) {
      return;
    }
    o.setParentBParentWithoutPercolation((ParentBParent) this);
    this.addParentBChildBarWithoutPercolation(o);
  }

  public void removeParentBChildBar(ParentBChildBar o) {
    if (o.getParentBParent() != this) {
      return;
    }
    o.setParentBParentWithoutPercolation(null);
    this.removeParentBChildBarWithoutPercolation(o);
  }

  protected void addParentBChildBarWithoutPercolation(ParentBChildBar o) {
    this.getChanged().record("parentBChildBars");
    this.parentBChildBars.add(o);
  }

  protected void removeParentBChildBarWithoutPercolation(ParentBChildBar o) {
    this.getChanged().record("parentBChildBars");
    this.parentBChildBars.remove(o);
  }

  public List<ParentBChildFoo> getParentBChildFoos() {
    return this.parentBChildFoos.get();
  }

  public void setParentBChildFoos(List<ParentBChildFoo> parentBChildFoos) {
    ListDiff<ParentBChildFoo> diff = ListDiff.of(this.getParentBChildFoos(), parentBChildFoos);
    for (ParentBChildFoo o : diff.removed) {
      this.removeParentBChildFoo(o);
    }
    for (ParentBChildFoo o : diff.added) {
      this.addParentBChildFoo(o);
    }
    this.parentBChildFoos.set(parentBChildFoos);
  }

  public void addParentBChildFoo(ParentBChildFoo o) {
    if (o.getParentBParent() == this) {
      return;
    }
    o.setParentBParentWithoutPercolation((ParentBParent) this);
    this.addParentBChildFooWithoutPercolation(o);
  }

  public void removeParentBChildFoo(ParentBChildFoo o) {
    if (o.getParentBParent() != this) {
      return;
    }
    o.setParentBParentWithoutPercolation(null);
    this.removeParentBChildFooWithoutPercolation(o);
  }

  protected void addParentBChildFooWithoutPercolation(ParentBChildFoo o) {
    this.getChanged().record("parentBChildFoos");
    this.parentBChildFoos.add(o);
  }

  protected void removeParentBChildFooWithoutPercolation(ParentBChildFoo o) {
    this.getChanged().record("parentBChildFoos");
    this.parentBChildFoos.remove(o);
  }

  public List<ParentBChildZaz> getParentBChildZazs() {
    return this.parentBChildZazs.get();
  }

  public void setParentBChildZazs(List<ParentBChildZaz> parentBChildZazs) {
    ListDiff<ParentBChildZaz> diff = ListDiff.of(this.getParentBChildZazs(), parentBChildZazs);
    for (ParentBChildZaz o : diff.removed) {
      this.removeParentBChildZaz(o);
    }
    for (ParentBChildZaz o : diff.added) {
      this.addParentBChildZaz(o);
    }
    this.parentBChildZazs.set(parentBChildZazs);
  }

  public void addParentBChildZaz(ParentBChildZaz o) {
    if (o.getParentBParent() == this) {
      return;
    }
    o.setParentBParentWithoutPercolation((ParentBParent) this);
    this.addParentBChildZazWithoutPercolation(o);
  }

  public void removeParentBChildZaz(ParentBChildZaz o) {
    if (o.getParentBParent() != this) {
      return;
    }
    o.setParentBParentWithoutPercolation(null);
    this.removeParentBChildZazWithoutPercolation(o);
  }

  protected void addParentBChildZazWithoutPercolation(ParentBChildZaz o) {
    this.getChanged().record("parentBChildZazs");
    this.parentBChildZazs.add(o);
  }

  protected void removeParentBChildZazWithoutPercolation(ParentBChildZaz o) {
    this.getChanged().record("parentBChildZazs");
    this.parentBChildZazs.remove(o);
  }

  public ParentBParentChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentBParentChanged((ParentBParent) this);
    }
    return (ParentBParentChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ParentBChildBar o : Copy.list(this.getParentBChildBars())) {
      removeParentBChildBar(o);
    }
    for (ParentBChildFoo o : Copy.list(this.getParentBChildFoos())) {
      removeParentBChildFoo(o);
    }
    for (ParentBChildZaz o : Copy.list(this.getParentBChildZazs())) {
      removeParentBChildZaz(o);
    }
  }

  static class Shims {
    protected static final Shim<ParentBParent, Long> id = new Shim<ParentBParent, Long>() {
      public void set(ParentBParent instance, Long id) {
        ((ParentBParentCodegen) instance).id = id;
      }
      public Long get(ParentBParent instance) {
        return ((ParentBParentCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentBParent, String> name = new Shim<ParentBParent, String>() {
      public void set(ParentBParent instance, String name) {
        ((ParentBParentCodegen) instance).name = name;
      }
      public String get(ParentBParent instance) {
        return ((ParentBParentCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentBParent, Long> version = new Shim<ParentBParent, Long>() {
      public void set(ParentBParent instance, Long version) {
        ((ParentBParentCodegen) instance).version = version;
      }
      public Long get(ParentBParent instance) {
        return ((ParentBParentCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ParentBChildBarsListDelegate implements ListProxy.Delegate<ParentBChildBar> {
    public void doAdd(ParentBChildBar e) {
      addParentBChildBar(e);
    }
    public void doRemove(ParentBChildBar e) {
      removeParentBChildBar(e);
    }
  }

  private class ParentBChildFoosListDelegate implements ListProxy.Delegate<ParentBChildFoo> {
    public void doAdd(ParentBChildFoo e) {
      addParentBChildFoo(e);
    }
    public void doRemove(ParentBChildFoo e) {
      removeParentBChildFoo(e);
    }
  }

  private class ParentBChildZazsListDelegate implements ListProxy.Delegate<ParentBChildZaz> {
    public void doAdd(ParentBChildZaz e) {
      addParentBChildZaz(e);
    }
    public void doRemove(ParentBChildZaz e) {
      removeParentBChildZaz(e);
    }
  }

  public static class ParentBParentChanged extends AbstractChanged {
    public ParentBParentChanged(ParentBParent instance) {
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
    public boolean hasParentBChildBars() {
      return this.contains("parentBChildBars");
    }
    public boolean hasParentBChildFoos() {
      return this.contains("parentBChildFoos");
    }
    public boolean hasParentBChildZazs() {
      return this.contains("parentBChildZazs");
    }
  }

}
