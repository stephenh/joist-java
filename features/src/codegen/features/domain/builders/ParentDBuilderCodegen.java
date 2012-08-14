package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildB;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentDBuilderCodegen extends AbstractBuilder<ParentD> {

  public ParentDBuilderCodegen(ParentD instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentDBuilder id(Long id) {
    get().setId(id);
    return (ParentDBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentDBuilder name(String name) {
    get().setName(name);
    return (ParentDBuilder) this;
  }

  public ParentDBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentDBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ParentDBuilder) super.defaults();
  }

  public List<ParentDChildBBuilder> parentDChildBs() {
    List<ParentDChildBBuilder> b = new ArrayList<ParentDChildBBuilder>();
    for (ParentDChildB e : get().getParentDChildBs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentDChildBBuilder parentDChildB(int i) {
    return Builders.existing(get().getParentDChildBs().get(i));
  }

  public ParentD get() {
    return (features.domain.ParentD) super.get();
  }

  @Override
  public ParentDBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentDBuilder) this;
  }

}
