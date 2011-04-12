package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
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
        if (get().getParent() == null) {
            return null;
        }
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

    public List<GrandChildBuilder> grandChilds() {
        List<GrandChildBuilder> b = new ArrayList<GrandChildBuilder>();
        for (GrandChild e : get().getGrandChilds()) {
            b.add(Builders.existing(e));
        }
        return b;
    }

    public GrandChildBuilder grandChild(int i) {
        return Builders.existing(get().getGrandChilds().get(i));
    }

    public Child get() {
        return (features.domain.Child) super.get();
    }

}
