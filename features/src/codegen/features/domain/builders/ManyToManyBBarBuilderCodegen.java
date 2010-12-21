package features.domain.builders;

import features.domain.ManyToManyBBar;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBBarBuilderCodegen extends AbstractBuilder<ManyToManyBBar> {

    public ManyToManyBBarBuilderCodegen(ManyToManyBBar instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ManyToManyBBarBuilder id(Long id) {
        get().setId(id);
        return (ManyToManyBBarBuilder) this;
    }

    public String name() {
        return get().getName();
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
