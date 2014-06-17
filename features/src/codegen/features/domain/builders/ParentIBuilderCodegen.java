package features.domain.builders;

import features.domain.ChildIA;
import features.domain.ParentI;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentIBuilderCodegen extends AbstractBuilder<ParentI> {

  public ParentIBuilderCodegen(ParentI instance) {
    super(instance);
  }

  @Override
  public ParentIBuilder defaults() {
    return (ParentIBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentIBuilder id(Long id) {
    get().setId(id);
    return (ParentIBuilder) this;
  }

  public ChildIABuilder newChildA() {
    return Builders.aChildIA().parent((ParentIBuilder) this);
  }

  public List<ChildIABuilder> childAs() {
    List<ChildIABuilder> b = new ArrayList<ChildIABuilder>();
    for (ChildIA e : get().getChildAs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ChildIABuilder childA(int i) {
    return Builders.existing(get().getChildAs().get(i));
  }

  public ChildIBBuilder newChildB() {
    return Builders.aChildIB().parent((ParentIBuilder) this);
  }

  public ChildIBBuilder childB() {
    if (get().getChildB() == null) {
      return null;
    }
    return Builders.existing(get().getChildB());
  }

  public ParentI get() {
    return (features.domain.ParentI) super.get();
  }

  @Override
  public ParentIBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentIBuilder) this;
  }

  @Override
  public ParentIBuilder use(AbstractBuilder<?> builder) {
    return (ParentIBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentI.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentI.queries.findAllIds();
    for (Long id : ids) {
      ParentI.queries.delete(ParentI.queries.find(id));
    }
  }

}
