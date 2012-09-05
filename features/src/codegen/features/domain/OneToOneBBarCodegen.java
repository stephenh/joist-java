package features.domain;

import features.domain.queries.OneToOneBBarQueries;
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
public abstract class OneToOneBBarCodegen extends AbstractDomainObject {

  public static final OneToOneBBarQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyHolder<OneToOneBBar, OneToOneBFoo> oneToOneBFoo = new ForeignKeyHolder<OneToOneBBar, OneToOneBFoo>(OneToOneBBar.class, OneToOneBFoo.class, Aliases.oneToOneBFoo(), Aliases.oneToOneBBar().oneToOneBFoo);
  protected Changed changed;

  static {
    Aliases.oneToOneBBar();
    queries = new OneToOneBBarQueries();
  }

  protected OneToOneBBarCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<OneToOneBBar>(Shims.name));
    this.addRule(new MaxLength<OneToOneBBar>(Shims.name, 100));
    this.addRule(new NotEmpty<OneToOneBBar>(Shims.name));
    this.addRule(new NotNull<OneToOneBBar>(Shims.oneToOneBFooId));
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

  public OneToOneBFoo getOneToOneBFoo() {
    return this.oneToOneBFoo.get();
  }

  public void setOneToOneBFoo(OneToOneBFoo oneToOneBFoo) {
    if (oneToOneBFoo == this.getOneToOneBFoo()) {
      return;
    }
    if (this.oneToOneBFoo.get() != null) {
      this.oneToOneBFoo.get().removeOneToOneBBarWithoutPercolation((OneToOneBBar) this);
    }
    this.setOneToOneBFooWithoutPercolation(oneToOneBFoo);
    if (this.oneToOneBFoo.get() != null) {
      this.oneToOneBFoo.get().addOneToOneBBarWithoutPercolation((OneToOneBBar) this);
    }
  }

  protected void setOneToOneBFooWithoutPercolation(OneToOneBFoo oneToOneBFoo) {
    this.getChanged().record("oneToOneBFoo", this.oneToOneBFoo.get(), oneToOneBFoo);
    this.oneToOneBFoo.set(oneToOneBFoo);
  }

  public OneToOneBBarChanged getChanged() {
    if (this.changed == null) {
      this.changed = new OneToOneBBarChanged((OneToOneBBar) this);
    }
    return (OneToOneBBarChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setOneToOneBFoo(null);
  }

  static class Shims {
    protected static final Shim<OneToOneBBar, Long> id = new Shim<OneToOneBBar, Long>() {
      public void set(OneToOneBBar instance, Long id) {
        ((OneToOneBBarCodegen) instance).id = id;
      }
      public Long get(OneToOneBBar instance) {
        return ((OneToOneBBarCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<OneToOneBBar, String> name = new Shim<OneToOneBBar, String>() {
      public void set(OneToOneBBar instance, String name) {
        ((OneToOneBBarCodegen) instance).name = name;
      }
      public String get(OneToOneBBar instance) {
        return ((OneToOneBBarCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<OneToOneBBar, Long> version = new Shim<OneToOneBBar, Long>() {
      public void set(OneToOneBBar instance, Long version) {
        ((OneToOneBBarCodegen) instance).version = version;
      }
      public Long get(OneToOneBBar instance) {
        return ((OneToOneBBarCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<OneToOneBBar, Long> oneToOneBFooId = new Shim<OneToOneBBar, Long>() {
      public void set(OneToOneBBar instance, Long oneToOneBFooId) {
        ((OneToOneBBarCodegen) instance).oneToOneBFoo.setId(oneToOneBFooId);
      }
      public Long get(OneToOneBBar instance) {
        return ((OneToOneBBarCodegen) instance).oneToOneBFoo.getId();
      }
      public String getName() {
        return "oneToOneBFoo";
      }
    };
  }

  public static class OneToOneBBarChanged extends AbstractChanged {
    public OneToOneBBarChanged(OneToOneBBar instance) {
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
    public boolean hasOneToOneBFoo() {
      return this.contains("oneToOneBFoo");
    }
    public OneToOneBFoo getOriginalOneToOneBFoo() {
      return (OneToOneBFoo) this.getOriginal("oneToOneBFoo");
    }
  }

}
