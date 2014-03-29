package features.domain.builders;

import features.domain.InheritanceAOwner;
import features.domain.InheritanceASubTwo;
import features.domain.InheritanceAThing;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceASubTwoBuilderCodegen extends InheritanceABaseBuilder {

  public InheritanceASubTwoBuilderCodegen(InheritanceASubTwo instance) {
    super(instance);
  }

  @Override
  public InheritanceASubTwoBuilder defaults() {
    return (InheritanceASubTwoBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (two() == null) {
      two(defaultTwo());
    }
    c.rememberIfSet(inheritanceAThing());
  }

  public String two() {
    return get().getTwo();
  }

  public InheritanceASubTwoBuilder two(String two) {
    get().setTwo(two);
    return (InheritanceASubTwoBuilder) this;
  }

  protected String defaultTwo() {
    return "two";
  }

  public String name() {
    return get().getName();
  }

  public InheritanceASubTwoBuilder name(String name) {
    get().setName(name);
    return (InheritanceASubTwoBuilder) this;
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
    return inheritanceAThing(inheritanceAThing == null ? null : inheritanceAThing.get());
  }

  public InheritanceASubTwoBuilder with(InheritanceAThingBuilder inheritanceAThing) {
    return inheritanceAThing(inheritanceAThing);
  }

  public InheritanceAOwnerBuilder inheritanceAOwner() {
    if (get().getInheritanceAOwner() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceAOwner());
  }

  public InheritanceASubTwoBuilder inheritanceAOwner(InheritanceAOwner inheritanceAOwner) {
    get().setInheritanceAOwner(inheritanceAOwner);
    return (InheritanceASubTwoBuilder) this;
  }

  public InheritanceASubTwoBuilder with(InheritanceAOwner inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceASubTwoBuilder inheritanceAOwner(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner == null ? null : inheritanceAOwner.get());
  }

  public InheritanceASubTwoBuilder with(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceASubTwo get() {
    return (features.domain.InheritanceASubTwo) super.get();
  }

  @Override
  public InheritanceASubTwoBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceASubTwoBuilder) this;
  }

  @Override
  public InheritanceASubTwoBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceASubTwoBuilder) super.use(builder);
  }

}
