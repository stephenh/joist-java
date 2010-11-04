package features.domain.builders;

import features.domain.ManyToManyABar;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyABarBuilderCodegen extends AbstractBuilder<ManyToManyABar> {

    public ManyToManyABarBuilderCodegen(ManyToManyABar instance) {
        super(instance);
    }

    public ManyToManyABarBuilder id(Integer id) {
        get().setId(id);
        return (ManyToManyABarBuilder) this;
    }

    public ManyToManyABarBuilder name(String name) {
        get().setName(name);
        return (ManyToManyABarBuilder) this;
    }

    public ManyToManyABarBuilder with(String name) {
        get().setName(name);
        return (ManyToManyABarBuilder) this;
    }

    public ManyToManyABar get() {
        return (features.domain.ManyToManyABar) super.get();
    }

}
