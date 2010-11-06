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

    public Integer id() {
        return get().getId();
    }

    public ManyToManyBFooToBarBuilder id(Integer id) {
        get().setId(id);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooBuilder blue() {
        return Builders.existing(get().getBlue());
    }

    public ManyToManyBFooToBarBuilder blue(ManyToManyBFoo blue) {
        get().setBlue(blue);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder with(ManyToManyBFoo blue) {
        get().setBlue(blue);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder blue(ManyToManyBFooBuilder blue) {
        get().setBlue(blue.get());
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder with(ManyToManyBFooBuilder blue) {
        get().setBlue(blue.get());
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBBarBuilder green() {
        return Builders.existing(get().getGreen());
    }

    public ManyToManyBFooToBarBuilder green(ManyToManyBBar green) {
        get().setGreen(green);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder with(ManyToManyBBar green) {
        get().setGreen(green);
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder green(ManyToManyBBarBuilder green) {
        get().setGreen(green.get());
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBarBuilder with(ManyToManyBBarBuilder green) {
        get().setGreen(green.get());
        return (ManyToManyBFooToBarBuilder) this;
    }

    public ManyToManyBFooToBar get() {
        return (features.domain.ManyToManyBFooToBar) super.get();
    }

}
