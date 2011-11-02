package features.domain.builders;

import joist.domain.builders.AbstractBuilder;
import features.domain.OneToOneABar;
import features.domain.OneToOneAFoo;

@SuppressWarnings("all")
public abstract class OneToOneABarBuilderCodegen extends AbstractBuilder<OneToOneABar> {

  public OneToOneABarBuilderCodegen(OneToOneABar instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public OneToOneABarBuilder id(Long id) {
    get().setId(id);
    return (OneToOneABarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public OneToOneABarBuilder name(String name) {
    get().setName(name);
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABarBuilder with(String name) {
    get().setName(name);
    return (OneToOneABarBuilder) this;
  }

  public OneToOneAFooBuilder oneToOneAFoo() {
    if (get().getOneToOneAFoo() == null) {
      return null;
    }
    return Builders.existing(get().getOneToOneAFoo());
  }

  public OneToOneABarBuilder oneToOneAFoo(OneToOneAFoo oneToOneAFoo) {
    get().setOneToOneAFoo(oneToOneAFoo);
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABarBuilder with(OneToOneAFoo oneToOneAFoo) {
    get().setOneToOneAFoo(oneToOneAFoo);
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABarBuilder oneToOneAFoo(OneToOneAFooBuilder oneToOneAFoo) {
    get().setOneToOneAFoo(oneToOneAFoo.get());
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABarBuilder with(OneToOneAFooBuilder oneToOneAFoo) {
    get().setOneToOneAFoo(oneToOneAFoo.get());
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABar get() {
    return (features.domain.OneToOneABar) super.get();
  }

}
