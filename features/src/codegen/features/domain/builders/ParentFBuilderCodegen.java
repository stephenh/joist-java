package features.domain.builders;

import features.domain.ChildF;
import features.domain.ParentF;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentFBuilderCodegen extends AbstractBuilder<ParentF> {

  public ParentFBuilderCodegen(ParentF instance) {
    super(instance);
  }

  @Override
  public ParentFBuilder defaults() {
    try {
      DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      DefaultsContext.get().rememberIfSet(childOne());
      DefaultsContext.get().rememberIfSet(childTwo());
      if (childOne() == null) {
        childOne(DefaultsContext.get().getIfAvailable(ChildF.class));
        if (childOne() == null) {
          childOne(Builders.aChildF().defaults());
          DefaultsContext.get().rememberIfSet(childOne());
        }
      }
      return (ParentFBuilder) super.defaults();
    } finally {
      DefaultsContext.pop();
    }
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
    return childOne(childOne == null ? null : childOne.get());
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
    return childTwo(childTwo == null ? null : childTwo.get());
  }

  public ParentF get() {
    return (features.domain.ParentF) super.get();
  }

  @Override
  public ParentFBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentFBuilder) this;
  }

  @Override
  public void delete() {
    ParentF.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentF.queries.findAllIds();
    for (Long id : ids) {
      ParentF.queries.delete(ParentF.queries.find(id));
    }
  }

}
