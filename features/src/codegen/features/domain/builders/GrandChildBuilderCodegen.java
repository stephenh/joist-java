package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class GrandChildBuilderCodegen extends AbstractBuilder<GrandChild> {

    public GrandChildBuilderCodegen(GrandChild instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public GrandChildBuilder id(Long id) {
        get().setId(id);
        return (GrandChildBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public GrandChildBuilder name(String name) {
        get().setName(name);
        return (GrandChildBuilder) this;
    }

    public GrandChildBuilder with(String name) {
        get().setName(name);
        return (GrandChildBuilder) this;
    }

    public ChildBuilder child() {
        if (get().getChild() == null) {
            return null;
        }
        return Builders.existing(get().getChild());
    }

    public GrandChildBuilder child(Child child) {
        get().setChild(child);
        return (GrandChildBuilder) this;
    }

    public GrandChildBuilder with(Child child) {
        get().setChild(child);
        return (GrandChildBuilder) this;
    }

    public GrandChildBuilder child(ChildBuilder child) {
        get().setChild(child.get());
        return (GrandChildBuilder) this;
    }

    public GrandChildBuilder with(ChildBuilder child) {
        get().setChild(child.get());
        return (GrandChildBuilder) this;
    }

    public GrandChild get() {
        return (features.domain.GrandChild) super.get();
    }

}
