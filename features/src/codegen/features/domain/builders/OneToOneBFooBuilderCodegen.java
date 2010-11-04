package features.domain.builders;

import features.domain.OneToOneBFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneBFooBuilderCodegen extends AbstractBuilder<OneToOneBFoo> {

    public OneToOneBFooBuilderCodegen(OneToOneBFoo instance) {
        super(instance);
    }

    public OneToOneBFooBuilder id(Integer id) {
        get().setId(id);
        return (OneToOneBFooBuilder) this;
    }

    public OneToOneBFooBuilder name(String name) {
        get().setName(name);
        return (OneToOneBFooBuilder) this;
    }

    public OneToOneBFooBuilder with(String name) {
        get().setName(name);
        return (OneToOneBFooBuilder) this;
    }

    public OneToOneBFoo get() {
        return (features.domain.OneToOneBFoo) super.get();
    }

}
