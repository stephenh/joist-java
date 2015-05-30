package features.domain;

import features.domain.queries.InheritanceASubOneQueries;
import java.util.List;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.util.ListProxy;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;
import joist.util.ListDiff;

@SuppressWarnings("all")
public abstract class InheritanceASubOneCodegen extends InheritanceABase {

  public static final InheritanceASubOneQueries queries;
  private String one = null;
  private final ForeignKeyHolder<InheritanceASubOne, InheritanceAThing> inheritanceAThing = new ForeignKeyHolder<InheritanceASubOne, InheritanceAThing>(InheritanceASubOne.class, InheritanceAThing.class, Aliases.inheritanceAThing(), Aliases.inheritanceASubOne().inheritanceAThing);
  private final ForeignKeyListHolder<InheritanceASubOne, InheritanceASubOneChild> inheritanceASubOneChilds = new ForeignKeyListHolder<InheritanceASubOne, InheritanceASubOneChild>((InheritanceASubOne) this, Aliases.inheritanceASubOneChild(), Aliases.inheritanceASubOneChild().sub, new InheritanceASubOneChildsListDelegate());

  static {
    Aliases.inheritanceASubOne();
    queries = new InheritanceASubOneQueries();
  }

  protected InheritanceASubOneCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceASubOne>(Shims.one));
    this.addRule(new MaxLength<InheritanceASubOne>(Shims.one, 100));
    this.addRule(new NotEmpty<InheritanceASubOne>(Shims.one));
  }

  public String getOne() {
    return this.one;
  }

  public void setOne(String one) {
    this.getChanged().record("one", this.one, one);
    this.one = one;
  }

  protected void defaultOne(String one) {
    this.one = one;
  }

  public InheritanceAThing getInheritanceAThing() {
    return this.inheritanceAThing.get();
  }

  public void setInheritanceAThing(InheritanceAThing inheritanceAThing) {
    if (inheritanceAThing == this.getInheritanceAThing()) {
      return;
    }
    if (this.inheritanceAThing.get() != null) {
      this.inheritanceAThing.get().removeInheritanceASubOneWithoutPercolation((InheritanceASubOne) this);
    }
    this.setInheritanceAThingWithoutPercolation(inheritanceAThing);
    if (this.inheritanceAThing.get() != null) {
      this.inheritanceAThing.get().addInheritanceASubOneWithoutPercolation((InheritanceASubOne) this);
    }
  }

  protected void setInheritanceAThingWithoutPercolation(InheritanceAThing inheritanceAThing) {
    this.getChanged().record("inheritanceAThing", this.inheritanceAThing.get(), inheritanceAThing);
    this.inheritanceAThing.set(inheritanceAThing);
  }

  public List<InheritanceASubOneChild> getInheritanceASubOneChilds() {
    return this.inheritanceASubOneChilds.get();
  }

  public void setInheritanceASubOneChilds(List<InheritanceASubOneChild> inheritanceASubOneChilds) {
    ListDiff<InheritanceASubOneChild> diff = ListDiff.of(this.getInheritanceASubOneChilds(), inheritanceASubOneChilds);
    for (InheritanceASubOneChild o : diff.removed) {
      this.removeInheritanceASubOneChild(o);
    }
    for (InheritanceASubOneChild o : diff.added) {
      this.addInheritanceASubOneChild(o);
    }
    this.inheritanceASubOneChilds.set(inheritanceASubOneChilds);
  }

  public void addInheritanceASubOneChild(InheritanceASubOneChild o) {
    if (o.getSub() == this) {
      return;
    }
    o.setSubWithoutPercolation((InheritanceASubOne) this);
    this.addInheritanceASubOneChildWithoutPercolation(o);
  }

  public void removeInheritanceASubOneChild(InheritanceASubOneChild o) {
    if (o.getSub() != this) {
      return;
    }
    o.setSubWithoutPercolation(null);
    this.removeInheritanceASubOneChildWithoutPercolation(o);
  }

  protected void addInheritanceASubOneChildWithoutPercolation(InheritanceASubOneChild o) {
    this.getChanged().record("inheritanceASubOneChilds");
    this.inheritanceASubOneChilds.add(o);
  }

  protected void removeInheritanceASubOneChildWithoutPercolation(InheritanceASubOneChild o) {
    this.getChanged().record("inheritanceASubOneChilds");
    this.inheritanceASubOneChilds.remove(o);
  }

  public InheritanceASubOneChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceASubOneChanged((InheritanceASubOne) this);
    }
    return (InheritanceASubOneChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setInheritanceAThing(null);
    for (InheritanceASubOneChild o : Copy.list(this.getInheritanceASubOneChilds())) {
      removeInheritanceASubOneChild(o);
    }
  }

  static class Shims {
    protected static final Shim<InheritanceASubOne, String> one = new Shim<InheritanceASubOne, String>() {
      public void set(InheritanceASubOne instance, String one) {
        ((InheritanceASubOneCodegen) instance).one = one;
      }
      public String get(InheritanceASubOne instance) {
        return ((InheritanceASubOneCodegen) instance).one;
      }
      public String getName() {
        return "one";
      }
    };
    protected static final Shim<InheritanceASubOne, Long> inheritanceAThingId = new Shim<InheritanceASubOne, Long>() {
      public void set(InheritanceASubOne instance, Long inheritanceAThingId) {
        ((InheritanceASubOneCodegen) instance).inheritanceAThing.setId(inheritanceAThingId);
      }
      public Long get(InheritanceASubOne instance) {
        return ((InheritanceASubOneCodegen) instance).inheritanceAThing.getId();
      }
      public String getName() {
        return "inheritanceAThing";
      }
    };
  }

  private class InheritanceASubOneChildsListDelegate implements ListProxy.Delegate<InheritanceASubOneChild> {
    public void doAdd(InheritanceASubOneChild e) {
      addInheritanceASubOneChild(e);
    }
    public void doRemove(InheritanceASubOneChild e) {
      removeInheritanceASubOneChild(e);
    }
  }

  public static class InheritanceASubOneChanged extends InheritanceABaseChanged {
    public InheritanceASubOneChanged(InheritanceASubOne instance) {
      super(instance);
    }
    public boolean hasOne() {
      return this.contains("one");
    }
    public String getOriginalOne() {
      return (java.lang.String) this.getOriginal("one");
    }
    public boolean hasInheritanceAThing() {
      return this.contains("inheritanceAThing");
    }
    public InheritanceAThing getOriginalInheritanceAThing() {
      return (InheritanceAThing) this.getOriginal("inheritanceAThing");
    }
    public boolean hasInheritanceASubOneChilds() {
      return this.contains("inheritanceASubOneChilds");
    }
  }

}
