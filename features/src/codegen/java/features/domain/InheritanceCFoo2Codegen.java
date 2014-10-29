package features.domain;

import features.domain.queries.InheritanceCFoo2Queries;
import joist.domain.Shim;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class InheritanceCFoo2Codegen extends InheritanceC {

  public static final InheritanceCFoo2Queries queries;
  private String foo = null;

  static {
    Aliases.inheritanceCFoo2();
    queries = new InheritanceCFoo2Queries();
  }

  protected InheritanceCFoo2Codegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new NotNull<InheritanceCFoo2>(Shims.foo));
    this.addRule(new MaxLength<InheritanceCFoo2>(Shims.foo, 100));
    this.addRule(new NotEmpty<InheritanceCFoo2>(Shims.foo));
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

  public InheritanceCFoo2Changed getChanged() {
    if (this.changed == null) {
      this.changed = new InheritanceCFoo2Changed((InheritanceCFoo2) this);
    }
    return (InheritanceCFoo2Changed) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<InheritanceCFoo2, String> foo = new Shim<InheritanceCFoo2, String>() {
      public void set(InheritanceCFoo2 instance, String foo) {
        ((InheritanceCFoo2Codegen) instance).foo = foo;
      }
      public String get(InheritanceCFoo2 instance) {
        return ((InheritanceCFoo2Codegen) instance).foo;
      }
      public String getName() {
        return "foo";
      }
    };
  }

  public static class InheritanceCFoo2Changed extends InheritanceCChanged {
    public InheritanceCFoo2Changed(InheritanceCFoo2 instance) {
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
