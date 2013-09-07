package features.domain.builders;

import features.domain.ParentBChildBar;
import features.domain.ParentBParent;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentBChildBarBuilderCodegen extends AbstractBuilder<ParentBChildBar> {

  public ParentBChildBarBuilderCodegen(ParentBChildBar instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentBChildBarBuilder id(Long id) {
    get().setId(id);
    return (ParentBChildBarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentBChildBarBuilder name(String name) {
    get().setName(name);
    return (ParentBChildBarBuilder) this;
  }

  public ParentBChildBarBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentBChildBarBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (parentBParent() == null) {
      parentBParent(Builders.aParentBParent().defaults());
    }
    return (ParentBChildBarBuilder) super.defaults();
  }

  public ParentBParentBuilder parentBParent() {
    if (get().getParentBParent() == null) {
      return null;
    }
    return Builders.existing(get().getParentBParent());
  }

  public ParentBChildBarBuilder parentBParent(ParentBParent parentBParent) {
    get().setParentBParent(parentBParent);
    return (ParentBChildBarBuilder) this;
  }

  public ParentBChildBarBuilder with(ParentBParent parentBParent) {
    return parentBParent(parentBParent);
  }

  public ParentBChildBarBuilder parentBParent(ParentBParentBuilder parentBParent) {
    return parentBParent(parentBParent == null ? null : parentBParent.get());
  }

  public ParentBChildBarBuilder with(ParentBParentBuilder parentBParent) {
    return parentBParent(parentBParent);
  }

  public ParentBChildBar get() {
    return (features.domain.ParentBChildBar) super.get();
  }

  @Override
  public ParentBChildBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentBChildBarBuilder) this;
  }

}
