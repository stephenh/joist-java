package features.domain.builders;

import features.domain.OneToOneAFoo;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class OneToOneAFooBuilderCodegen extends AbstractBuilder<OneToOneAFoo> {

  public OneToOneAFooBuilderCodegen(OneToOneAFoo instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
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
    return name(name);
  }

  @Override
  public OneToOneAFooBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (OneToOneAFooBuilder) super.defaults();
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
