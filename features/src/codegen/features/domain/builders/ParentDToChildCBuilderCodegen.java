package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildC;
import features.domain.ParentDToChildC;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentDToChildCBuilderCodegen extends AbstractBuilder<ParentDToChildC> {

  public ParentDToChildCBuilderCodegen(ParentDToChildC instance) {
    super(instance);
  }

  @Override
  public ParentDToChildCBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      c.rememberIfSet(parentDChildC());
      c.rememberIfSet(parentD());
      if (parentDChildC() == null) {
        parentDChildC(c.getIfAvailable(ParentDChildC.class));
        if (parentDChildC() == null) {
          parentDChildC(Builders.aParentDChildC().defaults());
          c.rememberIfSet(parentDChildC());
        }
      }
      if (parentD() == null) {
        parentD(c.getIfAvailable(ParentD.class));
        if (parentD() == null) {
          parentD(Builders.aParentD().defaults());
          c.rememberIfSet(parentD());
        }
      }
      return (ParentDToChildCBuilder) super.defaults();
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

  public ParentDToChildCBuilder id(Long id) {
    get().setId(id);
    return (ParentDToChildCBuilder) this;
  }

  public ParentDChildCBuilder parentDChildC() {
    if (get().getParentDChildC() == null) {
      return null;
    }
    return Builders.existing(get().getParentDChildC());
  }

  public ParentDToChildCBuilder parentDChildC(ParentDChildC parentDChildC) {
    get().setParentDChildC(parentDChildC);
    return (ParentDToChildCBuilder) this;
  }

  public ParentDToChildCBuilder with(ParentDChildC parentDChildC) {
    return parentDChildC(parentDChildC);
  }

  public ParentDToChildCBuilder parentDChildC(ParentDChildCBuilder parentDChildC) {
    return parentDChildC(parentDChildC == null ? null : parentDChildC.get());
  }

  public ParentDToChildCBuilder with(ParentDChildCBuilder parentDChildC) {
    return parentDChildC(parentDChildC);
  }

  public ParentDBuilder parentD() {
    if (get().getParentD() == null) {
      return null;
    }
    return Builders.existing(get().getParentD());
  }

  public ParentDToChildCBuilder parentD(ParentD parentD) {
    get().setParentD(parentD);
    return (ParentDToChildCBuilder) this;
  }

  public ParentDToChildCBuilder with(ParentD parentD) {
    return parentD(parentD);
  }

  public ParentDToChildCBuilder parentD(ParentDBuilder parentD) {
    return parentD(parentD == null ? null : parentD.get());
  }

  public ParentDToChildCBuilder with(ParentDBuilder parentD) {
    return parentD(parentD);
  }

  public ParentDToChildC get() {
    return (features.domain.ParentDToChildC) super.get();
  }

  @Override
  public ParentDToChildCBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentDToChildCBuilder) this;
  }

  @Override
  public void delete() {
    ParentDToChildC.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentDToChildC.queries.findAllIds();
    for (Long id : ids) {
      ParentDToChildC.queries.delete(ParentDToChildC.queries.find(id));
    }
  }

}
