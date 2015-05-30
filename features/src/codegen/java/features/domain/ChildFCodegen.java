package features.domain;

import features.domain.queries.ChildFQueries;
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
public abstract class ChildFCodegen extends AbstractDomainObject {

  public static final ChildFQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ChildF, ParentF> childOneParentFs = new ForeignKeyListHolder<ChildF, ParentF>((ChildF) this, Aliases.parentF(), Aliases.parentF().childOne, new ChildOneParentFsListDelegate());
  private final ForeignKeyListHolder<ChildF, ParentF> childTwoParentFs = new ForeignKeyListHolder<ChildF, ParentF>((ChildF) this, Aliases.parentF(), Aliases.parentF().childTwo, new ChildTwoParentFsListDelegate());
  protected Changed changed;

  static {
    Aliases.childF();
    queries = new ChildFQueries();
  }

  protected ChildFCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ChildF>(Shims.name));
    this.addRule(new MaxLength<ChildF>(Shims.name, 100));
    this.addRule(new NotEmpty<ChildF>(Shims.name));
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

  public List<ParentF> getChildOneParentFs() {
    return this.childOneParentFs.get();
  }

  public void setChildOneParentFs(List<ParentF> childOneParentFs) {
    ListDiff<ParentF> diff = ListDiff.of(this.getChildOneParentFs(), childOneParentFs);
    for (ParentF o : diff.removed) {
      this.removeChildOneParentF(o);
    }
    for (ParentF o : diff.added) {
      this.addChildOneParentF(o);
    }
    this.childOneParentFs.set(childOneParentFs);
  }

  public void addChildOneParentF(ParentF o) {
    if (o.getChildOne() == this) {
      return;
    }
    o.setChildOneWithoutPercolation((ChildF) this);
    this.addChildOneParentFWithoutPercolation(o);
  }

  public void removeChildOneParentF(ParentF o) {
    if (o.getChildOne() != this) {
      return;
    }
    o.setChildOneWithoutPercolation(null);
    this.removeChildOneParentFWithoutPercolation(o);
  }

  protected void addChildOneParentFWithoutPercolation(ParentF o) {
    this.getChanged().record("childOneParentFs");
    this.childOneParentFs.add(o);
  }

  protected void removeChildOneParentFWithoutPercolation(ParentF o) {
    this.getChanged().record("childOneParentFs");
    this.childOneParentFs.remove(o);
  }

  public List<ParentF> getChildTwoParentFs() {
    return this.childTwoParentFs.get();
  }

  public void setChildTwoParentFs(List<ParentF> childTwoParentFs) {
    ListDiff<ParentF> diff = ListDiff.of(this.getChildTwoParentFs(), childTwoParentFs);
    for (ParentF o : diff.removed) {
      this.removeChildTwoParentF(o);
    }
    for (ParentF o : diff.added) {
      this.addChildTwoParentF(o);
    }
    this.childTwoParentFs.set(childTwoParentFs);
  }

  public void addChildTwoParentF(ParentF o) {
    if (o.getChildTwo() == this) {
      return;
    }
    o.setChildTwoWithoutPercolation((ChildF) this);
    this.addChildTwoParentFWithoutPercolation(o);
  }

  public void removeChildTwoParentF(ParentF o) {
    if (o.getChildTwo() != this) {
      return;
    }
    o.setChildTwoWithoutPercolation(null);
    this.removeChildTwoParentFWithoutPercolation(o);
  }

  protected void addChildTwoParentFWithoutPercolation(ParentF o) {
    this.getChanged().record("childTwoParentFs");
    this.childTwoParentFs.add(o);
  }

  protected void removeChildTwoParentFWithoutPercolation(ParentF o) {
    this.getChanged().record("childTwoParentFs");
    this.childTwoParentFs.remove(o);
  }

  public ChildFChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ChildFChanged((ChildF) this);
    }
    return (ChildFChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ParentF o : Copy.list(this.getChildOneParentFs())) {
      removeChildOneParentF(o);
    }
    for (ParentF o : Copy.list(this.getChildTwoParentFs())) {
      removeChildTwoParentF(o);
    }
  }

  static class Shims {
    protected static final Shim<ChildF, Long> id = new Shim<ChildF, Long>() {
      public void set(ChildF instance, Long id) {
        ((ChildFCodegen) instance).id = id;
      }
      public Long get(ChildF instance) {
        return ((ChildFCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ChildF, String> name = new Shim<ChildF, String>() {
      public void set(ChildF instance, String name) {
        ((ChildFCodegen) instance).name = name;
      }
      public String get(ChildF instance) {
        return ((ChildFCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ChildF, Long> version = new Shim<ChildF, Long>() {
      public void set(ChildF instance, Long version) {
        ((ChildFCodegen) instance).version = version;
      }
      public Long get(ChildF instance) {
        return ((ChildFCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ChildOneParentFsListDelegate implements ListProxy.Delegate<ParentF> {
    public void doAdd(ParentF e) {
      addChildOneParentF(e);
    }
    public void doRemove(ParentF e) {
      removeChildOneParentF(e);
    }
  }

  private class ChildTwoParentFsListDelegate implements ListProxy.Delegate<ParentF> {
    public void doAdd(ParentF e) {
      addChildTwoParentF(e);
    }
    public void doRemove(ParentF e) {
      removeChildTwoParentF(e);
    }
  }

  public static class ChildFChanged extends AbstractChanged {
    public ChildFChanged(ChildF instance) {
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
    public boolean hasChildOneParentFs() {
      return this.contains("childOneParentFs");
    }
    public boolean hasChildTwoParentFs() {
      return this.contains("childTwoParentFs");
    }
  }

}
