package features.domain.builders;

import features.domain.Primitives;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class PrimitivesBuilderCodegen extends AbstractBuilder<Primitives> {

  public PrimitivesBuilderCodegen(Primitives instance) {
    super(instance);
  }

  public Boolean flag() {
    return get().getFlag();
  }

  public PrimitivesBuilder flag(Boolean flag) {
    get().setFlag(flag);
    return (PrimitivesBuilder) this;
  }

  public PrimitivesBuilder with(Boolean flag) {
    return flag(flag);
  }

  @Override
  public PrimitivesBuilder defaults() {
    if (flag() == null) {
      flag(false);
    }
    if (name() == null) {
      name("name");
    }
    return (PrimitivesBuilder) super.defaults();
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public PrimitivesBuilder id(Long id) {
    get().setId(id);
    return (PrimitivesBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public PrimitivesBuilder name(String name) {
    get().setName(name);
    return (PrimitivesBuilder) this;
  }

  public PrimitivesBuilder with(String name) {
    return name(name);
  }

  public Primitives get() {
    return (features.domain.Primitives) super.get();
  }

  @Override
  public PrimitivesBuilder ensureSaved() {
    doEnsureSaved();
    return (PrimitivesBuilder) this;
  }

}
