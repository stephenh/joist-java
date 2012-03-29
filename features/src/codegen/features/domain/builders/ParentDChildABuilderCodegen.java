package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildA;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDChildABuilderCodegen extends AbstractBuilder<ParentDChildA> {

  public ParentDChildABuilderCodegen(ParentDChildA instance) {
    super(instance);
  }

  public Long id() {
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
    return parentD(parentD.get());
  }

  public ParentDChildABuilder with(ParentDBuilder parentD) {
    return parentD(parentD);
  }

  public ParentDChildA get() {
    return (features.domain.ParentDChildA) super.get();
  }

}
