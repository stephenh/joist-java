package features.domain.builders;

import features.domain.OneToOneAFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneAFooBuilderCodegen extends AbstractBuilder<OneToOneAFoo> {

  public OneToOneAFooBuilderCodegen(OneToOneAFoo instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public OneToOneAFooBuilder id(Long id) {
    get().setId(id);
    return (OneToOneAFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public OneToOneAFooBuilder name(String name) {
    get().setName(name);
    return (OneToOneAFooBuilder) this;
  }

  public OneToOneAFooBuilder with(String name) {
    get().setName(name);
    return (OneToOneAFooBuilder) this;
  }

  public OneToOneABarBuilder oneToOneABar() {
    if (get().getOneToOneABar() == null) {
      return null;
    }
    return Builders.existing(get().getOneToOneABar());
  }

  public OneToOneAFoo get() {
    return (features.domain.OneToOneAFoo) super.get();
  }

}
