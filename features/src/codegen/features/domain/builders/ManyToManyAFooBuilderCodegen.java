package features.domain.builders;

import features.domain.ManyToManyAFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyAFooBuilderCodegen extends AbstractBuilder<ManyToManyAFoo> {

    public ManyToManyAFooBuilderCodegen(ManyToManyAFoo instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ManyToManyAFooBuilder id(Long id) {
        get().setId(id);
        return (ManyToManyAFooBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ManyToManyAFooBuilder name(String name) {
        get().setName(name);
        return (ManyToManyAFooBuilder) this;
    }

    public ManyToManyAFooBuilder with(String name) {
        get().setName(name);
        return (ManyToManyAFooBuilder) this;
    }

    public ManyToManyAFoo get() {
        return (features.domain.ManyToManyAFoo) super.get();
    }

}
