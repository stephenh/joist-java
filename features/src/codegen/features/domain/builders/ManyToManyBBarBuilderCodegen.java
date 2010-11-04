package features.domain.builders;

import features.domain.ManyToManyBBar;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBBarBuilderCodegen extends AbstractBuilder<ManyToManyBBar> {

    public ManyToManyBBarBuilderCodegen(ManyToManyBBar instance) {
        super(instance);
    }

    public ManyToManyBBarBuilder id(Integer id) {
        get().setId(id);
        return (ManyToManyBBarBuilder) this;
    }

    public ManyToManyBBarBuilder name(String name) {
        get().setName(name);
        return (ManyToManyBBarBuilder) this;
    }

    public ManyToManyBBarBuilder with(String name) {
        get().setName(name);
        return (ManyToManyBBarBuilder) this;
    }

    public ManyToManyBBar get() {
        return (features.domain.ManyToManyBBar) super.get();
    }

}
