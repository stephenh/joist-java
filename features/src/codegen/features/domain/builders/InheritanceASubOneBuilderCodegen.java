package features.domain.builders;

import features.domain.InheritanceAOwner;
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

  @Override
  public InheritanceASubOneBuilder defaults() {
    if (one() == null) {
      one("one");
    }
    return (InheritanceASubOneBuilder) super.defaults();
  }

  public String name() {
    return get().getName();
  }

  public InheritanceASubOneBuilder name(String name) {
    get().setName(name);
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
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceASubOneBuilder inheritanceAThing(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing == null ? null : inheritanceAThing.get());
  }

  public InheritanceASubOneBuilder with(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceAOwnerBuilder inheritanceAOwner() {
    if (get().getInheritanceAOwner() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceAOwner());
  }

  public InheritanceASubOneBuilder inheritanceAOwner(InheritanceAOwner inheritanceAOwner) {
    get().setInheritanceAOwner(inheritanceAOwner);
    return (InheritanceASubOneBuilder) this;
  }

  public InheritanceASubOneBuilder with(InheritanceAOwner inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceASubOneBuilder inheritanceAOwner(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner == null ? null : inheritanceAOwner.get());
  }

  public InheritanceASubOneBuilder with(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
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
