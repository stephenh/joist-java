package features.domain.builders;

import features.domain.ChildG;
import features.domain.ParentG;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildGBuilderCodegen extends AbstractBuilder<ChildG> {

  public ChildGBuilderCodegen(ChildG instance) {
    super(instance);
  }

  @Override
  public ChildGBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      c.rememberIfSet(parentOne());
      c.rememberIfSet(parentTwo());
      if (parentOne() == null) {
        parentOne(c.getIfAvailable(ParentG.class));
        if (parentOne() == null) {
          parentOne(Builders.aParentG().defaults());
          c.rememberIfSet(parentOne());
        }
      }
      return (ChildGBuilder) super.defaults();
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

  public static void deleteAll() {
    List<Long> ids = ChildG.queries.findAllIds();
    for (Long id : ids) {
      ChildG.queries.delete(ChildG.queries.find(id));
    }
  }

}
