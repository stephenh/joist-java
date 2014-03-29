package features.domain.builders;

import features.domain.ValidationAFoo;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ValidationAFooBuilderCodegen extends AbstractBuilder<ValidationAFoo> {

  public ValidationAFooBuilderCodegen(ValidationAFoo instance) {
    super(instance);
  }

  @Override
  public ValidationAFooBuilder defaults() {
    return (ValidationAFooBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
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

  protected String defaultName() {
    return "name";
  }

  public ValidationAFoo get() {
    return (features.domain.ValidationAFoo) super.get();
  }

  @Override
  public ValidationAFooBuilder ensureSaved() {
    doEnsureSaved();
    return (ValidationAFooBuilder) this;
  }

  @Override
  public ValidationAFooBuilder use(AbstractBuilder<?> builder) {
    return (ValidationAFooBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ValidationAFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ValidationAFoo.queries.findAllIds();
    for (Long id : ids) {
      ValidationAFoo.queries.delete(ValidationAFoo.queries.find(id));
    }
  }

}
