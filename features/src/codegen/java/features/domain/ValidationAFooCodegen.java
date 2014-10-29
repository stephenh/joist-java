package features.domain;

import features.domain.queries.ValidationAFooQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class ValidationAFooCodegen extends AbstractDomainObject {

  public static final ValidationAFooQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  protected Changed changed;

  static {
    Aliases.validationAFoo();
    queries = new ValidationAFooQueries();
  }

  protected ValidationAFooCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<ValidationAFoo>(Shims.name));
    this.addRule(new MaxLength<ValidationAFoo>(Shims.name, 100));
    this.addRule(new NotEmpty<ValidationAFoo>(Shims.name));
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

  public ValidationAFooChanged getChanged() {
    if (this.changed == null) {
      this.changed = new ValidationAFooChanged((ValidationAFoo) this);
    }
    return (ValidationAFooChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<ValidationAFoo, Long> id = new Shim<ValidationAFoo, Long>() {
      public void set(ValidationAFoo instance, Long id) {
        ((ValidationAFooCodegen) instance).id = id;
      }
      public Long get(ValidationAFoo instance) {
        return ((ValidationAFooCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<ValidationAFoo, String> name = new Shim<ValidationAFoo, String>() {
      public void set(ValidationAFoo instance, String name) {
        ((ValidationAFooCodegen) instance).name = name;
      }
      public String get(ValidationAFoo instance) {
        return ((ValidationAFooCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<ValidationAFoo, Long> version = new Shim<ValidationAFoo, Long>() {
      public void set(ValidationAFoo instance, Long version) {
        ((ValidationAFooCodegen) instance).version = version;
      }
      public Long get(ValidationAFoo instance) {
        return ((ValidationAFooCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  public static class ValidationAFooChanged extends AbstractChanged {
    public ValidationAFooChanged(ValidationAFoo instance) {
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
  }

}
