package features.domain.builders;

import features.domain.InheritanceAThing;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceAThingBuilderCodegen extends AbstractBuilder<InheritanceAThing> {

    public InheritanceAThingBuilderCodegen(InheritanceAThing instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public InheritanceAThingBuilder id(Long id) {
        get().setId(id);
        return (InheritanceAThingBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public InheritanceAThingBuilder name(String name) {
        get().setName(name);
        return (InheritanceAThingBuilder) this;
    }

    public InheritanceAThingBuilder with(String name) {
        get().setName(name);
        return (InheritanceAThingBuilder) this;
    }

    public InheritanceAThing get() {
        return (features.domain.InheritanceAThing) super.get();
    }

}
