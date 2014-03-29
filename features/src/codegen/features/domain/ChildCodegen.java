package features.domain;

import features.domain.queries.ChildQueries;
import java.util.List;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.util.ListProxy;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;
import joist.util.ListDiff;

@SuppressWarnings("all")
public abstract class ChildCodegen extends AbstractDomainObject {

  public static final ChildQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<Child, Parent> parent = new ForeignKeyHolder<Child, Parent>(Child.class, Parent.class, Aliases.parent(), Aliases.child().parent);
  private final ForeignKeyListHolder<Child, GrandChild> grandChilds = new ForeignKeyListHolder<Child, GrandChild>((Child) this, Aliases.grandChild(), Aliases.grandChild().child, new GrandChildsListDelegate());
  protected Changed changed;

  static {
    Aliases.child();
    queries = new ChildQueries();
  }

  protected ChildCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<Child>(Shims.name));
    this.addRule(new MaxLength<Child>(Shims.name, 100));
    this.addRule(new NotEmpty<Child>(Shims.name));
    this.addRule(new NotNull<Child>(Shims.parentId));
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

  public Parent getParent() {
    return this.parent.get();
  }

  public void setParent(Parent parent) {
    if (parent == this.getParent()) {
      return;
    }
    if (this.parent.get() != null) {
      this.parent.get().removeChildWithoutPercolation((Child) this);
    }
    this.setParentWithoutPercolation(parent);
    if (this.parent.get() != null) {
      this.parent.get().addChildWithoutPercolation((Child) this);
    }
  }

  protected void setParentWithoutPercolation(Parent parent) {
    this.getChanged().record("parent", this.parent.get(), parent);
    this.parent.set(parent);
  }

  public List<GrandChild> getGrandChilds() {
    return this.grandChilds.get();
  }

  public void setGrandChilds(List<GrandChild> grandChilds) {
    ListDiff<GrandChild> diff = ListDiff.of(this.getGrandChilds(), grandChilds);
    for (GrandChild o : diff.removed) {
      this.removeGrandChild(o);
    }
    for (GrandChild o : diff.added) {
      this.addGrandChild(o);
    }
    this.grandChilds.set(grandChilds);
  }

  public void addGrandChild(GrandChild o) {
    if (o.getChild() == this) {
      return;
    }
    o.setChildWithoutPercolation((Child) this);
    this.addGrandChildWithoutPercolation(o);
  }

  public void removeGrandChild(GrandChild o) {
    if (o.getChild() != this) {
      return;
    }
    o.setChildWithoutPercolation(null);
    this.removeGrandChildWithoutPercolation(o);
  }

  protected void addGrandChildWithoutPercolation(GrandChild o) {
    this.getChanged().record("grandChilds");
    this.grandChilds.add(o);
  }

  protected void removeGrandChildWithoutPercolation(GrandChild o) {
    this.getChanged().record("grandChilds");
    this.grandChilds.remove(o);
  }

  public ChildChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ChildChanged((Child) this);
    }
    return (ChildChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParent(null);
    for (GrandChild o : Copy.list(this.getGrandChilds())) {
      removeGrandChild(o);
    }
  }

  static class Shims {
    protected static final Shim<Child, Long> id = new Shim<Child, Long>() {
      public void set(Child instance, Long id) {
        ((ChildCodegen) instance).id = id;
      }
      public Long get(Child instance) {
        return ((ChildCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<Child, String> name = new Shim<Child, String>() {
      public void set(Child instance, String name) {
        ((ChildCodegen) instance).name = name;
      }
      public String get(Child instance) {
        return ((ChildCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<Child, Long> version = new Shim<Child, Long>() {
      public void set(Child instance, Long version) {
        ((ChildCodegen) instance).version = version;
      }
      public Long get(Child instance) {
        return ((ChildCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<Child, Long> parentId = new Shim<Child, Long>() {
      public void set(Child instance, Long parentId) {
        ((ChildCodegen) instance).parent.setId(parentId);
      }
      public Long get(Child instance) {
        return ((ChildCodegen) instance).parent.getId();
      }
      public String getName() {
        return "parent";
      }
    };
  }

  private class GrandChildsListDelegate implements ListProxy.Delegate<GrandChild> {
    public void doAdd(GrandChild e) {
      addGrandChild(e);
    }
    public void doRemove(GrandChild e) {
      removeGrandChild(e);
    }
  }

  public static class ChildChanged extends AbstractChanged {
    public ChildChanged(Child instance) {
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
    public boolean hasParent() {
      return this.contains("parent");
    }
    public Parent getOriginalParent() {
      return (Parent) this.getOriginal("parent");
    }
    public boolean hasGrandChilds() {
      return this.contains("grandChilds");
    }
  }

}
