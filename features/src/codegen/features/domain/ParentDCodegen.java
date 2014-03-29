package features.domain;

import features.domain.queries.ParentDQueries;
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
public abstract class ParentDCodegen extends AbstractDomainObject {

  public static final ParentDQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ParentD, ParentDChildB> parentDChildBs = new ForeignKeyListHolder<ParentD, ParentDChildB>((ParentD) this, Aliases.parentDChildB(), Aliases.parentDChildB().parentD, new ParentDChildBsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentD();
    queries = new ParentDQueries();
  }

  protected ParentDCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentD>(Shims.name));
    this.addRule(new MaxLength<ParentD>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentD>(Shims.name));
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

  public List<ParentDChildB> getParentDChildBs() {
    return this.parentDChildBs.get();
  }

  public void setParentDChildBs(List<ParentDChildB> parentDChildBs) {
    ListDiff<ParentDChildB> diff = ListDiff.of(this.getParentDChildBs(), parentDChildBs);
    for (ParentDChildB o : diff.removed) {
      this.removeParentDChildB(o);
    }
    for (ParentDChildB o : diff.added) {
      this.addParentDChildB(o);
    }
    this.parentDChildBs.set(parentDChildBs);
  }

  public void addParentDChildB(ParentDChildB o) {
    if (o.getParentD() == this) {
      return;
    }
    o.setParentDWithoutPercolation((ParentD) this);
    this.addParentDChildBWithoutPercolation(o);
  }

  public void removeParentDChildB(ParentDChildB o) {
    if (o.getParentD() != this) {
      return;
    }
    o.setParentDWithoutPercolation(null);
    this.removeParentDChildBWithoutPercolation(o);
  }

  protected void addParentDChildBWithoutPercolation(ParentDChildB o) {
    this.getChanged().record("parentDChildBs");
    this.parentDChildBs.add(o);
  }

  protected void removeParentDChildBWithoutPercolation(ParentDChildB o) {
    this.getChanged().record("parentDChildBs");
    this.parentDChildBs.remove(o);
  }

  public ParentDChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentDChanged((ParentD) this);
    }
    return (ParentDChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ParentDChildB o : Copy.list(this.getParentDChildBs())) {
      removeParentDChildB(o);
    }
  }

  static class Shims {
    protected static final Shim<ParentD, Long> id = new Shim<ParentD, Long>() {
      public void set(ParentD instance, Long id) {
        ((ParentDCodegen) instance).id = id;
      }
      public Long get(ParentD instance) {
        return ((ParentDCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentD, String> name = new Shim<ParentD, String>() {
      public void set(ParentD instance, String name) {
        ((ParentDCodegen) instance).name = name;
      }
      public String get(ParentD instance) {
        return ((ParentDCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentD, Long> version = new Shim<ParentD, Long>() {
      public void set(ParentD instance, Long version) {
        ((ParentDCodegen) instance).version = version;
      }
      public Long get(ParentD instance) {
        return ((ParentDCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ParentDChildBsListDelegate implements ListProxy.Delegate<ParentDChildB> {
    public void doAdd(ParentDChildB e) {
      addParentDChildB(e);
    }
    public void doRemove(ParentDChildB e) {
      removeParentDChildB(e);
    }
  }

  public static class ParentDChanged extends AbstractChanged {
    public ParentDChanged(ParentD instance) {
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
    public boolean hasParentDChildBs() {
      return this.contains("parentDChildBs");
    }
  }

}
