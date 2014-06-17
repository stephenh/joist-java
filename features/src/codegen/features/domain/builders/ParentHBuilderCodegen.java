package features.domain.builders;

import features.domain.ChildH;
import features.domain.ParentH;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentHBuilderCodegen extends AbstractBuilder<ParentH> {

  public ParentHBuilderCodegen(ParentH instance) {
    super(instance);
  }

  @Override
  public ParentHBuilder defaults() {
    return (ParentHBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    if (threshold() == null) {
      threshold(defaultThreshold());
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentHBuilder id(Long id) {
    get().setId(id);
    return (ParentHBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentHBuilder name(String name) {
    get().setName(name);
    return (ParentHBuilder) this;
  }

  public ParentHBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public Long threshold() {
    return get().getThreshold();
  }

  public ParentHBuilder threshold(Long threshold) {
    get().setThreshold(threshold);
    return (ParentHBuilder) this;
  }

  public ParentHBuilder with(Long threshold) {
    return threshold(threshold);
  }

  protected Long defaultThreshold() {
    return 0l;
  }

  public ChildHBuilder newChildH() {
    return Builders.aChildH().parent((ParentHBuilder) this);
  }

  public List<ChildHBuilder> childHs() {
    List<ChildHBuilder> b = new ArrayList<ChildHBuilder>();
    for (ChildH e : get().getChildHs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ChildHBuilder childH(int i) {
    return Builders.existing(get().getChildHs().get(i));
  }

  public ParentH get() {
    return (features.domain.ParentH) super.get();
  }

  @Override
  public ParentHBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentHBuilder) this;
  }

  @Override
  public ParentHBuilder use(AbstractBuilder<?> builder) {
    return (ParentHBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentH.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentH.queries.findAllIds();
    for (Long id : ids) {
      ParentH.queries.delete(ParentH.queries.find(id));
    }
  }

}
