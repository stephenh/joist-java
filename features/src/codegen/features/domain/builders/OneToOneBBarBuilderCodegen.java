package features.domain.builders;

import features.domain.OneToOneBBar;
import features.domain.OneToOneBFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneBBarBuilderCodegen extends AbstractBuilder<OneToOneBBar> {

    public OneToOneBBarBuilderCodegen(OneToOneBBar instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public OneToOneBBarBuilder id(Long id) {
        get().setId(id);
        return (OneToOneBBarBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public OneToOneBBarBuilder name(String name) {
        get().setName(name);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBarBuilder with(String name) {
        get().setName(name);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBFooBuilder oneToOneBFoo() {
        return Builders.existing(get().getOneToOneBFoo());
    }

    public OneToOneBBarBuilder oneToOneBFoo(OneToOneBFoo oneToOneBFoo) {
        get().setOneToOneBFoo(oneToOneBFoo);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBarBuilder with(OneToOneBFoo oneToOneBFoo) {
        get().setOneToOneBFoo(oneToOneBFoo);
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBarBuilder oneToOneBFoo(OneToOneBFooBuilder oneToOneBFoo) {
        get().setOneToOneBFoo(oneToOneBFoo.get());
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBarBuilder with(OneToOneBFooBuilder oneToOneBFoo) {
        get().setOneToOneBFoo(oneToOneBFoo.get());
        return (OneToOneBBarBuilder) this;
    }

    public OneToOneBBar get() {
        return (features.domain.OneToOneBBar) super.get();
    }

}
