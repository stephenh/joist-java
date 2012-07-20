package features.domain;

import features.domain.queries.ValuesAQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ValuesACodegen extends AbstractDomainObject {

  public static final ValuesAQueries queries;
  private Integer i = null;
  private Long id = null;
  private String name = null;
  private Long version = null;
  protected Changed changed;

  static {
    Aliases.valuesA();
    queries = new ValuesAQueries();
  }

  protected ValuesACodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ValuesA>(Shims.name));
    this.addRule(new MaxLength<ValuesA>(Shims.name, 100));
    this.addRule(new NotEmpty<ValuesA>(Shims.name));
  }

  public Integer getI() {
    return this.i;
  }

  public void setI(Integer i) {
    this.getChanged().record("i", this.i, i);
    this.i = i;
  }

  protected void defaultI(Integer i) {
    this.i = i;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
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

  public ValuesAChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ValuesAChanged((ValuesA) this);
    }
    return (ValuesAChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<ValuesA, Integer> i = new Shim<ValuesA, Integer>() {
      public void set(ValuesA instance, Integer i) {
        ((ValuesACodegen) instance).i = i;
      }
      public Integer get(ValuesA instance) {
        return ((ValuesACodegen) instance).i;
      }
      public String getName() {
        return "i";
      }
    };
    protected static final Shim<ValuesA, Long> id = new Shim<ValuesA, Long>() {
      public void set(ValuesA instance, Long id) {
        ((ValuesACodegen) instance).id = id;
      }
      public Long get(ValuesA instance) {
        return ((ValuesACodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ValuesA, String> name = new Shim<ValuesA, String>() {
      public void set(ValuesA instance, String name) {
        ((ValuesACodegen) instance).name = name;
      }
      public String get(ValuesA instance) {
        return ((ValuesACodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ValuesA, Long> version = new Shim<ValuesA, Long>() {
      public void set(ValuesA instance, Long version) {
        ((ValuesACodegen) instance).version = version;
      }
      public Long get(ValuesA instance) {
        return ((ValuesACodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  public static class ValuesAChanged extends AbstractChanged {
    public ValuesAChanged(ValuesA instance) {
      super(instance);
    }
    public boolean hasI() {
      return this.contains("i");
    }
    public Integer getOriginalI() {
      return (java.lang.Integer) this.getOriginal("i");
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
  }

}
