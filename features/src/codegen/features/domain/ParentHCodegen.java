package features.domain;

import features.domain.queries.ParentHQueries;
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
public abstract class ParentHCodegen extends AbstractDomainObject {

  public static final ParentHQueries queries;
  private Long id = null;
  private String name = null;
  private Long threshold = null;
  private Long version = null;
  private final ForeignKeyListHolder<ParentH, ChildH> parentChildHs = new ForeignKeyListHolder<ParentH, ChildH>((ParentH) this, Aliases.childH(), Aliases.childH().parent, new ParentChildHsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentH();
    queries = new ParentHQueries();
  }

  protected ParentHCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentH>(Shims.name));
    this.addRule(new MaxLength<ParentH>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentH>(Shims.name));
    this.addRule(new NotNull<ParentH>(Shims.threshold));
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

  public Long getThreshold() {
    return this.threshold;
  }

  public void setThreshold(Long threshold) {
    this.getChanged().record("threshold", this.threshold, threshold);
    this.threshold = threshold;
  }

  protected void defaultThreshold(Long threshold) {
    this.threshold = threshold;
  }

  public Long getVersion() {
    return this.version;
  }

  public List<ChildH> getParentChildHs() {
    return this.parentChildHs.get();
  }

  public void setParentChildHs(List<ChildH> parentChildHs) {
    ListDiff<ChildH> diff = ListDiff.of(this.getParentChildHs(), parentChildHs);
    for (ChildH o : diff.removed) {
      this.removeParentChildH(o);
    }
    for (ChildH o : diff.added) {
      this.addParentChildH(o);
    }
  }

  public void addParentChildH(ChildH o) {
    if (o.getParent() == this) {
      return;
    }
    o.setParentWithoutPercolation((ParentH) this);
    this.addParentChildHWithoutPercolation(o);
  }

  public void removeParentChildH(ChildH o) {
    if (o.getParent() != this) {
      return;
    }
    o.setParentWithoutPercolation(null);
    this.removeParentChildHWithoutPercolation(o);
    if (UoW.isOpen() && UoW.isImplicitDeletionOfChildrenEnabled()) {
      ChildH.queries.delete(o);
    }
  }

  protected void addParentChildHWithoutPercolation(ChildH o) {
    this.getChanged().record("parentChildHs");
    this.parentChildHs.add(o);
  }

  protected void removeParentChildHWithoutPercolation(ChildH o) {
    this.getChanged().record("parentChildHs");
    this.parentChildHs.remove(o);
  }

  public ParentHChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentHChanged((ParentH) this);
    }
    return (ParentHChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ChildH o : Copy.list(this.getParentChildHs())) {
      removeParentChildH(o);
    }
  }

  static class Shims {
    protected static final Shim<ParentH, Long> id = new Shim<ParentH, Long>() {
      public void set(ParentH instance, Long id) {
        ((ParentHCodegen) instance).id = id;
      }
      public Long get(ParentH instance) {
        return ((ParentHCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentH, String> name = new Shim<ParentH, String>() {
      public void set(ParentH instance, String name) {
        ((ParentHCodegen) instance).name = name;
      }
      public String get(ParentH instance) {
        return ((ParentHCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentH, Long> threshold = new Shim<ParentH, Long>() {
      public void set(ParentH instance, Long threshold) {
        ((ParentHCodegen) instance).threshold = threshold;
      }
      public Long get(ParentH instance) {
        return ((ParentHCodegen) instance).threshold;
      }
      public String getName() {
        return "threshold";
      }
    };
    protected static final Shim<ParentH, Long> version = new Shim<ParentH, Long>() {
      public void set(ParentH instance, Long version) {
        ((ParentHCodegen) instance).version = version;
      }
      public Long get(ParentH instance) {
        return ((ParentHCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ParentChildHsListDelegate implements ListProxy.Delegate<ChildH> {
    public void doAdd(ChildH e) {
      addParentChildH(e);
    }
    public void doRemove(ChildH e) {
      removeParentChildH(e);
    }
  }

  public static class ParentHChanged extends AbstractChanged {
    public ParentHChanged(ParentH instance) {
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
    public boolean hasThreshold() {
      return this.contains("threshold");
    }
    public Long getOriginalThreshold() {
      return (java.lang.Long) this.getOriginal("threshold");
    }
    public boolean hasVersion() {
      return this.contains("version");
    }
    public Long getOriginalVersion() {
      return (Long) this.getOriginal("version");
    }
    public boolean hasParentChildHs() {
      return this.contains("parentChildHs");
    }
  }

}
