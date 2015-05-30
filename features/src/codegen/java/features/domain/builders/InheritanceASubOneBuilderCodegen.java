package features.domain.builders;

import features.domain.InheritanceAOwner;
import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubOneChild;
import features.domain.InheritanceAThing;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceASubOneBuilderCodegen extends InheritanceABaseBuilder {

  public InheritanceASubOneBuilderCodegen(InheritanceASubOne instance) {
    super(instance);
  }

  @Override
  public InheritanceASubOneBuilder defaults() {
    return (InheritanceASubOneBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (one() == null) {
      one(defaultOne());
    }
    c.rememberIfSet(inheritanceAThing());
  }

  public String one() {
    return get().getOne();
  }

  public InheritanceASubOneBuilder one(String one) {
    get().setOne(one);
    return (InheritanceASubOneBuilder) this;
  }

  protected String defaultOne() {
    return "one";
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

  public InheritanceASubOneChildBuilder newInheritanceASubOneChild() {
    return Builders.aInheritanceASubOneChild().sub((InheritanceASubOneBuilder) this);
  }

  public List<InheritanceASubOneChildBuilder> inheritanceASubOneChilds() {
    List<InheritanceASubOneChildBuilder> b = new ArrayList<InheritanceASubOneChildBuilder>();
    for (InheritanceASubOneChild e : get().getInheritanceASubOneChilds()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public InheritanceASubOneChildBuilder inheritanceASubOneChild(int i) {
    return Builders.existing(get().getInheritanceASubOneChilds().get(i));
  }

  public InheritanceASubOne get() {
    return (features.domain.InheritanceASubOne) super.get();
  }

  @Override
  public InheritanceASubOneBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceASubOneBuilder) this;
  }

  @Override
  public InheritanceASubOneBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceASubOneBuilder) super.use(builder);
  }

}
