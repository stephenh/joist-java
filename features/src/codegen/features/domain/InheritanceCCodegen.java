package features.domain;

import features.domain.queries.InheritanceCQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceCCodegen extends AbstractDomainObject {

  public static final InheritanceCQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  protected Changed changed;

  static {
    Aliases.inheritanceC();
    queries = new InheritanceCQueries();
  }

  protected InheritanceCCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceC>(Shims.name));
    this.addRule(new MaxLength<InheritanceC>(Shims.name, 100));
    this.addRule(new NotEmpty<InheritanceC>(Shims.name));
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

  public InheritanceCChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceCChanged((InheritanceC) this);
    }
    return (InheritanceCChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<InheritanceC, Long> id = new Shim<InheritanceC, Long>() {
      public void set(InheritanceC instance, Long id) {
        ((InheritanceCCodegen) instance).id = id;
      }
      public Long get(InheritanceC instance) {
        return ((InheritanceCCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<InheritanceC, String> name = new Shim<InheritanceC, String>() {
      public void set(InheritanceC instance, String name) {
        ((InheritanceCCodegen) instance).name = name;
      }
      public String get(InheritanceC instance) {
        return ((InheritanceCCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<InheritanceC, Long> version = new Shim<InheritanceC, Long>() {
      public void set(InheritanceC instance, Long version) {
        ((InheritanceCCodegen) instance).version = version;
      }
      public Long get(InheritanceC instance) {
        return ((InheritanceCCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  public static class InheritanceCChanged extends AbstractChanged {
    public InheritanceCChanged(InheritanceC instance) {
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
