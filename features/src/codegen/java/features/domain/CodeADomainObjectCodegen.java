package features.domain;

import features.domain.queries.CodeADomainObjectQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyCodeHolder;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class CodeADomainObjectCodegen extends AbstractDomainObject {

  public static final CodeADomainObjectQueries queries;
  private Long id = null;
  private String name = null;
  private Long version = null;
  private final ForeignKeyCodeHolder<CodeAColor> codeAColor = new ForeignKeyCodeHolder<CodeAColor>(CodeAColor.class);
  private final ForeignKeyCodeHolder<CodeASize> codeASize = new ForeignKeyCodeHolder<CodeASize>(CodeASize.class);
  protected Changed changed;

  static {
    Aliases.codeADomainObject();
    queries = new CodeADomainObjectQueries();
  }

  protected CodeADomainObjectCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<CodeADomainObject>(Shims.name));
    this.addRule(new MaxLength<CodeADomainObject>(Shims.name, 100));
    this.addRule(new NotEmpty<CodeADomainObject>(Shims.name));
    this.addRule(new NotNull<CodeADomainObject>(Shims.codeAColorId));
    this.addRule(new NotNull<CodeADomainObject>(Shims.codeASizeId));
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

  public CodeAColor getCodeAColor() {
    return this.codeAColor.get();
  }

  public void setCodeAColor(CodeAColor codeAColor) {
    this.setCodeAColorWithoutPercolation(codeAColor);
  }

  protected void setCodeAColorWithoutPercolation(CodeAColor codeAColor) {
    this.getChanged().record("codeAColor", this.codeAColor.get(), codeAColor);
    this.codeAColor.set(codeAColor);
  }

  protected void defaultCodeAColor(CodeAColor codeAColor) {
    this.codeAColor.set(codeAColor);
  }

  public boolean isBlue() {
    return getCodeAColor() == CodeAColor.BLUE;
  }

  public boolean isGreen() {
    return getCodeAColor() == CodeAColor.GREEN;
  }

  public CodeASize getCodeASize() {
    return this.codeASize.get();
  }

  public void setCodeASize(CodeASize codeASize) {
    this.setCodeASizeWithoutPercolation(codeASize);
  }

  protected void setCodeASizeWithoutPercolation(CodeASize codeASize) {
    this.getChanged().record("codeASize", this.codeASize.get(), codeASize);
    this.codeASize.set(codeASize);
  }

  protected void defaultCodeASize(CodeASize codeASize) {
    this.codeASize.set(codeASize);
  }

  public boolean isOne() {
    return getCodeASize() == CodeASize.ONE;
  }

  public boolean isTwo() {
    return getCodeASize() == CodeASize.TWO;
  }

  public CodeADomainObjectChanged getChanged() {
    if (this.changed == null) {
      this.changed = new CodeADomainObjectChanged((CodeADomainObject) this);
    }
    return (CodeADomainObjectChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setCodeAColor(null);
    this.setCodeASize(null);
  }

  static class Shims {
    protected static final Shim<CodeADomainObject, Long> id = new Shim<CodeADomainObject, Long>() {
      public void set(CodeADomainObject instance, Long id) {
        ((CodeADomainObjectCodegen) instance).id = id;
      }
      public Long get(CodeADomainObject instance) {
        return ((CodeADomainObjectCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<CodeADomainObject, String> name = new Shim<CodeADomainObject, String>() {
      public void set(CodeADomainObject instance, String name) {
        ((CodeADomainObjectCodegen) instance).name = name;
      }
      public String get(CodeADomainObject instance) {
        return ((CodeADomainObjectCodegen) instance).name;
      }
      public String getName() {
        return "name";
      }
    };
    protected static final Shim<CodeADomainObject, Long> version = new Shim<CodeADomainObject, Long>() {
      public void set(CodeADomainObject instance, Long version) {
        ((CodeADomainObjectCodegen) instance).version = version;
      }
      public Long get(CodeADomainObject instance) {
        return ((CodeADomainObjectCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
    protected static final Shim<CodeADomainObject, Long> codeAColorId = new Shim<CodeADomainObject, Long>() {
      public void set(CodeADomainObject instance, Long codeAColorId) {
        ((CodeADomainObjectCodegen) instance).codeAColor.setId(codeAColorId);
      }
      public Long get(CodeADomainObject instance) {
        return ((CodeADomainObjectCodegen) instance).codeAColor.getId();
      }
      public String getName() {
        return "codeAColor";
      }
    };
    protected static final Shim<CodeADomainObject, Long> codeASizeId = new Shim<CodeADomainObject, Long>() {
      public void set(CodeADomainObject instance, Long codeASizeId) {
        ((CodeADomainObjectCodegen) instance).codeASize.setId(codeASizeId);
      }
      public Long get(CodeADomainObject instance) {
        return ((CodeADomainObjectCodegen) instance).codeASize.getId();
      }
      public String getName() {
        return "codeASize";
      }
    };
  }

  public static class CodeADomainObjectChanged extends AbstractChanged {
    public CodeADomainObjectChanged(CodeADomainObject instance) {
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
    public boolean hasCodeAColor() {
      return this.contains("codeAColor");
    }
    public CodeAColor getOriginalCodeAColor() {
      return (CodeAColor) this.getOriginal("codeAColor");
    }
    public boolean hasCodeASize() {
      return this.contains("codeASize");
    }
    public CodeASize getOriginalCodeASize() {
      return (CodeASize) this.getOriginal("codeASize");
    }
  }

}
