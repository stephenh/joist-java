package features.domain.builders;

import features.domain.ChildIB;
import features.domain.ParentI;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildIBBuilderCodegen extends AbstractBuilder<ChildIB> {

  public ChildIBBuilderCodegen(ChildIB instance) {
    super(instance);
  }

  @Override
  public ChildIBBuilder defaults() {
    return (ChildIBBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    c.rememberIfSet(parent());
    if (parent() == null) {
      parent(c.getIfAvailable(ParentI.class));
      if (parent() == null) {
        parent(defaultParent());
        c.rememberIfSet(parent());
      }
    }
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
    return parent(parent == null ? null : parent.get());
  }

  public ChildIBBuilder with(ParentIBuilder parent) {
    return parent(parent);
  }

  protected ParentIBuilder defaultParent() {
    return Builders.aParentI().defaults();
  }

  public ChildIB get() {
    return (features.domain.ChildIB) super.get();
  }

  @Override
  public ChildIBBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildIBBuilder) this;
  }

  @Override
  public ChildIBBuilder use(AbstractBuilder<?> builder) {
    return (ChildIBBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ChildIB.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ChildIB.queries.findAllIds();
    for (Long id : ids) {
      ChildIB.queries.delete(ChildIB.queries.find(id));
    }
  }

}
