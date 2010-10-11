package features.domain.builders;

import features.domain.OneToOneAFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneAFooBuilderCodegen extends AbstractBuilder<OneToOneAFoo> {

    public OneToOneAFooBuilderCodegen(OneToOneAFoo instance) {
        super(instance);
    }

    public OneToOneAFooBuilder id(Integer id) {
        get().setId(id);
        return (OneToOneAFooBuilder) this;
    }

    public OneToOneAFooBuilder name(String name) {
        get().setName(name);
        return (OneToOneAFooBuilder) this;
    }

    public OneToOneAFoo get() {
        return (features.domain.OneToOneAFoo) super.get();
    }

}
