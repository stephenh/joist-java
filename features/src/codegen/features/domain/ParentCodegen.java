package features.domain;

import features.domain.queries.ParentQueries;
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
public abstract class ParentCodegen extends AbstractDomainObject {

  public static final ParentQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<Parent, Child> childs = new ForeignKeyListHolder<Parent, Child>((Parent) this, Aliases.child(), Aliases.child().parent, new ChildsListDelegate());
  protected Changed changed;

  static {
    Aliases.parent();
    queries = new ParentQueries();
  }

  protected ParentCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<Parent>(Shims.name));
    this.addRule(new MaxLength<Parent>(Shims.name, 100));
    this.addRule(new NotEmpty<Parent>(Shims.name));
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

  public List<Child> getChilds() {
    return this.childs.get();
  }

  public void setChilds(List<Child> childs) {
    ListDiff<Child> diff = ListDiff.of(this.getChilds(), childs);
    for (Child o : diff.removed) {
      this.removeChild(o);
    }
    for (Child o : diff.added) {
      this.addChild(o);
    }
    this.childs.set(childs);
  }

  public void addChild(Child o) {
    if (o.getParent() == this) {
      return;
    }
    o.setParentWithoutPercolation((Parent) this);
    this.addChildWithoutPercolation(o);
  }

  public void removeChild(Child o) {
    if (o.getParent() != this) {
      return;
    }
    o.setParentWithoutPercolation(null);
    this.removeChildWithoutPercolation(o);
    if (UoW.isOpen() && UoW.isImplicitDeletionOfChildrenEnabled()) {
      Child.queries.delete(o);
    }
  }

  protected void addChildWithoutPercolation(Child o) {
    this.getChanged().record("childs");
    this.childs.add(o);
  }

  protected void removeChildWithoutPercolation(Child o) {
    this.getChanged().record("childs");
    this.childs.remove(o);
  }

  public ParentChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentChanged((Parent) this);
    }
    return (ParentChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (Child o : Copy.list(this.getChilds())) {
      removeChild(o);
    }
  }

  static class Shims {
    protected static final Shim<Parent, Long> id = new Shim<Parent, Long>() {
      public void set(Parent instance, Long id) {
        ((ParentCodegen) instance).id = id;
      }
      public Long get(Parent instance) {
        return ((ParentCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<Parent, String> name = new Shim<Parent, String>() {
      public void set(Parent instance, String name) {
        ((ParentCodegen) instance).name = name;
      }
      public String get(Parent instance) {
        return ((ParentCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<Parent, Long> version = new Shim<Parent, Long>() {
      public void set(Parent instance, Long version) {
        ((ParentCodegen) instance).version = version;
      }
      public Long get(Parent instance) {
        return ((ParentCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ChildsListDelegate implements ListProxy.Delegate<Child> {
    public void doAdd(Child e) {
      addChild(e);
    }
    public void doRemove(Child e) {
      removeChild(e);
    }
  }

  public static class ParentChanged extends AbstractChanged {
    public ParentChanged(Parent instance) {
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
    public boolean hasChilds() {
      return this.contains("childs");
    }
  }

}
