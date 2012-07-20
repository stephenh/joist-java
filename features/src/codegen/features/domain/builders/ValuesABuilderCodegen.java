package features.domain.builders;

import features.domain.ValuesA;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ValuesABuilderCodegen extends AbstractBuilder<ValuesA> {

  public ValuesABuilderCodegen(ValuesA instance) {
    super(instance);
  }

  public Integer i() {
    return get().getI();
  }

  public ValuesABuilder i(Integer i) {
    get().setI(i);
    return (ValuesABuilder) this;
  }

  public ValuesABuilder with(Integer i) {
    return i(i);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ValuesABuilder id(Long id) {
    get().setId(id);
    return (ValuesABuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ValuesABuilder name(String name) {
    get().setName(name);
    return (ValuesABuilder) this;
  }

  public ValuesABuilder with(String name) {
    return name(name);
  }

  @Override
  public ValuesABuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ValuesABuilder) super.defaults();
  }

  public ValuesA get() {
    return (features.domain.ValuesA) super.get();
  }

  @Override
  public ValuesABuilder ensureSaved() {
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (ValuesABuilder) this;
  }

}
