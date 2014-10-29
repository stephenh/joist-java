package features.domain;

import features.domain.queries.ParentGQueries;
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

@SuppressWarnings("all")
public abstract class ParentGCodegen extends AbstractDomainObject {

  public static final ParentGQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ParentG, ChildG> parentOneChildGs = new ForeignKeyListHolder<ParentG, ChildG>((ParentG) this, Aliases.childG(), Aliases.childG().parentOne, new ParentOneChildGsListDelegate());
  private final ForeignKeyListHolder<ParentG, ChildG> parentTwoChildGs = new ForeignKeyListHolder<ParentG, ChildG>((ParentG) this, Aliases.childG(), Aliases.childG().parentTwo, new ParentTwoChildGsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentG();
    queries = new ParentGQueries();
  }

  protected ParentGCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentG>(Shims.name));
    this.addRule(new MaxLength<ParentG>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentG>(Shims.name));
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

  public ChildG getParentOneChildG() {
    return (this.parentOneChildGs.get().size() == 0) ? null : this.parentOneChildGs.get().get(0);
  }

  public void setParentOneChildG(ChildG n) {
    ChildG o = this.getParentOneChildG();
    if (o == n) {
      return;
    }
    if (o != null) {
      o.setParentOneWithoutPercolation(null);
      this.removeParentOneChildGWithoutPercolation(o);
    }
    if (n != null) {
      n.setParentOneWithoutPercolation((ParentG) this);
      this.addParentOneChildGWithoutPercolation(n);
    }
  }

  protected void addParentOneChildGWithoutPercolation(ChildG o) {
    this.getChanged().record("parentOneChildGs");
    this.parentOneChildGs.add(o);
  }

  protected void removeParentOneChildGWithoutPercolation(ChildG o) {
    this.getChanged().record("parentOneChildGs");
    this.parentOneChildGs.remove(o);
  }

  public ChildG getParentTwoChildG() {
    return (this.parentTwoChildGs.get().size() == 0) ? null : this.parentTwoChildGs.get().get(0);
  }

  public void setParentTwoChildG(ChildG n) {
    ChildG o = this.getParentTwoChildG();
    if (o == n) {
      return;
    }
    if (o != null) {
      o.setParentTwoWithoutPercolation(null);
      this.removeParentTwoChildGWithoutPercolation(o);
    }
    if (n != null) {
      n.setParentTwoWithoutPercolation((ParentG) this);
      this.addParentTwoChildGWithoutPercolation(n);
    }
  }

  protected void addParentTwoChildGWithoutPercolation(ChildG o) {
    this.getChanged().record("parentTwoChildGs");
    this.parentTwoChildGs.add(o);
  }

  protected void removeParentTwoChildGWithoutPercolation(ChildG o) {
    this.getChanged().record("parentTwoChildGs");
    this.parentTwoChildGs.remove(o);
  }

  public ParentGChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentGChanged((ParentG) this);
    }
    return (ParentGChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentOneChildG(null);
    this.setParentTwoChildG(null);
  }

  static class Shims {
    protected static final Shim<ParentG, Long> id = new Shim<ParentG, Long>() {
      public void set(ParentG instance, Long id) {
        ((ParentGCodegen) instance).id = id;
      }
      public Long get(ParentG instance) {
        return ((ParentGCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentG, String> name = new Shim<ParentG, String>() {
      public void set(ParentG instance, String name) {
        ((ParentGCodegen) instance).name = name;
      }
      public String get(ParentG instance) {
        return ((ParentGCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentG, Long> version = new Shim<ParentG, Long>() {
      public void set(ParentG instance, Long version) {
        ((ParentGCodegen) instance).version = version;
      }
      public Long get(ParentG instance) {
        return ((ParentGCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ParentOneChildGsListDelegate implements ListProxy.Delegate<ChildG> {
    public void doAdd(ChildG e) {
      throw new UnsupportedOperationException("Not implemented");
    }
    public void doRemove(ChildG e) {
      throw new UnsupportedOperationException("Not implemented");
    }
  }

  private class ParentTwoChildGsListDelegate implements ListProxy.Delegate<ChildG> {
    public void doAdd(ChildG e) {
      throw new UnsupportedOperationException("Not implemented");
    }
    public void doRemove(ChildG e) {
      throw new UnsupportedOperationException("Not implemented");
    }
  }

  public static class ParentGChanged extends AbstractChanged {
    public ParentGChanged(ParentG instance) {
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
    public boolean hasParentOneChildGs() {
      return this.contains("parentOneChildGs");
    }
    public boolean hasParentTwoChildGs() {
      return this.contains("parentTwoChildGs");
    }
  }

}
