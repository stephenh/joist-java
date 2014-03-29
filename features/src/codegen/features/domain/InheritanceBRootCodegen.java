package features.domain;

import features.domain.queries.InheritanceBRootQueries;
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
public abstract class InheritanceBRootCodegen extends AbstractDomainObject {

  public static final InheritanceBRootQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<InheritanceBRoot, InheritanceBRootChild> inheritanceBRootChilds = new ForeignKeyListHolder<InheritanceBRoot, InheritanceBRootChild>((InheritanceBRoot) this, Aliases.inheritanceBRootChild(), Aliases.inheritanceBRootChild().inheritanceBRoot, new InheritanceBRootChildsListDelegate());
  protected Changed changed;

  static {
    Aliases.inheritanceBRoot();
    queries = new InheritanceBRootQueries();
  }

  protected InheritanceBRootCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceBRoot>(Shims.name));
    this.addRule(new MaxLength<InheritanceBRoot>(Shims.name, 100));
    this.addRule(new NotEmpty<InheritanceBRoot>(Shims.name));
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

  public List<InheritanceBRootChild> getInheritanceBRootChilds() {
    return this.inheritanceBRootChilds.get();
  }

  public void setInheritanceBRootChilds(List<InheritanceBRootChild> inheritanceBRootChilds) {
    ListDiff<InheritanceBRootChild> diff = ListDiff.of(this.getInheritanceBRootChilds(), inheritanceBRootChilds);
    for (InheritanceBRootChild o : diff.removed) {
      this.removeInheritanceBRootChild(o);
    }
    for (InheritanceBRootChild o : diff.added) {
      this.addInheritanceBRootChild(o);
    }
    this.inheritanceBRootChilds.set(inheritanceBRootChilds);
  }

  public void addInheritanceBRootChild(InheritanceBRootChild o) {
    if (o.getInheritanceBRoot() == this) {
      return;
    }
    o.setInheritanceBRootWithoutPercolation((InheritanceBRoot) this);
    this.addInheritanceBRootChildWithoutPercolation(o);
  }

  public void removeInheritanceBRootChild(InheritanceBRootChild o) {
    if (o.getInheritanceBRoot() != this) {
      return;
    }
    o.setInheritanceBRootWithoutPercolation(null);
    this.removeInheritanceBRootChildWithoutPercolation(o);
  }

  protected void addInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
    this.getChanged().record("inheritanceBRootChilds");
    this.inheritanceBRootChilds.add(o);
  }

  protected void removeInheritanceBRootChildWithoutPercolation(InheritanceBRootChild o) {
    this.getChanged().record("inheritanceBRootChilds");
    this.inheritanceBRootChilds.remove(o);
  }

  public InheritanceBRootChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceBRootChanged((InheritanceBRoot) this);
    }
    return (InheritanceBRootChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (InheritanceBRootChild o : Copy.list(this.getInheritanceBRootChilds())) {
      removeInheritanceBRootChild(o);
    }
  }

  static class Shims {
    protected static final Shim<InheritanceBRoot, Long> id = new Shim<InheritanceBRoot, Long>() {
      public void set(InheritanceBRoot instance, Long id) {
        ((InheritanceBRootCodegen) instance).id = id;
      }
      public Long get(InheritanceBRoot instance) {
        return ((InheritanceBRootCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<InheritanceBRoot, String> name = new Shim<InheritanceBRoot, String>() {
      public void set(InheritanceBRoot instance, String name) {
        ((InheritanceBRootCodegen) instance).name = name;
      }
      public String get(InheritanceBRoot instance) {
        return ((InheritanceBRootCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<InheritanceBRoot, Long> version = new Shim<InheritanceBRoot, Long>() {
      public void set(InheritanceBRoot instance, Long version) {
        ((InheritanceBRootCodegen) instance).version = version;
      }
      public Long get(InheritanceBRoot instance) {
        return ((InheritanceBRootCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class InheritanceBRootChildsListDelegate implements ListProxy.Delegate<InheritanceBRootChild> {
    public void doAdd(InheritanceBRootChild e) {
      addInheritanceBRootChild(e);
    }
    public void doRemove(InheritanceBRootChild e) {
      removeInheritanceBRootChild(e);
    }
  }

  public static class InheritanceBRootChanged extends AbstractChanged {
    public InheritanceBRootChanged(InheritanceBRoot instance) {
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
    public boolean hasInheritanceBRootChilds() {
      return this.contains("inheritanceBRootChilds");
    }
  }

}
