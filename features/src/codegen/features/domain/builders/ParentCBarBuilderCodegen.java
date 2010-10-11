package features.domain.builders;

import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentCBarBuilderCodegen extends AbstractBuilder<ParentCBar> {

    public ParentCBarBuilderCodegen(ParentCBar instance) {
        super(instance);
    }

    public ParentCBarBuilder id(Integer id) {
        get().setId(id);
        return (ParentCBarBuilder) this;
    }

    public ParentCBarBuilder name(String name) {
        get().setName(name);
        return (ParentCBarBuilder) this;
    }

    public ParentCBarBuilder firstParent(ParentCFoo firstParent) {
        get().setFirstParent(firstParent);
        return (ParentCBarBuilder) this;
    }

    public ParentCBarBuilder secondParent(ParentCFoo secondParent) {
        get().setSecondParent(secondParent);
        return (ParentCBarBuilder) this;
    }

    public ParentCBar get() {
        return (features.domain.ParentCBar) super.get();
    }

}
