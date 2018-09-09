package features.domain;

import com.linkedin.parseq.Task;
import features.domain.queries.PrimitivesQueries;
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
public abstract class PrimitivesCodegen extends AbstractDomainObject {

  public static final PrimitivesQueries queries;
  private Boolean flag = null;
  private Long id = null;
  private String name = null;
  private String skipped = null;
  private Long version = null;
  private final ForeignKeyHolder<Primitives, Parent> parent = new ForeignKeyHolder<Primitives, Parent>(Primitives.class, Parent.class, Aliases.parent(), Aliases.primitives().parent);
  protected Changed changed;

  static {
    Aliases.primitives();
    queries = new PrimitivesQueries();
  }

  protected PrimitivesCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<Primitives>(Shims.flag));
    this.addRule(new NotNull<Primitives>(Shims.name));
    this.addRule(new MaxLength<Primitives>(Shims.name, 100));
    this.addRule(new NotEmpty<Primitives>(Shims.name));
    this.addRule(new MaxLength<Primitives>(Shims.skipped, 100));
    this.addRule(new NotEmpty<Primitives>(Shims.skipped));
  }

  public Boolean getFlag() {
    return this.flag;
  }

  public void setFlag(Boolean flag) {
    this.getChanged().record("flag", this.flag, flag);
    this.flag = flag;
  }

  protected void defaultFlag(Boolean flag) {
    this.flag = flag;
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

  public String getSkipped() {
    return this.skipped;
  }

  public void setSkipped(String skipped) {
    this.getChanged().record("skipped", this.skipped, skipped);
    this.skipped = skipped;
  }

  protected void defaultSkipped(String skipped) {
    this.skipped = skipped;
  }

  public Long getVersion() {
    return this.version;
  }

  public Task<Parent> getParent() {
    return this.parent.get();
  }

  public void setParent(Parent parent) {
    if (parent == this.getParent()) {
      return;
    }
    if (this.parent.get() != null) {
      this.parent.get().removePrimitivesWithoutPercolation((Primitives) this);
    }
    this.setParentWithoutPercolation(parent);
    if (this.parent.get() != null) {
      this.parent.get().addPrimitivesWithoutPercolation((Primitives) this);
    }
  }

  protected void setParentWithoutPercolation(Parent parent) {
    this.getChanged().record("parent", this.parent.get(), parent);
    this.parent.set(parent);
  }

  public PrimitivesChanged getChanged() {
    if (this.changed == null) {
      this.changed = new PrimitivesChanged((Primitives) this);
    }
    return (PrimitivesChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setParent(null);
  }

  static class Shims {
    protected static final Shim<Primitives, Boolean> flag = new Shim<Primitives, Boolean>() {
      public void set(Primitives instance, Boolean flag) {
        ((PrimitivesCodegen) instance).flag = flag;
      }
      public Boolean get(Primitives instance) {
        return ((PrimitivesCodegen) instance).flag;
      }
      public String getName() {
        return "flag";
      }
    };
    protected static final Shim<Primitives, Long> id = new Shim<Primitives, Long>() {
      public void set(Primitives instance, Long id) {
        ((PrimitivesCodegen) instance).id = id;
      }
      public Long get(Primitives instance) {
        return ((PrimitivesCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<Primitives, String> name = new Shim<Primitives, String>() {
      public void set(Primitives instance, String name) {
        ((PrimitivesCodegen) instance).name = name;
      }
      public String get(Primitives instance) {
        return ((PrimitivesCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<Primitives, String> skipped = new Shim<Primitives, String>() {
      public void set(Primitives instance, String skipped) {
        ((PrimitivesCodegen) instance).skipped = skipped;
      }
      public String get(Primitives instance) {
        return ((PrimitivesCodegen) instance).skipped;
      }
      public String getName() {
        return "skipped";
      }
    };
    protected static final Shim<Primitives, Long> version = new Shim<Primitives, Long>() {
      public void set(Primitives instance, Long version) {
        ((PrimitivesCodegen) instance).version = version;
      }
      public Long get(Primitives instance) {
        return ((PrimitivesCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<Primitives, Long> parentId = new Shim<Primitives, Long>() {
      public void set(Primitives instance, Long parentId) {
        ((PrimitivesCodegen) instance).parent.setId(parentId);
      }
      public Long get(Primitives instance) {
        return ((PrimitivesCodegen) instance).parent.getId();
      }
      public String getName() {
        return "parent";
      }
    };
  }

  public static class PrimitivesChanged extends AbstractChanged {
    public PrimitivesChanged(Primitives instance) {
      super(instance);
    }
    public boolean hasFlag() {
      return this.contains("flag");
    }
    public Boolean getOriginalFlag() {
      return (java.lang.Boolean) this.getOriginal("flag");
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
    public boolean hasSkipped() {
      return this.contains("skipped");
    }
    public String getOriginalSkipped() {
      return (java.lang.String) this.getOriginal("skipped");
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
    public Parent getOriginalParent() {
      return (Parent) this.getOriginal("parent");
    }
  }

}
