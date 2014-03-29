package features.domain.builders;

import features.domain.Primitives;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class PrimitivesBuilderCodegen extends AbstractBuilder<Primitives> {

  public PrimitivesBuilderCodegen(Primitives instance) {
    super(instance);
  }

  @Override
  public PrimitivesBuilder defaults() {
    return (PrimitivesBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (flag() == null) {
      flag(defaultFlag());
    }
    if (name() == null) {
      name(defaultName());
    }
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

  protected Boolean defaultFlag() {
    return false;
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

  protected String defaultName() {
    return "name";
  }

  public Primitives get() {
    return (features.domain.Primitives) super.get();
  }

  @Override
  public PrimitivesBuilder ensureSaved() {
    doEnsureSaved();
    return (PrimitivesBuilder) this;
  }

  @Override
  public PrimitivesBuilder use(AbstractBuilder<?> builder) {
    return (PrimitivesBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    Primitives.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = Primitives.queries.findAllIds();
    for (Long id : ids) {
      Primitives.queries.delete(Primitives.queries.find(id));
    }
  }

}
