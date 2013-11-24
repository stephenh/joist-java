package features.domain.builders;

import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentCBarBuilderCodegen extends AbstractBuilder<ParentCBar> {

  public ParentCBarBuilderCodegen(ParentCBar instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentCBarBuilder id(Long id) {
    get().setId(id);
    return (ParentCBarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentCBarBuilder name(String name) {
    get().setName(name);
    return (ParentCBarBuilder) this;
  }

  public ParentCBarBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentCBarBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (firstParent() == null) {
      firstParent(Builders.aParentCFoo().defaults());
    }
    if (secondParent() == null) {
      secondParent(Builders.aParentCFoo().defaults());
    }
    return (ParentCBarBuilder) super.defaults();
  }

  public ParentCFooBuilder firstParent() {
    if (get().getFirstParent() == null) {
      return null;
    }
    return Builders.existing(get().getFirstParent());
  }

  public ParentCBarBuilder firstParent(ParentCFoo firstParent) {
    get().setFirstParent(firstParent);
    return (ParentCBarBuilder) this;
  }

  public ParentCBarBuilder firstParent(ParentCFooBuilder firstParent) {
    return firstParent(firstParent == null ? null : firstParent.get());
  }

  public ParentCFooBuilder secondParent() {
    if (get().getSecondParent() == null) {
      return null;
    }
    return Builders.existing(get().getSecondParent());
  }

  public ParentCBarBuilder secondParent(ParentCFoo secondParent) {
    get().setSecondParent(secondParent);
    return (ParentCBarBuilder) this;
  }

  public ParentCBarBuilder secondParent(ParentCFooBuilder secondParent) {
    return secondParent(secondParent == null ? null : secondParent.get());
  }

  public ParentCBar get() {
    return (features.domain.ParentCBar) super.get();
  }

  @Override
  public ParentCBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentCBarBuilder) this;
  }

  @Override
  public void delete() {
    ParentCBar.queries.delete(get());
  }

}
