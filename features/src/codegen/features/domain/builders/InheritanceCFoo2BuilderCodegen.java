package features.domain.builders;

import features.domain.InheritanceCFoo2;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceCFoo2BuilderCodegen extends InheritanceCBuilder {

  public InheritanceCFoo2BuilderCodegen(InheritanceCFoo2 instance) {
    super(instance);
  }

  public String foo() {
    return get().getFoo();
  }

  public InheritanceCFoo2Builder foo(String foo) {
    get().setFoo(foo);
    return (InheritanceCFoo2Builder) this;
  }

  public InheritanceCFoo2Builder with(String foo) {
    return foo(foo);
  }

  @Override
  public InheritanceCFoo2Builder defaults() {
    if (foo() == null) {
      foo("foo");
    }
    return (InheritanceCFoo2Builder) super.defaults();
  }

  public InheritanceCFoo2 get() {
    return (features.domain.InheritanceCFoo2) super.get();
  }

  @Override
  public InheritanceCFoo2Builder ensureSaved() {
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (InheritanceCFoo2Builder) this;
  }

}
