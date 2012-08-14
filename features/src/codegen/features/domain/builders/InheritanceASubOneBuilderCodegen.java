package features.domain.builders;

import features.domain.InheritanceASubOne;
import features.domain.InheritanceAThing;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceASubOneBuilderCodegen extends InheritanceABaseBuilder {

  public InheritanceASubOneBuilderCodegen(InheritanceASubOne instance) {
    super(instance);
  }

  public String one() {
    return get().getOne();
  }

  public InheritanceASubOneBuilder one(String one) {
    get().setOne(one);
    return (InheritanceASubOneBuilder) this;
  }

  public InheritanceASubOneBuilder with(String one) {
    return one(one);
  }

  @Override
  public InheritanceASubOneBuilder defaults() {
    if (one() == null) {
      one("one");
    }
    return (InheritanceASubOneBuilder) super.defaults();
  }

  public InheritanceAThingBuilder inheritanceAThing() {
    if (get().getInheritanceAThing() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceAThing());
  }

  public InheritanceASubOneBuilder inheritanceAThing(InheritanceAThing inheritanceAThing) {
    get().setInheritanceAThing(inheritanceAThing);
    return (InheritanceASubOneBuilder) this;
  }

  public InheritanceASubOneBuilder with(InheritanceAThing inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceASubOneBuilder inheritanceAThing(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing.get());
  }

  public InheritanceASubOneBuilder with(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceASubOne get() {
    return (features.domain.InheritanceASubOne) super.get();
  }

  @Override
  public InheritanceASubOneBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceASubOneBuilder) this;
  }

}
