package features.domain.builders;

import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceABaseBuilderCodegen extends AbstractBuilder<InheritanceABase> {

  public InheritanceABaseBuilderCodegen(InheritanceABase instance) {
    super(instance);
  }

  @Override
  public InheritanceABaseBuilder defaults() {
    return (InheritanceABaseBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(inheritanceAOwner());
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public InheritanceABaseBuilder id(Long id) {
    get().setId(id);
    return (InheritanceABaseBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceABaseBuilder name(String name) {
    get().setName(name);
    return (InheritanceABaseBuilder) this;
  }

  protected String defaultName() {
    return "name";
  }

  public InheritanceAOwnerBuilder inheritanceAOwner() {
    if (get().getInheritanceAOwner() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceAOwner());
  }

  public InheritanceABaseBuilder inheritanceAOwner(InheritanceAOwner inheritanceAOwner) {
    get().setInheritanceAOwner(inheritanceAOwner);
    return (InheritanceABaseBuilder) this;
  }

  public InheritanceABaseBuilder with(InheritanceAOwner inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceABaseBuilder inheritanceAOwner(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner == null ? null : inheritanceAOwner.get());
  }

  public InheritanceABaseBuilder with(InheritanceAOwnerBuilder inheritanceAOwner) {
    return inheritanceAOwner(inheritanceAOwner);
  }

  public InheritanceABase get() {
    return (features.domain.InheritanceABase) super.get();
  }

  @Override
  public InheritanceABaseBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceABaseBuilder) this;
  }

  @Override
  public InheritanceABaseBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceABaseBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    InheritanceABase.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = InheritanceABase.queries.findAllIds();
    for (Long id : ids) {
      InheritanceABase.queries.delete(InheritanceABase.queries.find(id));
    }
  }

}
