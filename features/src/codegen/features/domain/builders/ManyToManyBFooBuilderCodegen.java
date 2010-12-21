package features.domain.builders;

import features.domain.ManyToManyBFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBFooBuilderCodegen extends AbstractBuilder<ManyToManyBFoo> {

    public ManyToManyBFooBuilderCodegen(ManyToManyBFoo instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ManyToManyBFooBuilder id(Long id) {
        get().setId(id);
        return (ManyToManyBFooBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ManyToManyBFooBuilder name(String name) {
        get().setName(name);
        return (ManyToManyBFooBuilder) this;
    }

    public ManyToManyBFooBuilder with(String name) {
        get().setName(name);
        return (ManyToManyBFooBuilder) this;
    }

    public ManyToManyBFoo get() {
        return (features.domain.ManyToManyBFoo) super.get();
    }

}
