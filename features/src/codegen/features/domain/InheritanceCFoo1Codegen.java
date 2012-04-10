package features.domain;

import features.domain.queries.InheritanceCFoo1Queries;
import joist.domain.Shim;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceCFoo1Codegen extends InheritanceC {

  public static final InheritanceCFoo1Queries queries;
  private String foo = null;

  static {
    Aliases.inheritanceCFoo1();
    queries = new InheritanceCFoo1Queries();
  }

  protected InheritanceCFoo1Codegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceCFoo1>(Shims.foo));
    this.addRule(new MaxLength<InheritanceCFoo1>(Shims.foo, 100));
    this.addRule(new NotEmpty<InheritanceCFoo1>(Shims.foo));
  }

  public String getFoo() {
    return this.foo;
  }

  public void setFoo(String foo) {
    this.getChanged().record("foo", this.foo, foo);
    this.foo = foo;
  }

  protected void defaultFoo(String foo) {
    this.foo = foo;
  }

  public InheritanceCFoo1Changed getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceCFoo1Changed((InheritanceCFoo1) this);
    }
    return (InheritanceCFoo1Changed) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<InheritanceCFoo1, String> foo = new Shim<InheritanceCFoo1, String>() {
      public void set(InheritanceCFoo1 instance, String foo) {
        ((InheritanceCFoo1Codegen) instance).foo = foo;
      }
      public String get(InheritanceCFoo1 instance) {
        return ((InheritanceCFoo1Codegen) instance).foo;
      }
      public String getName() {
        return "foo";
      }
    };
  }

  public static class InheritanceCFoo1Changed extends InheritanceCChanged {
    public InheritanceCFoo1Changed(InheritanceCFoo1 instance) {
      super(instance);
    }
    public boolean hasFoo() {
      return this.contains("foo");
    }
    public String getOriginalFoo() {
      return (java.lang.String) this.getOriginal("foo");
    }
  }

}
