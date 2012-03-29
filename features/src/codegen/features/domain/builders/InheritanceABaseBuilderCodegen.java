package features.domain.builders;

import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceABaseBuilderCodegen extends AbstractBuilder<InheritanceABase> {

  public InheritanceABaseBuilderCodegen(InheritanceABase instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public InheritanceABaseBuilder id(Long id) {
    get().setId(id);
    return (InheritanceABaseBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceABaseBuilder name(String name) {
    get().setName(name);
    return (InheritanceABaseBuilder) this;
  }

  public InheritanceABaseBuilder with(String name) {
    return name(name);
  }

  public InheritanceAOwnerBuilder inheritanceAOwner() {
    if (get().getInheritanceAOwner() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceAOwner());
  }

  public InheritanceABaseBuilder inheritanceAOwner(InheritanceAOwner inheritanceAOwner) {
    get().setInheritanceAOwner(inheritanceAOwner);
    return (InheritanceABaseBuilder) this;
  }

  public InheritanceABaseBuilder with(InheritanceAOwner inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceABaseBuilder inheritanceAOwner(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner.get());
  }

  public InheritanceABaseBuilder with(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceABase get() {
    return (features.domain.InheritanceABase) super.get();
  }

}
