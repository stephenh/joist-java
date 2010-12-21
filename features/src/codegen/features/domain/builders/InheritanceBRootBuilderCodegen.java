package features.domain.builders;

import features.domain.InheritanceBRoot;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceBRootBuilderCodegen extends AbstractBuilder<InheritanceBRoot> {

    public InheritanceBRootBuilderCodegen(InheritanceBRoot instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public InheritanceBRootBuilder id(Long id) {
        get().setId(id);
        return (InheritanceBRootBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public InheritanceBRootBuilder name(String name) {
        get().setName(name);
        return (InheritanceBRootBuilder) this;
    }

    public InheritanceBRootBuilder with(String name) {
        get().setName(name);
        return (InheritanceBRootBuilder) this;
    }

    public InheritanceBRoot get() {
        return (features.domain.InheritanceBRoot) super.get();
    }

}
