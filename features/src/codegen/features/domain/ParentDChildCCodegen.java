package features.domain;

import features.domain.queries.ParentDChildCQueries;
import java.util.ArrayList;
import java.util.Collections;
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
public abstract class ParentDChildCCodegen extends AbstractDomainObject {

  public static final ParentDChildCQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyListHolder<ParentDChildC, ParentDToChildC> parentDToChildCs = new ForeignKeyListHolder<ParentDChildC, ParentDToChildC>((ParentDChildC) this, Aliases.parentDToChildC(), Aliases.parentDToChildC().parentDChildC, new ParentDToChildCsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentDChildC();
    queries = new ParentDChildCQueries();
  }

  protected ParentDChildCCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentDChildC>(Shims.name));
    this.addRule(new MaxLength<ParentDChildC>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentDChildC>(Shims.name));
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

  private List<ParentDToChildC> getParentDToChildCs() {
    return this.parentDToChildCs.get();
  }

  private void setParentDToChildCs(List<ParentDToChildC> parentDToChildCs) {
    ListDiff<ParentDToChildC> diff = ListDiff.of(this.getParentDToChildCs(), parentDToChildCs);
    for (ParentDToChildC o : diff.removed) {
      this.removeParentDToChildC(o);
    }
    for (ParentDToChildC o : diff.added) {
      this.addParentDToChildC(o);
    }
    this.parentDToChildCs.set(parentDToChildCs);
  }

  private void addParentDToChildC(ParentDToChildC o) {
    if (o.getParentDChildC() == this) {
      return;
    }
    o.setParentDChildCWithoutPercolation((ParentDChildC) this);
    this.addParentDToChildCWithoutPercolation(o);
  }

  private void removeParentDToChildC(ParentDToChildC o) {
    if (o.getParentDChildC() != this) {
      return;
    }
    o.setParentDChildCWithoutPercolation(null);
    this.removeParentDToChildCWithoutPercolation(o);
    if (UoW.isOpen()) {
      ParentDToChildC.queries.delete(o);
    }
  }

  protected void addParentDToChildCWithoutPercolation(ParentDToChildC o) {
    this.getChanged().record("parentDToChildCs");
    this.parentDToChildCs.add(o);
  }

  protected void removeParentDToChildCWithoutPercolation(ParentDToChildC o) {
    this.getChanged().record("parentDToChildCs");
    this.parentDToChildCs.remove(o);
  }

  public List<ParentD> getParentDs() {
    List<ParentD> l = new ArrayList<ParentD>();
    for (ParentDToChildC o : this.getParentDToChildCs()) {
      l.add(o.getParentD());
    }
    return Collections.unmodifiableList(l);
  }

  public void setParentDs(List<ParentD> parentDs) {
    ListDiff<ParentD> diff = ListDiff.of(this.getParentDs(), parentDs);
    for (ParentD o : diff.removed) {
      this.removeParentD(o);
    }
    for (ParentD o : diff.added) {
      this.addParentD(o);
    }
  }

  public void addParentD(ParentD o) {
    ParentDToChildC a = new ParentDToChildC();
    a.setParentDChildC((ParentDChildC) this);
    a.setParentD(o);
  }

  public void removeParentD(ParentD o) {
    for (ParentDToChildC a : Copy.list(this.getParentDToChildCs())) {
      if (a.getParentD().equals(o)) {
        a.setParentD(null);
        a.setParentDChildC(null);
        if (UoW.isOpen()) {
          UoW.delete(a);
        }
      }
    }
  }

  public ParentDChildCChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentDChildCChanged((ParentDChildC) this);
    }
    return (ParentDChildCChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    for (ParentDToChildC o : Copy.list(this.getParentDToChildCs())) {
      removeParentDToChildC(o);
    }
  }

  static class Shims {
    protected static final Shim<ParentDChildC, Long> id = new Shim<ParentDChildC, Long>() {
      public void set(ParentDChildC instance, Long id) {
        ((ParentDChildCCodegen) instance).id = id;
      }
      public Long get(ParentDChildC instance) {
        return ((ParentDChildCCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentDChildC, String> name = new Shim<ParentDChildC, String>() {
      public void set(ParentDChildC instance, String name) {
        ((ParentDChildCCodegen) instance).name = name;
      }
      public String get(ParentDChildC instance) {
        return ((ParentDChildCCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentDChildC, Long> version = new Shim<ParentDChildC, Long>() {
      public void set(ParentDChildC instance, Long version) {
        ((ParentDChildCCodegen) instance).version = version;
      }
      public Long get(ParentDChildC instance) {
        return ((ParentDChildCCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  private class ParentDToChildCsListDelegate implements ListProxy.Delegate<ParentDToChildC> {
    public void doAdd(ParentDToChildC e) {
      addParentDToChildC(e);
    }
    public void doRemove(ParentDToChildC e) {
      removeParentDToChildC(e);
    }
  }

  public static class ParentDChildCChanged extends AbstractChanged {
    public ParentDChildCChanged(ParentDChildC instance) {
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
    public boolean hasParentDToChildCs() {
      return this.contains("parentDToChildCs");
    }
  }

}
