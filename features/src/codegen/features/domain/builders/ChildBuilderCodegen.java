package features.domain.builders;

import features.domain.Child;
import features.domain.Parent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ChildBuilderCodegen extends AbstractBuilder<Child> {

    public ChildBuilderCodegen(Child instance) {
        super(instance);
    }

    public ChildBuilder id(Integer id) {
        get().setId(id);
        return (ChildBuilder) this;
    }

    public ChildBuilder name(String name) {
        get().setName(name);
        return (ChildBuilder) this;
    }

    public ChildBuilder parent(Parent parent) {
        get().setParent(parent);
        return (ChildBuilder) this;
    }

    public Child get() {
        return (features.domain.Child) super.get();
    }

}
