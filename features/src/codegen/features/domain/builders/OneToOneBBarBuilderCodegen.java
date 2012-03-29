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
    return name(name);
  }

  public OneToOneBFooBuilder oneToOneBFoo() {
    if (get().getOneToOneBFoo() == null) {
      return null;
    }
    return Builders.existing(get().getOneToOneBFoo());
  }

  public OneToOneBBarBuilder oneToOneBFoo(OneToOneBFoo oneToOneBFoo) {
    get().setOneToOneBFoo(oneToOneBFoo);
    return (OneToOneBBarBuilder) this;
  }

  public OneToOneBBarBuilder with(OneToOneBFoo oneToOneBFoo) {
    return oneToOneBFoo(oneToOneBFoo);
  }

  public OneToOneBBarBuilder oneToOneBFoo(OneToOneBFooBuilder oneToOneBFoo) {
    return oneToOneBFoo(oneToOneBFoo.get());
  }

  public OneToOneBBarBuilder with(OneToOneBFooBuilder oneToOneBFoo) {
    return oneToOneBFoo(oneToOneBFoo);
  }

  public OneToOneBBar get() {
    return (features.domain.OneToOneBBar) super.get();
  }

}
