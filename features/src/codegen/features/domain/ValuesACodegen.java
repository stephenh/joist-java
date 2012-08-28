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
  private String a = "a";
  private String b = "b";
  private Integer i = 1;
  private Long id = null;
  private Integer j = 2;
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
    this.addRule(new MaxLength<ValuesA>(Shims.a, 100));
    this.addRule(new NotEmpty<ValuesA>(Shims.a));
    this.addRule(new NotNull<ValuesA>(Shims.b));
    this.addRule(new MaxLength<ValuesA>(Shims.b, 100));
    this.addRule(new NotEmpty<ValuesA>(Shims.b));
    this.addRule(new NotNull<ValuesA>(Shims.j));
    this.addRule(new NotNull<ValuesA>(Shims.name));
    this.addRule(new MaxLength<ValuesA>(Shims.name, 100));
    this.addRule(new NotEmpty<ValuesA>(Shims.name));
  }

  public String getA() {
    return this.a;
  }

  public void setA(String a) {
    this.getChanged().record("a", this.a, a);
    this.a = a;
  }

  protected void defaultA(String a) {
    this.a = a;
  }

  public String getB() {
    return this.b;
  }

  public void setB(String b) {
    this.getChanged().record("b", this.b, b);
    this.b = b;
  }

  protected void defaultB(String b) {
    this.b = b;
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
    if (this.id != null) {
      throw new IllegalStateException(this + " id cannot be changed");
    }
    this.getChanged().record("id", this.id, id);
    this.id = id;
    if (UoW.isOpen()) {
      UoW.getIdentityMap().store(this);
    }
  }

  public Integer getJ() {
    return this.j;
  }

  public void setJ(Integer j) {
    this.getChanged().record("j", this.j, j);
    this.j = j;
  }

  protected void defaultJ(Integer j) {
    this.j = j;
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
    protected static final Shim<ValuesA, String> a = new Shim<ValuesA, String>() {
      public void set(ValuesA instance, String a) {
        ((ValuesACodegen) instance).a = a;
      }
      public String get(ValuesA instance) {
        return ((ValuesACodegen) instance).a;
      }
      public String getName() {
        return "a";
      }
    };
    protected static final Shim<ValuesA, String> b = new Shim<ValuesA, String>() {
      public void set(ValuesA instance, String b) {
        ((ValuesACodegen) instance).b = b;
      }
      public String get(ValuesA instance) {
        return ((ValuesACodegen) instance).b;
      }
      public String getName() {
        return "b";
      }
    };
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
    protected static final Shim<ValuesA, Integer> j = new Shim<ValuesA, Integer>() {
      public void set(ValuesA instance, Integer j) {
        ((ValuesACodegen) instance).j = j;
      }
      public Integer get(ValuesA instance) {
        return ((ValuesACodegen) instance).j;
      }
      public String getName() {
        return "j";
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
    public boolean hasA() {
      return this.contains("a");
    }
    public String getOriginalA() {
      return (java.lang.String) this.getOriginal("a");
    }
    public boolean hasB() {
      return this.contains("b");
    }
    public String getOriginalB() {
      return (java.lang.String) this.getOriginal("b");
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
    public boolean hasJ() {
      return this.contains("j");
    }
    public Integer getOriginalJ() {
      return (java.lang.Integer) this.getOriginal("j");
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
