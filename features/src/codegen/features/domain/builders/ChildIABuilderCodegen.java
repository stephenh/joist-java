package features.domain.builders;

import features.domain.ChildIA;
import features.domain.ParentI;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildIABuilderCodegen extends AbstractBuilder<ChildIA> {

  public ChildIABuilderCodegen(ChildIA instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ChildIABuilder id(Long id) {
    get().setId(id);
    return (ChildIABuilder) this;
  }

  public ParentIBuilder parent() {
    if (get().getParent() == null) {
      return null;
    }
    return Builders.existing(get().getParent());
  }

  public ChildIABuilder parent(ParentI parent) {
    get().setParent(parent);
    return (ChildIABuilder) this;
  }

  public ChildIABuilder with(ParentI parent) {
    return parent(parent);
  }

  public ChildIABuilder parent(ParentIBuilder parent) {
    return parent(parent.get());
  }

  public ChildIABuilder with(ParentIBuilder parent) {
    return parent(parent);
  }

  @Override
  public ChildIABuilder defaults() {
    if (parent() == null) {
      parent(Builders.aParentI().defaults());
    }
    return (ChildIABuilder) super.defaults();
  }

  public ChildIA get() {
    return (features.domain.ChildIA) super.get();
  }

  @Override
  public ChildIABuilder ensureSaved() {
    doEnsureSaved();
    return (ChildIABuilder) this;
  }

}
