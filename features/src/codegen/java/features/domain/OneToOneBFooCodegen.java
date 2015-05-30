package features.domain;

import features.domain.queries.OneToOneBFooQueries;
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
public abstract class OneToOneBFooCodegen extends AbstractDomainObject {

  public static final OneToOneBFooQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<OneToOneBFoo, OneToOneBBar> oneToOneBBars = new ForeignKeyListHolder<OneToOneBFoo, OneToOneBBar>((OneToOneBFoo) this, Aliases.oneToOneBBar(), Aliases.oneToOneBBar().oneToOneBFoo, new OneToOneBBarsListDelegate());
  protected Changed changed;

  static {
    Aliases.oneToOneBFoo();
    queries = new OneToOneBFooQueries();
  }

  protected OneToOneBFooCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<OneToOneBFoo>(Shims.name));
    this.addRule(new MaxLength<OneToOneBFoo>(Shims.name, 100));
    this.addRule(new NotEmpty<OneToOneBFoo>(Shims.name));
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

  public List<OneToOneBBar> getOneToOneBBars() {
    return this.oneToOneBBars.get();
  }

  public void setOneToOneBBars(List<OneToOneBBar> oneToOneBBars) {
    ListDiff<OneToOneBBar> diff = ListDiff.of(this.getOneToOneBBars(), oneToOneBBars);
    for (OneToOneBBar o : diff.removed) {
      this.removeOneToOneBBar(o);
    }
    for (OneToOneBBar o : diff.added) {
      this.addOneToOneBBar(o);
    }
    this.oneToOneBBars.set(oneToOneBBars);
  }

  public void addOneToOneBBar(OneToOneBBar o) {
    if (o.getOneToOneBFoo() == this) {
      return;
    }
    o.setOneToOneBFooWithoutPercolation((OneToOneBFoo) this);
    this.addOneToOneBBarWithoutPercolation(o);
  }

  public void removeOneToOneBBar(OneToOneBBar o) {
    if (o.getOneToOneBFoo() != this) {
      return;
    }
    o.setOneToOneBFooWithoutPercolation(null);
    this.removeOneToOneBBarWithoutPercolation(o);
  }

  protected void addOneToOneBBarWithoutPercolation(OneToOneBBar o) {
    this.getChanged().record("oneToOneBBars");
    this.oneToOneBBars.add(o);
  }

  protected void removeOneToOneBBarWithoutPercolation(OneToOneBBar o) {
    this.getChanged().record("oneToOneBBars");
    this.oneToOneBBars.remove(o);
  }

  public OneToOneBFooChanged getChanged() {
    if (this.changed == null) {
      this.changed = new OneToOneBFooChanged((OneToOneBFoo) this);
    }
    return (OneToOneBFooChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (OneToOneBBar o : Copy.list(this.getOneToOneBBars())) {
      removeOneToOneBBar(o);
    }
  }

  static class Shims {
    protected static final Shim<OneToOneBFoo, Long> id = new Shim<OneToOneBFoo, Long>() {
      public void set(OneToOneBFoo instance, Long id) {
        ((OneToOneBFooCodegen) instance).id = id;
      }
      public Long get(OneToOneBFoo instance) {
        return ((OneToOneBFooCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<OneToOneBFoo, String> name = new Shim<OneToOneBFoo, String>() {
      public void set(OneToOneBFoo instance, String name) {
        ((OneToOneBFooCodegen) instance).name = name;
      }
      public String get(OneToOneBFoo instance) {
        return ((OneToOneBFooCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<OneToOneBFoo, Long> version = new Shim<OneToOneBFoo, Long>() {
      public void set(OneToOneBFoo instance, Long version) {
        ((OneToOneBFooCodegen) instance).version = version;
      }
      public Long get(OneToOneBFoo instance) {
        return ((OneToOneBFooCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class OneToOneBBarsListDelegate implements ListProxy.Delegate<OneToOneBBar> {
    public void doAdd(OneToOneBBar e) {
      addOneToOneBBar(e);
    }
    public void doRemove(OneToOneBBar e) {
      removeOneToOneBBar(e);
    }
  }

  public static class OneToOneBFooChanged extends AbstractChanged {
    public OneToOneBFooChanged(OneToOneBFoo instance) {
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
    public boolean hasOneToOneBBars() {
      return this.contains("oneToOneBBars");
    }
  }

}
