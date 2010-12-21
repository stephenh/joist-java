package features.domain.builders;

import features.domain.ParentCFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentCFooBuilderCodegen extends AbstractBuilder<ParentCFoo> {

    public ParentCFooBuilderCodegen(ParentCFoo instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ParentCFooBuilder id(Long id) {
        get().setId(id);
        return (ParentCFooBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ParentCFooBuilder name(String name) {
        get().setName(name);
        return (ParentCFooBuilder) this;
    }

    public ParentCFooBuilder with(String name) {
        get().setName(name);
        return (ParentCFooBuilder) this;
    }

    public ParentCFoo get() {
        return (features.domain.ParentCFoo) super.get();
    }

}
