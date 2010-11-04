package features.domain.builders;

import features.domain.ParentBParent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentBParentBuilderCodegen extends AbstractBuilder<ParentBParent> {

    public ParentBParentBuilderCodegen(ParentBParent instance) {
        super(instance);
    }

    public ParentBParentBuilder id(Integer id) {
        get().setId(id);
        return (ParentBParentBuilder) this;
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
