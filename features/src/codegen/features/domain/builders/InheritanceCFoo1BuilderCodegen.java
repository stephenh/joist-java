package features.domain.builders;

import features.domain.InheritanceCFoo1;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceCFoo1BuilderCodegen extends InheritanceCBuilder {

  public InheritanceCFoo1BuilderCodegen(InheritanceCFoo1 instance) {
    super(instance);
  }

  public String foo() {
    return get().getFoo();
  }

  public InheritanceCFoo1Builder foo(String foo) {
    get().setFoo(foo);
    return (InheritanceCFoo1Builder) this;
  }

  @Override
  public InheritanceCFoo1Builder defaults() {
    if (foo() == null) {
      foo("foo");
    }
    return (InheritanceCFoo1Builder) super.defaults();
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

}
