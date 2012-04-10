package features.domain;

import features.domain.queries.InheritanceBBottomQueries;
import joist.domain.Shim;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceBBottomCodegen extends InheritanceBMiddle {

  public static final InheritanceBBottomQueries queries;
  private String bottomName = null;

  static {
    Aliases.inheritanceBBottom();
    queries = new InheritanceBBottomQueries();
  }

  protected InheritanceBBottomCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceBBottom>(Shims.bottomName));
    this.addRule(new MaxLength<InheritanceBBottom>(Shims.bottomName, 100));
    this.addRule(new NotEmpty<InheritanceBBottom>(Shims.bottomName));
  }

  public String getBottomName() {
    return this.bottomName;
  }

  public void setBottomName(String bottomName) {
    this.getChanged().record("bottomName", this.bottomName, bottomName);
    this.bottomName = bottomName;
  }

  protected void defaultBottomName(String bottomName) {
    this.bottomName = bottomName;
  }

  public InheritanceBBottomChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceBBottomChanged((InheritanceBBottom) this);
    }
    return (InheritanceBBottomChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<InheritanceBBottom, String> bottomName = new Shim<InheritanceBBottom, String>() {
      public void set(InheritanceBBottom instance, String bottomName) {
        ((InheritanceBBottomCodegen) instance).bottomName = bottomName;
      }
      public String get(InheritanceBBottom instance) {
        return ((InheritanceBBottomCodegen) instance).bottomName;
      }
      public String getName() {
        return "bottomName";
      }
    };
  }

  public static class InheritanceBBottomChanged extends InheritanceBMiddleChanged {
    public InheritanceBBottomChanged(InheritanceBBottom instance) {
      super(instance);
    }
    public boolean hasBottomName() {
      return this.contains("bottomName");
    }
    public String getOriginalBottomName() {
      return (java.lang.String) this.getOriginal("bottomName");
    }
  }

}
