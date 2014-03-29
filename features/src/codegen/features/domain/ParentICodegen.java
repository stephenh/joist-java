package features.domain;

import features.domain.queries.ParentIQueries;
import java.util.List;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.util.ListProxy;
import joist.util.Copy;
import joist.util.ListDiff;

@SuppressWarnings("all")
public abstract class ParentICodegen extends AbstractDomainObject {

  public static final ParentIQueries queries;
  private Long id = null;
  private Long version = null;
  private final ForeignKeyListHolder<ParentI, ChildIA> childAs = new ForeignKeyListHolder<ParentI, ChildIA>((ParentI) this, Aliases.childIA(), Aliases.childIA().parent, new ChildAsListDelegate());
  private final ForeignKeyListHolder<ParentI, ChildIB> childBs = new ForeignKeyListHolder<ParentI, ChildIB>((ParentI) this, Aliases.childIB(), Aliases.childIB().parent, new ChildBsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentI();
    queries = new ParentIQueries();
  }

  protected ParentICodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
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

  public List<ChildIA> getChildAs() {
    return this.childAs.get();
  }

  public void setChildAs(List<ChildIA> childAs) {
    ListDiff<ChildIA> diff = ListDiff.of(this.getChildAs(), childAs);
    for (ChildIA o : diff.removed) {
      this.removeChildA(o);
    }
    for (ChildIA o : diff.added) {
      this.addChildA(o);
    }
    this.childAs.set(childAs);
  }

  public void addChildA(ChildIA o) {
    if (o.getParent() == this) {
      return;
    }
    o.setParentWithoutPercolation((ParentI) this);
    this.addChildAWithoutPercolation(o);
  }

  public void removeChildA(ChildIA o) {
    if (o.getParent() != this) {
      return;
    }
    o.setParentWithoutPercolation(null);
    this.removeChildAWithoutPercolation(o);
    if (UoW.isOpen() && UoW.isImplicitDeletionOfChildrenEnabled()) {
      ChildIA.queries.delete(o);
    }
  }

  protected void addChildAWithoutPercolation(ChildIA o) {
    this.getChanged().record("childAs");
    this.childAs.add(o);
  }

  protected void removeChildAWithoutPercolation(ChildIA o) {
    this.getChanged().record("childAs");
    this.childAs.remove(o);
  }

  public ChildIB getChildB() {
    return (this.childBs.get().size() == 0) ? null : this.childBs.get().get(0);
  }

  public void setChildB(ChildIB n) {
    ChildIB o = this.getChildB();
    if (o == n) {
      return;
    }
    if (o != null) {
      o.setParentWithoutPercolation(null);
      this.removeChildBWithoutPercolation(o);
    }
    if (n != null) {
      n.setParentWithoutPercolation((ParentI) this);
      this.addChildBWithoutPercolation(n);
    }
  }

  protected void addChildBWithoutPercolation(ChildIB o) {
    this.getChanged().record("childBs");
    this.childBs.add(o);
  }

  protected void removeChildBWithoutPercolation(ChildIB o) {
    this.getChanged().record("childBs");
    this.childBs.remove(o);
  }

  public ParentIChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentIChanged((ParentI) this);
    }
    return (ParentIChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ChildIA o : Copy.list(this.getChildAs())) {
      removeChildA(o);
    }
    this.setChildB(null);
  }

  static class Shims {
    protected static final Shim<ParentI, Long> id = new Shim<ParentI, Long>() {
      public void set(ParentI instance, Long id) {
        ((ParentICodegen) instance).id = id;
      }
      public Long get(ParentI instance) {
        return ((ParentICodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentI, Long> version = new Shim<ParentI, Long>() {
      public void set(ParentI instance, Long version) {
        ((ParentICodegen) instance).version = version;
      }
      public Long get(ParentI instance) {
        return ((ParentICodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ChildAsListDelegate implements ListProxy.Delegate<ChildIA> {
    public void doAdd(ChildIA e) {
      addChildA(e);
    }
    public void doRemove(ChildIA e) {
      removeChildA(e);
    }
  }

  private class ChildBsListDelegate implements ListProxy.Delegate<ChildIB> {
    public void doAdd(ChildIB e) {
      throw new UnsupportedOperationException("Not implemented");
    }
    public void doRemove(ChildIB e) {
      throw new UnsupportedOperationException("Not implemented");
    }
  }

  public static class ParentIChanged extends AbstractChanged {
    public ParentIChanged(ParentI instance) {
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
    public boolean hasChildAs() {
      return this.contains("childAs");
    }
    public boolean hasChildBs() {
      return this.contains("childBs");
    }
  }

}
