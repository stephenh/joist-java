package features.domain.builders;

import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceABaseBuilderCodegen extends AbstractBuilder<InheritanceABase> {

  public InheritanceABaseBuilderCodegen(InheritanceABase instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
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

  @Override
  public InheritanceABaseBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (InheritanceABaseBuilder) super.defaults();
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

  @Override
  public InheritanceABaseBuilder ensureSaved() {
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (InheritanceABaseBuilder) this;
  }

}
