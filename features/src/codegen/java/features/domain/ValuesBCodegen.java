package features.domain;

import com.domainlanguage.time.TimePoint;
import features.domain.queries.ValuesBQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ValuesBCodegen extends AbstractDomainObject {

  public static final ValuesBQueries queries;
  private Long id = null;
  private String name = null;
  private TimePoint start = null;
  private Long version = null;
  protected Changed changed;

  static {
    Aliases.valuesB();
    queries = new ValuesBQueries();
  }

  protected ValuesBCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ValuesB>(Shims.name));
    this.addRule(new MaxLength<ValuesB>(Shims.name, 100));
    this.addRule(new NotEmpty<ValuesB>(Shims.name));
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

  public TimePoint getStart() {
    return this.start;
  }

  public void setStart(TimePoint start) {
    this.getChanged().record("start", this.start, start);
    this.start = start;
  }

  protected void defaultStart(TimePoint start) {
    this.start = start;
  }

  public Long getVersion() {
    return this.version;
  }

  public ValuesBChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ValuesBChanged((ValuesB) this);
    }
    return (ValuesBChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<ValuesB, Long> id = new Shim<ValuesB, Long>() {
      public void set(ValuesB instance, Long id) {
        ((ValuesBCodegen) instance).id = id;
      }
      public Long get(ValuesB instance) {
        return ((ValuesBCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ValuesB, String> name = new Shim<ValuesB, String>() {
      public void set(ValuesB instance, String name) {
        ((ValuesBCodegen) instance).name = name;
      }
      public String get(ValuesB instance) {
        return ((ValuesBCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ValuesB, TimePoint> start = new Shim<ValuesB, TimePoint>() {
      public void set(ValuesB instance, TimePoint start) {
        ((ValuesBCodegen) instance).start = start;
      }
      public TimePoint get(ValuesB instance) {
        return ((ValuesBCodegen) instance).start;
      }
      public String getName() {
        return "start";
      }
    };
    protected static final Shim<ValuesB, Long> version = new Shim<ValuesB, Long>() {
      public void set(ValuesB instance, Long version) {
        ((ValuesBCodegen) instance).version = version;
      }
      public Long get(ValuesB instance) {
        return ((ValuesBCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  public static class ValuesBChanged extends AbstractChanged {
    public ValuesBChanged(ValuesB instance) {
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
    public boolean hasStart() {
      return this.contains("start");
    }
    public TimePoint getOriginalStart() {
      return (com.domainlanguage.time.TimePoint) this.getOriginal("start");
    }
    public boolean hasVersion() {
      return this.contains("version");
    }
    public Long getOriginalVersion() {
      return (Long) this.getOriginal("version");
    }
  }

}
