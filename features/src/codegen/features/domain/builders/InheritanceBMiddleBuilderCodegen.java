package features.domain.builders;

import features.domain.InheritanceBMiddle;

@SuppressWarnings("all")
public abstract class InheritanceBMiddleBuilderCodegen extends InheritanceBRootBuilder {

  public InheritanceBMiddleBuilderCodegen(InheritanceBMiddle instance) {
    super(instance);
  }

  public String middleName() {
    return get().getMiddleName();
  }

  public InheritanceBMiddleBuilder middleName(String middleName) {
    get().setMiddleName(middleName);
    return (InheritanceBMiddleBuilder) this;
  }

  public InheritanceBMiddleBuilder with(String middleName) {
    return middleName(middleName);
  }

  @Override
  public InheritanceBMiddleBuilder defaults() {
    if (middleName() == null) {
      middleName("middleName");
    }
    return (InheritanceBMiddleBuilder) super.defaults();
  }

  public InheritanceBMiddle get() {
    return (features.domain.InheritanceBMiddle) super.get();
  }

}
