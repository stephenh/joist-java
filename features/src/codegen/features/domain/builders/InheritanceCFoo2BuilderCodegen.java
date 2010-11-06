package features.domain.builders;

import features.domain.InheritanceCFoo2;

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
        get().setFoo(foo);
        return (InheritanceCFoo2Builder) this;
    }

    public InheritanceCFoo2 get() {
        return (features.domain.InheritanceCFoo2) super.get();
    }

}
