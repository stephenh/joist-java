package features.domain.builders;

import features.domain.InheritanceASubTwo;
import features.domain.InheritanceAThing;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceASubTwoBuilderCodegen extends InheritanceABaseBuilder {

  public InheritanceASubTwoBuilderCodegen(InheritanceASubTwo instance) {
    super(instance);
  }

  public String two() {
    return get().getTwo();
  }

  public InheritanceASubTwoBuilder two(String two) {
    get().setTwo(two);
    return (InheritanceASubTwoBuilder) this;
  }

  public InheritanceASubTwoBuilder with(String two) {
    return two(two);
  }

  @Override
  public InheritanceASubTwoBuilder defaults() {
    if (two() == null) {
      two("two");
    }
    return (InheritanceASubTwoBuilder) super.defaults();
  }

  public InheritanceAThingBuilder inheritanceAThing() {
    if (get().getInheritanceAThing() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceAThing());
  }

  public InheritanceASubTwoBuilder inheritanceAThing(InheritanceAThing inheritanceAThing) {
    get().setInheritanceAThing(inheritanceAThing);
    return (InheritanceASubTwoBuilder) this;
  }

  public InheritanceASubTwoBuilder with(InheritanceAThing inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceASubTwoBuilder inheritanceAThing(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing.get());
  }

  public InheritanceASubTwoBuilder with(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceASubTwo get() {
    return (features.domain.InheritanceASubTwo) super.get();
  }

  @Override
  public InheritanceASubTwoBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceASubTwoBuilder) this;
  }

}
