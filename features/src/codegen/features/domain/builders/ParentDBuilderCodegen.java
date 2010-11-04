package features.domain.builders;

import features.domain.ParentD;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDBuilderCodegen extends AbstractBuilder<ParentD> {

    public ParentDBuilderCodegen(ParentD instance) {
        super(instance);
    }

    public ParentDBuilder id(Integer id) {
        get().setId(id);
        return (ParentDBuilder) this;
    }

    public ParentDBuilder name(String name) {
        get().setName(name);
        return (ParentDBuilder) this;
    }

    public ParentDBuilder with(String name) {
        get().setName(name);
        return (ParentDBuilder) this;
    }

    public ParentD get() {
        return (features.domain.ParentD) super.get();
    }

}
