package features.domain.builders;

import features.domain.InheritanceBBottom;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceBBottomBuilderCodegen extends InheritanceBMiddleBuilder {

  public InheritanceBBottomBuilderCodegen(InheritanceBBottom instance) {
    super(instance);
  }

  public String bottomName() {
    return get().getBottomName();
  }

  public InheritanceBBottomBuilder bottomName(String bottomName) {
    get().setBottomName(bottomName);
    return (InheritanceBBottomBuilder) this;
  }

  @Override
  public InheritanceBBottomBuilder defaults() {
    if (bottomName() == null) {
      bottomName("bottomName");
    }
    return (InheritanceBBottomBuilder) super.defaults();
  }

  public String middleName() {
    return get().getMiddleName();
  }

  public InheritanceBBottomBuilder middleName(String middleName) {
    get().setMiddleName(middleName);
    return (InheritanceBBottomBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceBBottomBuilder name(String name) {
    get().setName(name);
    return (InheritanceBBottomBuilder) this;
  }

  public InheritanceBBottom get() {
    return (features.domain.InheritanceBBottom) super.get();
  }

  @Override
  public InheritanceBBottomBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceBBottomBuilder) this;
  }

}
