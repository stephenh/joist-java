package features.domain.builders;

import features.domain.ChildF;
import features.domain.ParentF;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentFBuilderCodegen extends AbstractBuilder<ParentF> {

  public ParentFBuilderCodegen(ParentF instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentFBuilder id(Long id) {
    get().setId(id);
    return (ParentFBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentFBuilder name(String name) {
    get().setName(name);
    return (ParentFBuilder) this;
  }

  public ParentFBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentFBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (childOne() == null) {
      childOne(Builders.aChildF().defaults());
    }
    return (ParentFBuilder) super.defaults();
  }

  public ChildFBuilder childOne() {
    if (get().getChildOne() == null) {
      return null;
    }
    return Builders.existing(get().getChildOne());
  }

  public ParentFBuilder childOne(ChildF childOne) {
    get().setChildOne(childOne);
    return (ParentFBuilder) this;
  }

  public ParentFBuilder childOne(ChildFBuilder childOne) {
    return childOne(childOne.get());
  }

  public ChildFBuilder childTwo() {
    if (get().getChildTwo() == null) {
      return null;
    }
    return Builders.existing(get().getChildTwo());
  }

  public ParentFBuilder childTwo(ChildF childTwo) {
    get().setChildTwo(childTwo);
    return (ParentFBuilder) this;
  }

  public ParentFBuilder childTwo(ChildFBuilder childTwo) {
    return childTwo(childTwo.get());
  }

  public ParentF get() {
    return (features.domain.ParentF) super.get();
  }

  @Override
  public ParentFBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentFBuilder) this;
  }

}
