package features.domain;

import features.domain.queries.InheritanceAOwnerQueries;
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
public abstract class InheritanceAOwnerCodegen extends AbstractDomainObject {

  public static final InheritanceAOwnerQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<InheritanceAOwner, InheritanceABase> inheritanceABases = new ForeignKeyListHolder<InheritanceAOwner, InheritanceABase>((InheritanceAOwner) this, Aliases.inheritanceABase(), Aliases.inheritanceABase().inheritanceAOwner, new InheritanceABasesListDelegate());
  protected Changed changed;

  static {
    Aliases.inheritanceAOwner();
    queries = new InheritanceAOwnerQueries();
  }

  protected InheritanceAOwnerCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceAOwner>(Shims.name));
    this.addRule(new MaxLength<InheritanceAOwner>(Shims.name, 100));
    this.addRule(new NotEmpty<InheritanceAOwner>(Shims.name));
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

  public List<InheritanceABase> getInheritanceABases() {
    return this.inheritanceABases.get();
  }

  public void setInheritanceABases(List<InheritanceABase> inheritanceABases) {
    ListDiff<InheritanceABase> diff = ListDiff.of(this.getInheritanceABases(), inheritanceABases);
    for (InheritanceABase o : diff.removed) {
      this.removeInheritanceABase(o);
    }
    for (InheritanceABase o : diff.added) {
      this.addInheritanceABase(o);
    }
    this.inheritanceABases.set(inheritanceABases);
  }

  public void addInheritanceABase(InheritanceABase o) {
    if (o.getInheritanceAOwner() == this) {
      return;
    }
    o.setInheritanceAOwnerWithoutPercolation((InheritanceAOwner) this);
    this.addInheritanceABaseWithoutPercolation(o);
  }

  public void removeInheritanceABase(InheritanceABase o) {
    if (o.getInheritanceAOwner() != this) {
      return;
    }
    o.setInheritanceAOwnerWithoutPercolation(null);
    this.removeInheritanceABaseWithoutPercolation(o);
  }

  protected void addInheritanceABaseWithoutPercolation(InheritanceABase o) {
    this.getChanged().record("inheritanceABases");
    this.inheritanceABases.add(o);
  }

  protected void removeInheritanceABaseWithoutPercolation(InheritanceABase o) {
    this.getChanged().record("inheritanceABases");
    this.inheritanceABases.remove(o);
  }

  public InheritanceAOwnerChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceAOwnerChanged((InheritanceAOwner) this);
    }
    return (InheritanceAOwnerChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (InheritanceABase o : Copy.list(this.getInheritanceABases())) {
      removeInheritanceABase(o);
    }
  }

  static class Shims {
    protected static final Shim<InheritanceAOwner, Long> id = new Shim<InheritanceAOwner, Long>() {
      public void set(InheritanceAOwner instance, Long id) {
        ((InheritanceAOwnerCodegen) instance).id = id;
      }
      public Long get(InheritanceAOwner instance) {
        return ((InheritanceAOwnerCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<InheritanceAOwner, String> name = new Shim<InheritanceAOwner, String>() {
      public void set(InheritanceAOwner instance, String name) {
        ((InheritanceAOwnerCodegen) instance).name = name;
      }
      public String get(InheritanceAOwner instance) {
        return ((InheritanceAOwnerCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<InheritanceAOwner, Long> version = new Shim<InheritanceAOwner, Long>() {
      public void set(InheritanceAOwner instance, Long version) {
        ((InheritanceAOwnerCodegen) instance).version = version;
      }
      public Long get(InheritanceAOwner instance) {
        return ((InheritanceAOwnerCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class InheritanceABasesListDelegate implements ListProxy.Delegate<InheritanceABase> {
    public void doAdd(InheritanceABase e) {
      addInheritanceABase(e);
    }
    public void doRemove(InheritanceABase e) {
      removeInheritanceABase(e);
    }
  }

  public static class InheritanceAOwnerChanged extends AbstractChanged {
    public InheritanceAOwnerChanged(InheritanceAOwner instance) {
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
    public boolean hasInheritanceABases() {
      return this.contains("inheritanceABases");
    }
  }

}
