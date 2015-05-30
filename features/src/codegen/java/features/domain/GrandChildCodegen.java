package features.domain;

import features.domain.queries.GrandChildQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class GrandChildCodegen extends AbstractDomainObject {

  public static final GrandChildQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<GrandChild, Child> child = new ForeignKeyHolder<GrandChild, Child>(GrandChild.class, Child.class, Aliases.child(), Aliases.grandChild().child);
  protected Changed changed;

  static {
    Aliases.grandChild();
    queries = new GrandChildQueries();
  }

  protected GrandChildCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<GrandChild>(Shims.name));
    this.addRule(new MaxLength<GrandChild>(Shims.name, 100));
    this.addRule(new NotEmpty<GrandChild>(Shims.name));
    this.addRule(new NotNull<GrandChild>(Shims.childId));
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

  public Child getChild() {
    return this.child.get();
  }

  public void setChild(Child child) {
    if (child == this.getChild()) {
      return;
    }
    if (this.child.get() != null) {
      this.child.get().removeGrandChildWithoutPercolation((GrandChild) this);
    }
    this.setChildWithoutPercolation(child);
    if (this.child.get() != null) {
      this.child.get().addGrandChildWithoutPercolation((GrandChild) this);
    }
  }

  protected void setChildWithoutPercolation(Child child) {
    this.getChanged().record("child", this.child.get(), child);
    this.child.set(child);
  }

  public GrandChildChanged getChanged() {
    if (this.changed == null) {
      this.changed = new GrandChildChanged((GrandChild) this);
    }
    return (GrandChildChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setChild(null);
  }

  static class Shims {
    protected static final Shim<GrandChild, Long> id = new Shim<GrandChild, Long>() {
      public void set(GrandChild instance, Long id) {
        ((GrandChildCodegen) instance).id = id;
      }
      public Long get(GrandChild instance) {
        return ((GrandChildCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<GrandChild, String> name = new Shim<GrandChild, String>() {
      public void set(GrandChild instance, String name) {
        ((GrandChildCodegen) instance).name = name;
      }
      public String get(GrandChild instance) {
        return ((GrandChildCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<GrandChild, Long> version = new Shim<GrandChild, Long>() {
      public void set(GrandChild instance, Long version) {
        ((GrandChildCodegen) instance).version = version;
      }
      public Long get(GrandChild instance) {
        return ((GrandChildCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<GrandChild, Long> childId = new Shim<GrandChild, Long>() {
      public void set(GrandChild instance, Long childId) {
        ((GrandChildCodegen) instance).child.setId(childId);
      }
      public Long get(GrandChild instance) {
        return ((GrandChildCodegen) instance).child.getId();
      }
      public String getName() {
        return "child";
      }
    };
  }

  public static class GrandChildChanged extends AbstractChanged {
    public GrandChildChanged(GrandChild instance) {
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
    public boolean hasChild() {
      return this.contains("child");
    }
    public Child getOriginalChild() {
      return (Child) this.getOriginal("child");
    }
  }

}
