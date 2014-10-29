package features.domain;

import features.domain.queries.ChildIAQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ChildIACodegen extends AbstractDomainObject {

  public static final ChildIAQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyHolder<ChildIA, ParentI> parent = new ForeignKeyHolder<ChildIA, ParentI>(ChildIA.class, ParentI.class, Aliases.parentI(), Aliases.childIA().parent);
  protected Changed changed;

  static {
    Aliases.childIA();
    queries = new ChildIAQueries();
  }

  protected ChildIACodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ChildIA>(Shims.parentId));
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
      this.parent.get().removeChildAWithoutPercolation((ChildIA) this);
    }
    this.setParentWithoutPercolation(parent);
    if (this.parent.get() != null) {
      this.parent.get().addChildAWithoutPercolation((ChildIA) this);
    }
  }

  protected void setParentWithoutPercolation(ParentI parent) {
    this.getChanged().record("parent", this.parent.get(), parent);
    this.parent.set(parent);
  }

  public ChildIAChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ChildIAChanged((ChildIA) this);
    }
    return (ChildIAChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParent(null);
  }

  static class Shims {
    protected static final Shim<ChildIA, Long> id = new Shim<ChildIA, Long>() {
      public void set(ChildIA instance, Long id) {
        ((ChildIACodegen) instance).id = id;
      }
      public Long get(ChildIA instance) {
        return ((ChildIACodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ChildIA, Long> version = new Shim<ChildIA, Long>() {
      public void set(ChildIA instance, Long version) {
        ((ChildIACodegen) instance).version = version;
      }
      public Long get(ChildIA instance) {
        return ((ChildIACodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ChildIA, Long> parentId = new Shim<ChildIA, Long>() {
      public void set(ChildIA instance, Long parentId) {
        ((ChildIACodegen) instance).parent.setId(parentId);
      }
      public Long get(ChildIA instance) {
        return ((ChildIACodegen) instance).parent.getId();
      }
      public String getName() {
        return "parent";
      }
    };
  }

  public static class ChildIAChanged extends AbstractChanged {
    public ChildIAChanged(ChildIA instance) {
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
