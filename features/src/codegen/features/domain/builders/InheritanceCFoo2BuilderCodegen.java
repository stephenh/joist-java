package features.domain.builders;

import features.domain.InheritanceCFoo2;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceCFoo2BuilderCodegen extends InheritanceCBuilder {

  public InheritanceCFoo2BuilderCodegen(InheritanceCFoo2 instance) {
    super(instance);
  }

  @Override
  public InheritanceCFoo2Builder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (foo() == null) {
        foo(defaultFoo());
      }
      return (InheritanceCFoo2Builder) super.defaults();
    } finally {
      DefaultsContext.pop();
    }
  }

  public String foo() {
    return get().getFoo();
  }

  public InheritanceCFoo2Builder foo(String foo) {
    get().setFoo(foo);
    return (InheritanceCFoo2Builder) this;
  }

  protected String defaultFoo() {
    return "foo";
  }

  public String name() {
    return get().getName();
  }

  public InheritanceCFoo2Builder name(String name) {
    get().setName(name);
    return (InheritanceCFoo2Builder) this;
  }

  public InheritanceCFoo2 get() {
    return (features.domain.InheritanceCFoo2) super.get();
  }

  @Override
  public InheritanceCFoo2Builder ensureSaved() {
    doEnsureSaved();
    return (InheritanceCFoo2Builder) this;
  }

}
