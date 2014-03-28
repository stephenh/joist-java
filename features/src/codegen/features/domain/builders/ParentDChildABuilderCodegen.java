package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildA;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentDChildABuilderCodegen extends AbstractBuilder<ParentDChildA> {

  public ParentDChildABuilderCodegen(ParentDChildA instance) {
    super(instance);
  }

  @Override
  public ParentDChildABuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      c.rememberIfSet(parentD());
      if (parentD() == null) {
        parentD(c.getIfAvailable(ParentD.class));
        if (parentD() == null) {
          parentD(Builders.aParentD().defaults());
          c.rememberIfSet(parentD());
        }
      }
      return (ParentDChildABuilder) super.defaults();
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

  public ParentDChildABuilder id(Long id) {
    get().setId(id);
    return (ParentDChildABuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentDChildABuilder name(String name) {
    get().setName(name);
    return (ParentDChildABuilder) this;
  }

  public ParentDChildABuilder with(String name) {
    return name(name);
  }

  public ParentDBuilder parentD() {
    if (get().getParentD() == null) {
      return null;
    }
    return Builders.existing(get().getParentD());
  }

  public ParentDChildABuilder parentD(ParentD parentD) {
    get().setParentD(parentD);
    return (ParentDChildABuilder) this;
  }

  public ParentDChildABuilder with(ParentD parentD) {
    return parentD(parentD);
  }

  public ParentDChildABuilder parentD(ParentDBuilder parentD) {
    return parentD(parentD == null ? null : parentD.get());
  }

  public ParentDChildABuilder with(ParentDBuilder parentD) {
    return parentD(parentD);
  }

  public ParentDChildA get() {
    return (features.domain.ParentDChildA) super.get();
  }

  @Override
  public ParentDChildABuilder ensureSaved() {
    doEnsureSaved();
    return (ParentDChildABuilder) this;
  }

  @Override
  public void delete() {
    ParentDChildA.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentDChildA.queries.findAllIds();
    for (Long id : ids) {
      ParentDChildA.queries.delete(ParentDChildA.queries.find(id));
    }
  }

}
