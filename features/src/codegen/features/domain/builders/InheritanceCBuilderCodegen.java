package features.domain.builders;

import features.domain.InheritanceC;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceCBuilderCodegen extends AbstractBuilder<InheritanceC> {

  public InheritanceCBuilderCodegen(InheritanceC instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public InheritanceCBuilder id(Long id) {
    get().setId(id);
    return (InheritanceCBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceCBuilder name(String name) {
    get().setName(name);
    return (InheritanceCBuilder) this;
  }

  @Override
  public InheritanceCBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (InheritanceCBuilder) super.defaults();
  }

  public InheritanceC get() {
    return (features.domain.InheritanceC) super.get();
  }

  @Override
  public InheritanceCBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceCBuilder) this;
  }

}
