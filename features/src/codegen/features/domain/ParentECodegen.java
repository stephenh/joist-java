package features.domain;

import features.domain.queries.ParentEQueries;
import java.util.List;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.orm.ForeignKeyListHolder;
import joist.domain.uow.UoW;
import joist.domain.util.ListProxy;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;
import joist.util.Copy;
import joist.util.ListDiff;

@SuppressWarnings("all")
public abstract class ParentECodegen extends AbstractDomainObject {

  public static final ParentEQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentE, ParentE> parentE = new ForeignKeyHolder<ParentE, ParentE>(ParentE.class, ParentE.class, Aliases.parentE(), Aliases.parentE().parentE);
  private final ForeignKeyListHolder<ParentE, ParentE> parentEs = new ForeignKeyListHolder<ParentE, ParentE>((ParentE) this, Aliases.parentE(), Aliases.parentE().parentE, new ParentEsListDelegate());
  protected Changed changed;

  static {
    Aliases.parentE();
    queries = new ParentEQueries();
  }

  protected ParentECodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentE>(Shims.name));
    this.addRule(new MaxLength<ParentE>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentE>(Shims.name));
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

  public ParentE getParentE() {
    return this.parentE.get();
  }

  public void setParentE(ParentE parentE) {
    if (parentE == this.getParentE()) {
      return;
    }
    if (this.parentE.get() != null) {
      this.parentE.get().removeParentEWithoutPercolation((ParentE) this);
    }
    this.setParentEWithoutPercolation(parentE);
    if (this.parentE.get() != null) {
      this.parentE.get().addParentEWithoutPercolation((ParentE) this);
    }
  }

  protected void setParentEWithoutPercolation(ParentE parentE) {
    this.getChanged().record("parentE", this.parentE.get(), parentE);
    this.parentE.set(parentE);
  }

  public List<ParentE> getParentEs() {
    return this.parentEs.get();
  }

  public void setParentEs(List<ParentE> parentEs) {
    ListDiff<ParentE> diff = ListDiff.of(this.getParentEs(), parentEs);
    for (ParentE o : diff.removed) {
      this.removeParentE(o);
    }
    for (ParentE o : diff.added) {
      this.addParentE(o);
    }
    this.parentEs.set(parentEs);
  }

  public void addParentE(ParentE o) {
    if (o.getParentE() == this) {
      return;
    }
    o.setParentEWithoutPercolation((ParentE) this);
    this.addParentEWithoutPercolation(o);
  }

  public void removeParentE(ParentE o) {
    if (o.getParentE() != this) {
      return;
    }
    o.setParentEWithoutPercolation(null);
    this.removeParentEWithoutPercolation(o);
  }

  protected void addParentEWithoutPercolation(ParentE o) {
    this.getChanged().record("parentEs");
    this.parentEs.add(o);
  }

  protected void removeParentEWithoutPercolation(ParentE o) {
    this.getChanged().record("parentEs");
    this.parentEs.remove(o);
  }

  public ParentEChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentEChanged((ParentE) this);
    }
    return (ParentEChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentE(null);
    for (ParentE o : Copy.list(this.getParentEs())) {
      removeParentE(o);
    }
  }

  static class Shims {
    protected static final Shim<ParentE, Long> id = new Shim<ParentE, Long>() {
      public void set(ParentE instance, Long id) {
        ((ParentECodegen) instance).id = id;
      }
      public Long get(ParentE instance) {
        return ((ParentECodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentE, String> name = new Shim<ParentE, String>() {
      public void set(ParentE instance, String name) {
        ((ParentECodegen) instance).name = name;
      }
      public String get(ParentE instance) {
        return ((ParentECodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentE, Long> version = new Shim<ParentE, Long>() {
      public void set(ParentE instance, Long version) {
        ((ParentECodegen) instance).version = version;
      }
      public Long get(ParentE instance) {
        return ((ParentECodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentE, Long> parentEId = new Shim<ParentE, Long>() {
      public void set(ParentE instance, Long parentEId) {
        ((ParentECodegen) instance).parentE.setId(parentEId);
      }
      public Long get(ParentE instance) {
        return ((ParentECodegen) instance).parentE.getId();
      }
      public String getName() {
        return "parentE";
      }
    };
  }

  private class ParentEsListDelegate implements ListProxy.Delegate<ParentE> {
    public void doAdd(ParentE e) {
      addParentE(e);
    }
    public void doRemove(ParentE e) {
      removeParentE(e);
    }
  }

  public static class ParentEChanged extends AbstractChanged {
    public ParentEChanged(ParentE instance) {
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
    public boolean hasParentE() {
      return this.contains("parentE");
    }
    public ParentE getOriginalParentE() {
      return (ParentE) this.getOriginal("parentE");
    }
    public boolean hasParentEs() {
      return this.contains("parentEs");
    }
  }

}
