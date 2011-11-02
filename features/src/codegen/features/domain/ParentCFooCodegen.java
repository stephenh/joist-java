package features.domain;

import java.util.List;

import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;
import features.domain.queries.ParentCFooQueries;

@SuppressWarnings("all")
public abstract class ParentCFooCodegen extends AbstractDomainObject {

  public static final ParentCFooQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private ForeignKeyListHolder<ParentCFoo, ParentCBar> firstParentParentCBars = new ForeignKeyListHolder<ParentCFoo, ParentCBar>((ParentCFoo) this, Aliases.parentCBar(), Aliases.parentCBar().firstParent);
  private ForeignKeyListHolder<ParentCFoo, ParentCBar> secondParentParentCBars = new ForeignKeyListHolder<ParentCFoo, ParentCBar>((ParentCFoo) this, Aliases.parentCBar(), Aliases.parentCBar().secondParent);
  protected Changed changed;

  static {
    Aliases.parentCFoo();
    queries = new ParentCFooQueries();
  }

  protected ParentCFooCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentCFoo>(Shims.name));
    this.addRule(new MaxLength<ParentCFoo>(Shims.name, 100));
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

  public List<ParentCBar> getFirstParentParentCBars() {
    return this.firstParentParentCBars.get();
  }

  public void setFirstParentParentCBars(List<ParentCBar> firstParentParentCBars) {
    for (ParentCBar o : Copy.list(this.getFirstParentParentCBars())) {
      this.removeFirstParentParentCBar(o);
    }
    for (ParentCBar o : firstParentParentCBars) {
      this.addFirstParentParentCBar(o);
    }
  }

  public void addFirstParentParentCBar(ParentCBar o) {
    o.setFirstParentWithoutPercolation((ParentCFoo) this);
    this.addFirstParentParentCBarWithoutPercolation(o);
  }

  public void removeFirstParentParentCBar(ParentCBar o) {
    o.setFirstParentWithoutPercolation(null);
    this.removeFirstParentParentCBarWithoutPercolation(o);
  }

  protected void addFirstParentParentCBarWithoutPercolation(ParentCBar o) {
    this.getChanged().record("firstParentParentCBars");
    this.firstParentParentCBars.add(o);
  }

  protected void removeFirstParentParentCBarWithoutPercolation(ParentCBar o) {
    this.getChanged().record("firstParentParentCBars");
    this.firstParentParentCBars.remove(o);
  }

  public List<ParentCBar> getSecondParentParentCBars() {
    return this.secondParentParentCBars.get();
  }

  public void setSecondParentParentCBars(List<ParentCBar> secondParentParentCBars) {
    for (ParentCBar o : Copy.list(this.getSecondParentParentCBars())) {
      this.removeSecondParentParentCBar(o);
    }
    for (ParentCBar o : secondParentParentCBars) {
      this.addSecondParentParentCBar(o);
    }
  }

  public void addSecondParentParentCBar(ParentCBar o) {
    o.setSecondParentWithoutPercolation((ParentCFoo) this);
    this.addSecondParentParentCBarWithoutPercolation(o);
  }

  public void removeSecondParentParentCBar(ParentCBar o) {
    o.setSecondParentWithoutPercolation(null);
    this.removeSecondParentParentCBarWithoutPercolation(o);
  }

  protected void addSecondParentParentCBarWithoutPercolation(ParentCBar o) {
    this.getChanged().record("secondParentParentCBars");
    this.secondParentParentCBars.add(o);
  }

  protected void removeSecondParentParentCBarWithoutPercolation(ParentCBar o) {
    this.getChanged().record("secondParentParentCBars");
    this.secondParentParentCBars.remove(o);
  }

  public ParentCFooChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentCFooChanged((ParentCFoo) this);
    }
    return (ParentCFooChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ParentCBar o : Copy.list(this.getFirstParentParentCBars())) {
      o.setFirstParentWithoutPercolation(null);
    }
    for (ParentCBar o : Copy.list(this.getSecondParentParentCBars())) {
      o.setSecondParentWithoutPercolation(null);
    }
  }

  static class Shims {
    protected static final Shim<ParentCFoo, Long> id = new Shim<ParentCFoo, Long>() {
      public void set(ParentCFoo instance, Long id) {
        ((ParentCFooCodegen) instance).id = id;
      }
      public Long get(ParentCFoo instance) {
        return ((ParentCFooCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentCFoo, String> name = new Shim<ParentCFoo, String>() {
      public void set(ParentCFoo instance, String name) {
        ((ParentCFooCodegen) instance).name = name;
      }
      public String get(ParentCFoo instance) {
        return ((ParentCFooCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentCFoo, Long> version = new Shim<ParentCFoo, Long>() {
      public void set(ParentCFoo instance, Long version) {
        ((ParentCFooCodegen) instance).version = version;
      }
      public Long get(ParentCFoo instance) {
        return ((ParentCFooCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  public static class ParentCFooChanged extends AbstractChanged {
    public ParentCFooChanged(ParentCFoo instance) {
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
    public boolean hasFirstParentParentCBars() {
      return this.contains("firstParentParentCBars");
    }
    public boolean hasSecondParentParentCBars() {
      return this.contains("secondParentParentCBars");
    }
  }

}
