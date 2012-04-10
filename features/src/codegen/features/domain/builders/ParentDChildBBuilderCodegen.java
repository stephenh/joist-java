package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildB;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentDChildBBuilderCodegen extends AbstractBuilder<ParentDChildB> {

  public ParentDChildBBuilderCodegen(ParentDChildB instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentDChildBBuilder id(Long id) {
    get().setId(id);
    return (ParentDChildBBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentDChildBBuilder name(String name) {
    get().setName(name);
    return (ParentDChildBBuilder) this;
  }

  public ParentDChildBBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentDChildBBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (parentD() == null) {
      parentD(Builders.aParentD().defaults());
    }
    return (ParentDChildBBuilder) super.defaults();
  }

  public ParentDBuilder parentD() {
    if (get().getParentD() == null) {
      return null;
    }
    return Builders.existing(get().getParentD());
  }

  public ParentDChildBBuilder parentD(ParentD parentD) {
    get().setParentD(parentD);
    return (ParentDChildBBuilder) this;
  }

  public ParentDChildBBuilder with(ParentD parentD) {
    return parentD(parentD);
  }

  public ParentDChildBBuilder parentD(ParentDBuilder parentD) {
    return parentD(parentD.get());
  }

  public ParentDChildBBuilder with(ParentDBuilder parentD) {
    return parentD(parentD);
  }

  public ParentDChildB get() {
    return (features.domain.ParentDChildB) super.get();
  }

}
