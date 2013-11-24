package features.domain.builders;

import features.domain.ParentG;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentGBuilderCodegen extends AbstractBuilder<ParentG> {

  public ParentGBuilderCodegen(ParentG instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentGBuilder id(Long id) {
    get().setId(id);
    return (ParentGBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentGBuilder name(String name) {
    get().setName(name);
    return (ParentGBuilder) this;
  }

  public ParentGBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentGBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ParentGBuilder) super.defaults();
  }

  public ChildGBuilder parentOneChildG() {
    if (get().getParentOneChildG() == null) {
      return null;
    }
    return Builders.existing(get().getParentOneChildG());
  }

  public ChildGBuilder newParentOneChildG() {
    return Builders.aChildG().parentOne((ParentGBuilder) this);
  }

  public ChildGBuilder parentTwoChildG() {
    if (get().getParentTwoChildG() == null) {
      return null;
    }
    return Builders.existing(get().getParentTwoChildG());
  }

  public ChildGBuilder newParentTwoChildG() {
    return Builders.aChildG().parentTwo((ParentGBuilder) this);
  }

  public ParentG get() {
    return (features.domain.ParentG) super.get();
  }

  @Override
  public ParentGBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentGBuilder) this;
  }

  @Override
  public void delete() {
    ParentG.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentG.queries.findAllIds();
    for (Long id : ids) {
      ParentG.queries.delete(ParentG.queries.find(id));
    }
  }

}
