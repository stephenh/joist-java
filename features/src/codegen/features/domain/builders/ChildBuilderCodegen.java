package features.domain.builders;

import features.domain.Child;
import features.domain.Parent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ChildBuilderCodegen extends AbstractBuilder<Child> {

    public ChildBuilderCodegen(Child instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ChildBuilder id(Long id) {
        get().setId(id);
        return (ChildBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ChildBuilder name(String name) {
        get().setName(name);
        return (ChildBuilder) this;
    }

    public ChildBuilder with(String name) {
        get().setName(name);
        return (ChildBuilder) this;
    }

    public ParentBuilder parent() {
        return Builders.existing(get().getParent());
    }

    public ChildBuilder parent(Parent parent) {
        get().setParent(parent);
        return (ChildBuilder) this;
    }

    public ChildBuilder with(Parent parent) {
        get().setParent(parent);
        return (ChildBuilder) this;
    }

    public ChildBuilder parent(ParentBuilder parent) {
        get().setParent(parent.get());
        return (ChildBuilder) this;
    }

    public ChildBuilder with(ParentBuilder parent) {
        get().setParent(parent.get());
        return (ChildBuilder) this;
    }

    public Child get() {
        return (features.domain.Child) super.get();
    }

}
