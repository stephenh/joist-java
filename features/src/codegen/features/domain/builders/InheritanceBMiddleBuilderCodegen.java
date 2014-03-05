package features.domain.builders;

import features.domain.InheritanceBMiddle;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceBMiddleBuilderCodegen extends InheritanceBRootBuilder {

  public InheritanceBMiddleBuilderCodegen(InheritanceBMiddle instance) {
    super(instance);
  }

  @Override
  public InheritanceBMiddleBuilder defaults() {
    try {
      DefaultsContext.push();
      if (middleName() == null) {
        middleName("middleName");
      }
      return (InheritanceBMiddleBuilder) super.defaults();
    } finally {
      DefaultsContext.pop();
    }
  }

  public String middleName() {
    return get().getMiddleName();
  }

  public InheritanceBMiddleBuilder middleName(String middleName) {
    get().setMiddleName(middleName);
    return (InheritanceBMiddleBuilder) this;
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

}
