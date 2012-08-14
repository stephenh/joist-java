package features.domain.builders;

import features.domain.ValidationAFoo;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ValidationAFooBuilderCodegen extends AbstractBuilder<ValidationAFoo> {

  public ValidationAFooBuilderCodegen(ValidationAFoo instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ValidationAFooBuilder id(Long id) {
    get().setId(id);
    return (ValidationAFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ValidationAFooBuilder name(String name) {
    get().setName(name);
    return (ValidationAFooBuilder) this;
  }

  public ValidationAFooBuilder with(String name) {
    return name(name);
  }

  @Override
  public ValidationAFooBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ValidationAFooBuilder) super.defaults();
  }

  public ValidationAFoo get() {
    return (features.domain.ValidationAFoo) super.get();
  }

  @Override
  public ValidationAFooBuilder ensureSaved() {
    doEnsureSaved();
    return (ValidationAFooBuilder) this;
  }

}
