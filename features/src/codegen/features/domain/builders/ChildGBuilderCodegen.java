package features.domain.builders;

import features.domain.ChildG;
import features.domain.ParentG;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildGBuilderCodegen extends AbstractBuilder<ChildG> {

  public ChildGBuilderCodegen(ChildG instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ChildGBuilder id(Long id) {
    get().setId(id);
    return (ChildGBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ChildGBuilder name(String name) {
    get().setName(name);
    return (ChildGBuilder) this;
  }

  public ChildGBuilder with(String name) {
    return name(name);
  }

  @Override
  public ChildGBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (parentOne() == null) {
      parentOne(Builders.aParentG().defaults());
    }
    return (ChildGBuilder) super.defaults();
  }

  public ParentGBuilder parentOne() {
    if (get().getParentOne() == null) {
      return null;
    }
    return Builders.existing(get().getParentOne());
  }

  public ChildGBuilder parentOne(ParentG parentOne) {
    get().setParentOne(parentOne);
    return (ChildGBuilder) this;
  }

  public ChildGBuilder parentOne(ParentGBuilder parentOne) {
    return parentOne(parentOne == null ? null : parentOne.get());
  }

  public ParentGBuilder parentTwo() {
    if (get().getParentTwo() == null) {
      return null;
    }
    return Builders.existing(get().getParentTwo());
  }

  public ChildGBuilder parentTwo(ParentG parentTwo) {
    get().setParentTwo(parentTwo);
    return (ChildGBuilder) this;
  }

  public ChildGBuilder parentTwo(ParentGBuilder parentTwo) {
    return parentTwo(parentTwo == null ? null : parentTwo.get());
  }

  public ChildG get() {
    return (features.domain.ChildG) super.get();
  }

  @Override
  public ChildGBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildGBuilder) this;
  }

  @Override
  public void delete() {
    ChildG.queries.delete(get());
  }

}
