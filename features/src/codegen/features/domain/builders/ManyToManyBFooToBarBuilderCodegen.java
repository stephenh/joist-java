package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBFooToBarBuilderCodegen extends AbstractBuilder<ManyToManyBFooToBar> {

    public ManyToManyBFooToBarBuilderCodegen(ManyToManyBFooToBar instance) {
        super(instance);
    }

    public ManyToManyBFooToBarBuilder id(Integer id) {
        get().setId(id);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder blue(ManyToManyBFoo blue) {
        get().setBlue(blue);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder green(ManyToManyBBar green) {
        get().setGreen(green);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBar get() {
        return (features.domain.ManyToManyBFooToBar) super.get();
    }

}
