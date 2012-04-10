package features.domain;

import features.domain.queries.InheritanceBMiddleQueries;
import joist.domain.Shim;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceBMiddleCodegen extends InheritanceBRoot {

  public static final InheritanceBMiddleQueries queries;
  private String middleName = null;

  static {
    Aliases.inheritanceBMiddle();
    queries = new InheritanceBMiddleQueries();
  }

  protected InheritanceBMiddleCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceBMiddle>(Shims.middleName));
    this.addRule(new MaxLength<InheritanceBMiddle>(Shims.middleName, 100));
    this.addRule(new NotEmpty<InheritanceBMiddle>(Shims.middleName));
  }

  public String getMiddleName() {
    return this.middleName;
  }

  public void setMiddleName(String middleName) {
    this.getChanged().record("middleName", this.middleName, middleName);
    this.middleName = middleName;
  }

  protected void defaultMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public InheritanceBMiddleChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceBMiddleChanged((InheritanceBMiddle) this);
    }
    return (InheritanceBMiddleChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<InheritanceBMiddle, String> middleName = new Shim<InheritanceBMiddle, String>() {
      public void set(InheritanceBMiddle instance, String middleName) {
        ((InheritanceBMiddleCodegen) instance).middleName = middleName;
      }
      public String get(InheritanceBMiddle instance) {
        return ((InheritanceBMiddleCodegen) instance).middleName;
      }
      public String getName() {
        return "middleName";
      }
    };
  }

  public static class InheritanceBMiddleChanged extends InheritanceBRootChanged {
    public InheritanceBMiddleChanged(InheritanceBMiddle instance) {
      super(instance);
    }
    public boolean hasMiddleName() {
      return this.contains("middleName");
    }
    public String getOriginalMiddleName() {
      return (java.lang.String) this.getOriginal("middleName");
    }
  }

}
