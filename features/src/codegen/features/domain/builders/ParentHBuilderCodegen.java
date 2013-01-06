package features.domain.builders;

import features.domain.ChildH;
import features.domain.ParentH;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentHBuilderCodegen extends AbstractBuilder<ParentH> {

  public ParentHBuilderCodegen(ParentH instance) {
    super(instance);
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

  @Override
  public ParentHBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (threshold() == null) {
      threshold(0l);
    }
    return (ParentHBuilder) super.defaults();
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

  public List<ChildHBuilder> parentChildHs() {
    List<ChildHBuilder> b = new ArrayList<ChildHBuilder>();
    for (ChildH e : get().getParentChildHs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ChildHBuilder parentChildH(int i) {
    return Builders.existing(get().getParentChildHs().get(i));
  }

  public ParentH get() {
    return (features.domain.ParentH) super.get();
  }

  @Override
  public ParentHBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentHBuilder) this;
  }

}
