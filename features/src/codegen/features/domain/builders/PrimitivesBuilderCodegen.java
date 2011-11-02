package features.domain.builders;

import joist.domain.builders.AbstractBuilder;
import features.domain.Primitives;

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
    get().setFlag(flag);
    return (PrimitivesBuilder) this;
  }

  public Long id() {
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
    get().setName(name);
    return (PrimitivesBuilder) this;
  }

  public Primitives get() {
    return (features.domain.Primitives) super.get();
  }

}
