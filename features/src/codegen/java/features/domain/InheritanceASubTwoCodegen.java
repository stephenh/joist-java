package features.domain;

import features.domain.queries.InheritanceASubTwoQueries;
import joist.domain.Shim;
import joist.domain.orm.ForeignKeyHolder;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceASubTwoCodegen extends InheritanceABase {

  public static final InheritanceASubTwoQueries queries;
  private String two = null;
  private final ForeignKeyHolder<InheritanceASubTwo, InheritanceAThing> inheritanceAThing = new ForeignKeyHolder<InheritanceASubTwo, InheritanceAThing>(InheritanceASubTwo.class, InheritanceAThing.class, Aliases.inheritanceAThing(), Aliases.inheritanceASubTwo().inheritanceAThing);

  static {
    Aliases.inheritanceASubTwo();
    queries = new InheritanceASubTwoQueries();
  }

  protected InheritanceASubTwoCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceASubTwo>(Shims.two));
    this.addRule(new MaxLength<InheritanceASubTwo>(Shims.two, 100));
    this.addRule(new NotEmpty<InheritanceASubTwo>(Shims.two));
  }

  public String getTwo() {
    return this.two;
  }

  public void setTwo(String two) {
    this.getChanged().record("two", this.two, two);
    this.two = two;
  }

  protected void defaultTwo(String two) {
    this.two = two;
  }

  public InheritanceAThing getInheritanceAThing() {
    return this.inheritanceAThing.get();
  }

  public void setInheritanceAThing(InheritanceAThing inheritanceAThing) {
    if (inheritanceAThing == this.getInheritanceAThing()) {
      return;
    }
    if (this.inheritanceAThing.get() != null) {
      this.inheritanceAThing.get().removeInheritanceASubTwoWithoutPercolation((InheritanceASubTwo) this);
    }
    this.setInheritanceAThingWithoutPercolation(inheritanceAThing);
    if (this.inheritanceAThing.get() != null) {
      this.inheritanceAThing.get().addInheritanceASubTwoWithoutPercolation((InheritanceASubTwo) this);
    }
  }

  protected void setInheritanceAThingWithoutPercolation(InheritanceAThing inheritanceAThing) {
    this.getChanged().record("inheritanceAThing", this.inheritanceAThing.get(), inheritanceAThing);
    this.inheritanceAThing.set(inheritanceAThing);
  }

  public InheritanceASubTwoChanged getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceASubTwoChanged((InheritanceASubTwo) this);
    }
    return (InheritanceASubTwoChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
    this.setInheritanceAThing(null);
  }

  static class Shims {
    protected static final Shim<InheritanceASubTwo, String> two = new Shim<InheritanceASubTwo, String>() {
      public void set(InheritanceASubTwo instance, String two) {
        ((InheritanceASubTwoCodegen) instance).two = two;
      }
      public String get(InheritanceASubTwo instance) {
        return ((InheritanceASubTwoCodegen) instance).two;
      }
      public String getName() {
        return "two";
      }
    };
    protected static final Shim<InheritanceASubTwo, Long> inheritanceAThingId = new Shim<InheritanceASubTwo, Long>() {
      public void set(InheritanceASubTwo instance, Long inheritanceAThingId) {
        ((InheritanceASubTwoCodegen) instance).inheritanceAThing.setId(inheritanceAThingId);
      }
      public Long get(InheritanceASubTwo instance) {
        return ((InheritanceASubTwoCodegen) instance).inheritanceAThing.getId();
      }
      public String getName() {
        return "inheritanceAThing";
      }
    };
  }

  public static class InheritanceASubTwoChanged extends InheritanceABaseChanged {
    public InheritanceASubTwoChanged(InheritanceASubTwo instance) {
      super(instance);
    }
    public boolean hasTwo() {
      return this.contains("two");
    }
    public String getOriginalTwo() {
      return (java.lang.String) this.getOriginal("two");
    }
    public boolean hasInheritanceAThing() {
      return this.contains("inheritanceAThing");
    }
    public InheritanceAThing getOriginalInheritanceAThing() {
      return (InheritanceAThing) this.getOriginal("inheritanceAThing");
    }
  }

}
