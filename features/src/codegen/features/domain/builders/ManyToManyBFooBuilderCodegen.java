package features.domain.builders;

import features.domain.ManyToManyBFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBFooBuilderCodegen extends AbstractBuilder<ManyToManyBFoo> {

    public ManyToManyBFooBuilderCodegen(ManyToManyBFoo instance) {
        super(instance);
    }

    public ManyToManyBFooBuilder id(Integer id) {
        get().setId(id);
        return (ManyToManyBFooBuilder) this;
    }

    public ManyToManyBFooBuilder name(String name) {
        get().setName(name);
        return (ManyToManyBFooBuilder) this;
    }

    public ManyToManyBFoo get() {
        return (features.domain.ManyToManyBFoo) super.get();
    }

}
