package features.domain.builders;

import features.domain.OneToOneABar;
import features.domain.OneToOneAFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneABarBuilderCodegen extends AbstractBuilder<OneToOneABar> {

    public OneToOneABarBuilderCodegen(OneToOneABar instance) {
        super(instance);
    }

    public OneToOneABarBuilder id(Integer id) {
        get().setId(id);
        return (OneToOneABarBuilder) this;
    }

    public OneToOneABarBuilder name(String name) {
        get().setName(name);
        return (OneToOneABarBuilder) this;
    }

    public OneToOneABarBuilder oneToOneAFoo(OneToOneAFoo oneToOneAFoo) {
        get().setOneToOneAFoo(oneToOneAFoo);
        return (OneToOneABarBuilder) this;
    }

    public OneToOneABar get() {
        return (features.domain.OneToOneABar) super.get();
    }

}
