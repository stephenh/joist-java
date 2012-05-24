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

  public InheritanceBBottomBuilder with(String bottomName) {
    return bottomName(bottomName);
  }

  @Override
  public InheritanceBBottomBuilder defaults() {
    if (bottomName() == null) {
      bottomName("bottomName");
    }
    return (InheritanceBBottomBuilder) super.defaults();
  }

  public InheritanceBBottom get() {
    return (features.domain.InheritanceBBottom) super.get();
  }

  @Override
  public InheritanceBBottomBuilder ensureSaved() {
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (InheritanceBBottomBuilder) this;
  }

}
