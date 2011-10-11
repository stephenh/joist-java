package features.domain.builders;

import features.domain.InheritanceASubOne;
import features.domain.InheritanceAThing;

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
    get().setOne(one);
    return (InheritanceASubOneBuilder) this;
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
    get().setInheritanceAThing(inheritanceAThing);
    return (InheritanceASubOneBuilder) this;
  }

  public InheritanceASubOneBuilder inheritanceAThing(InheritanceAThingBuilder inheritanceAThing) {
    get().setInheritanceAThing(inheritanceAThing.get());
    return (InheritanceASubOneBuilder) this;
  }

  public InheritanceASubOneBuilder with(InheritanceAThingBuilder inheritanceAThing) {
    get().setInheritanceAThing(inheritanceAThing.get());
    return (InheritanceASubOneBuilder) this;
  }

  public InheritanceASubOne get() {
    return (features.domain.InheritanceASubOne) super.get();
  }

}
