package features.domain;

import features.domain.queries.ChildIBQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ChildIBCodegen extends AbstractDomainObject {

  public static final ChildIBQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyHolder<ChildIB, ParentI> parent = new ForeignKeyHolder<ChildIB, ParentI>(ChildIB.class, ParentI.class, Aliases.parentI(), Aliases.childIB().parent);
  protected Changed changed;

  static {
    Aliases.childIB();
    queries = new ChildIBQueries();
  }

  protected ChildIBCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ChildIB>(Shims.parentId));
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

  public Long getVersion() {
    return this.version;
  }

  public ParentI getParent() {
    return this.parent.get();
  }

  public void setParent(ParentI parent) {
    if (parent == this.getParent()) {
      return;
    }
    if (this.parent.get() != null) {
      this.parent.get().removeChildBWithoutPercolation((ChildIB) this);
    }
    if (parent != null) {
      parent.setChildB(null);
    }
    this.setParentWithoutPercolation(parent);
    if (this.parent.get() != null) {
      this.parent.get().addChildBWithoutPercolation((ChildIB) this);
    }
  }

  protected void setParentWithoutPercolation(ParentI parent) {
    this.getChanged().record("parent", this.parent.get(), parent);
    this.parent.set(parent);
  }

  public ChildIBChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ChildIBChanged((ChildIB) this);
    }
    return (ChildIBChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParent(null);
  }

  static class Shims {
    protected static final Shim<ChildIB, Long> id = new Shim<ChildIB, Long>() {
      public void set(ChildIB instance, Long id) {
        ((ChildIBCodegen) instance).id = id;
      }
      public Long get(ChildIB instance) {
        return ((ChildIBCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ChildIB, Long> version = new Shim<ChildIB, Long>() {
      public void set(ChildIB instance, Long version) {
        ((ChildIBCodegen) instance).version = version;
      }
      public Long get(ChildIB instance) {
        return ((ChildIBCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ChildIB, Long> parentId = new Shim<ChildIB, Long>() {
      public void set(ChildIB instance, Long parentId) {
        ((ChildIBCodegen) instance).parent.setId(parentId);
      }
      public Long get(ChildIB instance) {
        return ((ChildIBCodegen) instance).parent.getId();
      }
      public String getName() {
        return "parent";
      }
    };
  }

  public static class ChildIBChanged extends AbstractChanged {
    public ChildIBChanged(ChildIB instance) {
      super(instance);
    }
    public boolean hasId() {
      return this.contains("id");
    }
    public Long getOriginalId() {
      return (Long) this.getOriginal("id");
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
    public ParentI getOriginalParent() {
      return (ParentI) this.getOriginal("parent");
    }
  }

}
