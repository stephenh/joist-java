package features.domain.builders;

import features.domain.InheritanceCFoo1;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceCFoo1BuilderCodegen extends InheritanceCBuilder {

  public InheritanceCFoo1BuilderCodegen(InheritanceCFoo1 instance) {
    super(instance);
  }

  @Override
  public InheritanceCFoo1Builder defaults() {
    return (InheritanceCFoo1Builder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (foo() == null) {
      foo(defaultFoo());
    }
  }

  public String foo() {
    return get().getFoo();
  }

  public InheritanceCFoo1Builder foo(String foo) {
    get().setFoo(foo);
    return (InheritanceCFoo1Builder) this;
  }

  protected String defaultFoo() {
    return "foo";
  }

  public String name() {
    return get().getName();
  }

  public InheritanceCFoo1Builder name(String name) {
    get().setName(name);
    return (InheritanceCFoo1Builder) this;
  }

  public InheritanceCFoo1 get() {
    return (features.domain.InheritanceCFoo1) super.get();
  }

  @Override
  public InheritanceCFoo1Builder ensureSaved() {
    doEnsureSaved();
    return (InheritanceCFoo1Builder) this;
  }

  @Override
  public InheritanceCFoo1Builder use(AbstractBuilder<?> builder) {
    return (InheritanceCFoo1Builder) super.use(builder);
  }

}
