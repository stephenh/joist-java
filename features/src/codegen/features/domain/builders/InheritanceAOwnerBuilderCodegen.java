package features.domain.builders;

import features.domain.InheritanceAOwner;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceAOwnerBuilderCodegen extends AbstractBuilder<InheritanceAOwner> {

    public InheritanceAOwnerBuilderCodegen(InheritanceAOwner instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public InheritanceAOwnerBuilder id(Long id) {
        get().setId(id);
        return (InheritanceAOwnerBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public InheritanceAOwnerBuilder name(String name) {
        get().setName(name);
        return (InheritanceAOwnerBuilder) this;
    }

    public InheritanceAOwnerBuilder with(String name) {
        get().setName(name);
        return (InheritanceAOwnerBuilder) this;
    }

    public InheritanceAOwner get() {
        return (features.domain.InheritanceAOwner) super.get();
    }

}
