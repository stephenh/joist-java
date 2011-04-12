package features.domain.builders;

import features.domain.ParentBChildBar;
import features.domain.ParentBParent;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentBChildBarBuilderCodegen extends AbstractBuilder<ParentBChildBar> {

    public ParentBChildBarBuilderCodegen(ParentBChildBar instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ParentBChildBarBuilder id(Long id) {
        get().setId(id);
        return (ParentBChildBarBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ParentBChildBarBuilder name(String name) {
        get().setName(name);
        return (ParentBChildBarBuilder) this;
    }

    public ParentBChildBarBuilder with(String name) {
        get().setName(name);
        return (ParentBChildBarBuilder) this;
    }

    public ParentBParentBuilder parentBParent() {
        if (get().getParentBParent() == null) {
            return null;
        }
        return Builders.existing(get().getParentBParent());
    }

    public ParentBChildBarBuilder parentBParent(ParentBParent parentBParent) {
        get().setParentBParent(parentBParent);
        return (ParentBChildBarBuilder) this;
    }

    public ParentBChildBarBuilder with(ParentBParent parentBParent) {
        get().setParentBParent(parentBParent);
        return (ParentBChildBarBuilder) this;
    }

    public ParentBChildBarBuilder parentBParent(ParentBParentBuilder parentBParent) {
        get().setParentBParent(parentBParent.get());
        return (ParentBChildBarBuilder) this;
    }

    public ParentBChildBarBuilder with(ParentBParentBuilder parentBParent) {
        get().setParentBParent(parentBParent.get());
        return (ParentBChildBarBuilder) this;
    }

    public ParentBChildBar get() {
        return (features.domain.ParentBChildBar) super.get();
    }

}
