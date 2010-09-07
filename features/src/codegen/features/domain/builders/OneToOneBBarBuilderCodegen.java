package features.domain.builders;

import features.domain.OneToOneBBar;
import features.domain.OneToOneBFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneBBarBuilderCodegen extends AbstractBuilder<OneToOneBBar> {

    public OneToOneBBarBuilderCodegen(OneToOneBBar instance) {
        super(instance);
    }

    public OneToOneBBarBuilder id(Integer id) {
        get().setId(id);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBarBuilder name(String name) {
        get().setName(name);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBarBuilder oneToOneBFoo(OneToOneBFoo oneToOneBFoo) {
        get().setOneToOneBFoo(oneToOneBFoo);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBar get() {
        return (features.domain.OneToOneBBar) super.get();
    }

}
