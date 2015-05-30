package features.domain.builders;

import features.domain.ParentE;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentEBuilderCodegen extends AbstractBuilder<ParentE> {

  public ParentEBuilderCodegen(ParentE instance) {
    super(instance);
  }

  @Override
  public ParentEBuilder defaults() {
    return (ParentEBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(parentE());
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentEBuilder id(Long id) {
    get().setId(id);
    return (ParentEBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentEBuilder name(String name) {
    get().setName(name);
    return (ParentEBuilder) this;
  }

  public ParentEBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentEBuilder parentE() {
    if (get().getParentE() == null) {
      return null;
    }
    return Builders.existing(get().getParentE());
  }

  public ParentEBuilder parentE(ParentE parentE) {
    get().setParentE(parentE);
    return (ParentEBuilder) this;
  }

  public ParentEBuilder with(ParentE parentE) {
    return parentE(parentE);
  }

  public ParentEBuilder parentE(ParentEBuilder parentE) {
    return parentE(parentE == null ? null : parentE.get());
  }

  public ParentEBuilder with(ParentEBuilder parentE) {
    return parentE(parentE);
  }

  public ParentEBuilder newParentE() {
    return Builders.aParentE().parentE((ParentEBuilder) this);
  }

  public List<ParentEBuilder> parentEs() {
    List<ParentEBuilder> b = new ArrayList<ParentEBuilder>();
    for (ParentE e : get().getParentEs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentEBuilder parentE(int i) {
    return Builders.existing(get().getParentEs().get(i));
  }

  public ParentE get() {
    return (features.domain.ParentE) super.get();
  }

  @Override
  public ParentEBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentEBuilder) this;
  }

  @Override
  public ParentEBuilder use(AbstractBuilder<?> builder) {
    return (ParentEBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentE.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentE.queries.findAllIds();
    for (Long id : ids) {
      ParentE.queries.delete(ParentE.queries.find(id));
    }
  }

}
