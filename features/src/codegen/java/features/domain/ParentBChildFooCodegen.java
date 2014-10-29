package features.domain;

import features.domain.queries.ParentBChildFooQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ParentBChildFooCodegen extends AbstractDomainObject {

  public static final ParentBChildFooQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<ParentBChildFoo, ParentBParent> parentBParent = new ForeignKeyHolder<ParentBChildFoo, ParentBParent>(ParentBChildFoo.class, ParentBParent.class, Aliases.parentBParent(), Aliases.parentBChildFoo().parentBParent);
  protected Changed changed;

  static {
    Aliases.parentBChildFoo();
    queries = new ParentBChildFooQueries();
  }

  protected ParentBChildFooCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ParentBChildFoo>(Shims.name));
    this.addRule(new MaxLength<ParentBChildFoo>(Shims.name, 100));
    this.addRule(new NotEmpty<ParentBChildFoo>(Shims.name));
    this.addRule(new NotNull<ParentBChildFoo>(Shims.parentBParentId));
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

  public ParentBParent getParentBParent() {
    return this.parentBParent.get();
  }

  public void setParentBParent(ParentBParent parentBParent) {
    if (parentBParent == this.getParentBParent()) {
      return;
    }
    if (this.parentBParent.get() != null) {
      this.parentBParent.get().removeParentBChildFooWithoutPercolation((ParentBChildFoo) this);
    }
    this.setParentBParentWithoutPercolation(parentBParent);
    if (this.parentBParent.get() != null) {
      this.parentBParent.get().addParentBChildFooWithoutPercolation((ParentBChildFoo) this);
    }
  }

  protected void setParentBParentWithoutPercolation(ParentBParent parentBParent) {
    this.getChanged().record("parentBParent", this.parentBParent.get(), parentBParent);
    this.parentBParent.set(parentBParent);
  }

  public ParentBChildFooChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ParentBChildFooChanged((ParentBChildFoo) this);
    }
    return (ParentBChildFooChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParentBParent(null);
  }

  static class Shims {
    protected static final Shim<ParentBChildFoo, Long> id = new Shim<ParentBChildFoo, Long>() {
      public void set(ParentBChildFoo instance, Long id) {
        ((ParentBChildFooCodegen) instance).id = id;
      }
      public Long get(ParentBChildFoo instance) {
        return ((ParentBChildFooCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ParentBChildFoo, String> name = new Shim<ParentBChildFoo, String>() {
      public void set(ParentBChildFoo instance, String name) {
        ((ParentBChildFooCodegen) instance).name = name;
      }
      public String get(ParentBChildFoo instance) {
        return ((ParentBChildFooCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ParentBChildFoo, Long> version = new Shim<ParentBChildFoo, Long>() {
      public void set(ParentBChildFoo instance, Long version) {
        ((ParentBChildFooCodegen) instance).version = version;
      }
      public Long get(ParentBChildFoo instance) {
        return ((ParentBChildFooCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<ParentBChildFoo, Long> parentBParentId = new Shim<ParentBChildFoo, Long>() {
      public void set(ParentBChildFoo instance, Long parentBParentId) {
        ((ParentBChildFooCodegen) instance).parentBParent.setId(parentBParentId);
      }
      public Long get(ParentBChildFoo instance) {
        return ((ParentBChildFooCodegen) instance).parentBParent.getId();
      }
      public String getName() {
        return "parentBParent";
      }
    };
  }

  public static class ParentBChildFooChanged extends AbstractChanged {
    public ParentBChildFooChanged(ParentBChildFoo instance) {
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
    public boolean hasParentBParent() {
      return this.contains("parentBParent");
    }
    public ParentBParent getOriginalParentBParent() {
      return (ParentBParent) this.getOriginal("parentBParent");
    }
  }

}
