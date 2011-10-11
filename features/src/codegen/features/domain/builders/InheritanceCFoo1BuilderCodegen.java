package features.domain.builders;

import features.domain.InheritanceCFoo1;

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

  public InheritanceCFoo1Builder with(String foo) {
    get().setFoo(foo);
    return (InheritanceCFoo1Builder) this;
  }

  public InheritanceCFoo1 get() {
    return (features.domain.InheritanceCFoo1) super.get();
  }

}
