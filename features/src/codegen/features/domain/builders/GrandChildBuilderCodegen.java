package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class GrandChildBuilderCodegen extends AbstractBuilder<GrandChild> {

    public GrandChildBuilderCodegen(GrandChild instance) {
        super(instance);
    }

    public GrandChildBuilder id(Integer id) {
        get().setId(id);
        return (GrandChildBuilder) this;
    }

    public GrandChildBuilder name(String name) {
        get().setName(name);
        return (GrandChildBuilder) this;
    }

    public GrandChildBuilder child(Child child) {
        get().setChild(child);
        return (GrandChildBuilder) this;
    }

    public GrandChild get() {
        return (features.domain.GrandChild) super.get();
    }

}
