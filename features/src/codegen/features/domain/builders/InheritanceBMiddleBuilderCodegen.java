package features.domain.builders;

import features.domain.InheritanceBMiddle;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceBMiddleBuilderCodegen extends InheritanceBRootBuilder {

  public InheritanceBMiddleBuilderCodegen(InheritanceBMiddle instance) {
    super(instance);
  }

  @Override
  public InheritanceBMiddleBuilder defaults() {
    return (InheritanceBMiddleBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (middleName() == null) {
      middleName(defaultMiddleName());
    }
  }

  public String middleName() {
    return get().getMiddleName();
  }

  public InheritanceBMiddleBuilder middleName(String middleName) {
    get().setMiddleName(middleName);
    return (InheritanceBMiddleBuilder) this;
  }

  protected String defaultMiddleName() {
    return "middleName";
  }

  public String name() {
    return get().getName();
  }

  public InheritanceBMiddleBuilder name(String name) {
    get().setName(name);
    return (InheritanceBMiddleBuilder) this;
  }

  public InheritanceBMiddle get() {
    return (features.domain.InheritanceBMiddle) super.get();
  }

  @Override
  public InheritanceBMiddleBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceBMiddleBuilder) this;
  }

  @Override
  public InheritanceBMiddleBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceBMiddleBuilder) super.use(builder);
  }

}
