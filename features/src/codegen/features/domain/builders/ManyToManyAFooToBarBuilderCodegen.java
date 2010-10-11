package features.domain.builders;

import features.domain.ManyToManyABar;
import features.domain.ManyToManyAFoo;
import features.domain.ManyToManyAFooToBar;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyAFooToBarBuilderCodegen extends AbstractBuilder<ManyToManyAFooToBar> {

    public ManyToManyAFooToBarBuilderCodegen(ManyToManyAFooToBar instance) {
        super(instance);
    }

    public ManyToManyAFooToBarBuilder id(Integer id) {
        get().setId(id);
        return (ManyToManyAFooToBarBuilder) this;
    }

    public ManyToManyAFooToBarBuilder manyToManyABar(ManyToManyABar manyToManyABar) {
        get().setManyToManyABar(manyToManyABar);
        return (ManyToManyAFooToBarBuilder) this;
    }

    public ManyToManyAFooToBarBuilder manyToManyAFoo(ManyToManyAFoo manyToManyAFoo) {
        get().setManyToManyAFoo(manyToManyAFoo);
        return (ManyToManyAFooToBarBuilder) this;
    }

    public ManyToManyAFooToBar get() {
        return (features.domain.ManyToManyAFooToBar) super.get();
    }

}
