package features.domain.builders;

import features.domain.ChildIB;
import features.domain.ParentI;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildIBBuilderCodegen extends AbstractBuilder<ChildIB> {

  public ChildIBBuilderCodegen(ChildIB instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ChildIBBuilder id(Long id) {
    get().setId(id);
    return (ChildIBBuilder) this;
  }

  public ParentIBuilder parent() {
    if (get().getParent() == null) {
      return null;
    }
    return Builders.existing(get().getParent());
  }

  public ChildIBBuilder parent(ParentI parent) {
    get().setParent(parent);
    return (ChildIBBuilder) this;
  }

  public ChildIBBuilder with(ParentI parent) {
    return parent(parent);
  }

  public ChildIBBuilder parent(ParentIBuilder parent) {
    return parent(parent.get());
  }

  public ChildIBBuilder with(ParentIBuilder parent) {
    return parent(parent);
  }

  @Override
  public ChildIBBuilder defaults() {
    if (parent() == null) {
      parent(Builders.aParentI().defaults());
    }
    return (ChildIBBuilder) super.defaults();
  }

  public ChildIB get() {
    return (features.domain.ChildIB) super.get();
  }

  @Override
  public ChildIBBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildIBBuilder) this;
  }

}
