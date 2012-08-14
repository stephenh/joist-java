package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildC;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentDChildCBuilderCodegen extends AbstractBuilder<ParentDChildC> {

  public ParentDChildCBuilderCodegen(ParentDChildC instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentDChildCBuilder id(Long id) {
    get().setId(id);
    return (ParentDChildCBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentDChildCBuilder name(String name) {
    get().setName(name);
    return (ParentDChildCBuilder) this;
  }

  public ParentDChildCBuilder with(String name) {
    return name(name);
  }

  @Override
  public ParentDChildCBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ParentDChildCBuilder) super.defaults();
  }

  public List<ParentDBuilder> parentDs() {
    List<ParentDBuilder> b = new ArrayList<ParentDBuilder>();
    for (ParentD e : get().getParentDs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentDBuilder parentD(int i) {
    return Builders.existing(get().getParentDs().get(i));
  }

  public ParentDChildCBuilder parentD(ParentD parentDs) {
    get().addParentD(parentDs);
    return (ParentDChildCBuilder) this;
  }

  public ParentDChildCBuilder parentD(ParentDBuilder parentDs) {
    get().addParentD(parentDs.get());
    return (ParentDChildCBuilder) this;
  }

  public ParentDChildCBuilder with(ParentD parentDs) {
    get().addParentD(parentDs);
    return (ParentDChildCBuilder) this;
  }

  public ParentDChildCBuilder with(ParentDBuilder parentDs) {
    get().addParentD(parentDs.get());
    return (ParentDChildCBuilder) this;
  }

  public ParentDChildC get() {
    return (features.domain.ParentDChildC) super.get();
  }

  @Override
  public ParentDChildCBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentDChildCBuilder) this;
  }

}
