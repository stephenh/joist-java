package features.domain.builders;

import features.domain.ParentBParent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentBParentBuilderCodegen extends AbstractBuilder<ParentBParent> {

    public ParentBParentBuilderCodegen(ParentBParent instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ParentBParentBuilder id(Long id) {
        get().setId(id);
        return (ParentBParentBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ParentBParentBuilder name(String name) {
        get().setName(name);
        return (ParentBParentBuilder) this;
    }

    public ParentBParentBuilder with(String name) {
        get().setName(name);
        return (ParentBParentBuilder) this;
    }

    public ParentBParent get() {
        return (features.domain.ParentBParent) super.get();
    }

}
