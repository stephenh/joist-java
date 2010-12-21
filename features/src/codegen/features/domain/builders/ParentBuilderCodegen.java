package features.domain.builders;

import features.domain.Parent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentBuilderCodegen extends AbstractBuilder<Parent> {

    public ParentBuilderCodegen(Parent instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ParentBuilder id(Long id) {
        get().setId(id);
        return (ParentBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ParentBuilder name(String name) {
        get().setName(name);
        return (ParentBuilder) this;
    }

    public ParentBuilder with(String name) {
        get().setName(name);
        return (ParentBuilder) this;
    }

    public Parent get() {
        return (features.domain.Parent) super.get();
    }

}
