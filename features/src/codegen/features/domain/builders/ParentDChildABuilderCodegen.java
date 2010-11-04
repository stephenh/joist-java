package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildA;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDChildABuilderCodegen extends AbstractBuilder<ParentDChildA> {

    public ParentDChildABuilderCodegen(ParentDChildA instance) {
        super(instance);
    }

    public ParentDChildABuilder id(Integer id) {
        get().setId(id);
        return (ParentDChildABuilder) this;
    }

    public ParentDChildABuilder name(String name) {
        get().setName(name);
        return (ParentDChildABuilder) this;
    }

    public ParentDChildABuilder with(String name) {
        get().setName(name);
        return (ParentDChildABuilder) this;
    }

    public ParentDChildABuilder parentD(ParentD parentD) {
        get().setParentD(parentD);
        return (ParentDChildABuilder) this;
    }

    public ParentDChildABuilder with(ParentD parentD) {
        get().setParentD(parentD);
        return (ParentDChildABuilder) this;
    }

    public ParentDChildABuilder parentD(ParentDBuilder parentD) {
        get().setParentD(parentD.get());
        return (ParentDChildABuilder) this;
    }

    public ParentDChildABuilder with(ParentDBuilder parentD) {
        get().setParentD(parentD.get());
        return (ParentDChildABuilder) this;
    }

    public ParentDChildA get() {
        return (features.domain.ParentDChildA) super.get();
    }

}
