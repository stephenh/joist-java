package features.domain.builders;

import features.domain.ParentBChildFoo;
import features.domain.ParentBParent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentBChildFooBuilderCodegen extends AbstractBuilder<ParentBChildFoo> {

    public ParentBChildFooBuilderCodegen(ParentBChildFoo instance) {
        super(instance);
    }

    public ParentBChildFooBuilder id(Integer id) {
        get().setId(id);
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFooBuilder name(String name) {
        get().setName(name);
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFooBuilder with(String name) {
        get().setName(name);
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFooBuilder parentBParent(ParentBParent parentBParent) {
        get().setParentBParent(parentBParent);
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFooBuilder with(ParentBParent parentBParent) {
        get().setParentBParent(parentBParent);
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFooBuilder parentBParent(ParentBParentBuilder parentBParent) {
        get().setParentBParent(parentBParent.get());
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFooBuilder with(ParentBParentBuilder parentBParent) {
        get().setParentBParent(parentBParent.get());
        return (ParentBChildFooBuilder) this;
    }

    public ParentBChildFoo get() {
        return (features.domain.ParentBChildFoo) super.get();
    }

}
