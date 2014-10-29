package features.domain.builders;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceBRootChildBuilderCodegen extends AbstractBuilder<InheritanceBRootChild> {

  public InheritanceBRootChildBuilderCodegen(InheritanceBRootChild instance) {
    super(instance);
  }

  @Override
  public InheritanceBRootChildBuilder defaults() {
    return (InheritanceBRootChildBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(inheritanceBRoot());
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public InheritanceBRootChildBuilder id(Long id) {
    get().setId(id);
    return (InheritanceBRootChildBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceBRootChildBuilder name(String name) {
    get().setName(name);
    return (InheritanceBRootChildBuilder) this;
  }

  public InheritanceBRootChildBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public InheritanceBRootBuilder inheritanceBRoot() {
    if (get().getInheritanceBRoot() == null) {
      return null;
    }
    return Builders.existing(get().getInheritanceBRoot());
  }

  public InheritanceBRootChildBuilder inheritanceBRoot(InheritanceBRoot inheritanceBRoot) {
    get().setInheritanceBRoot(inheritanceBRoot);
    return (InheritanceBRootChildBuilder) this;
  }

  public InheritanceBRootChildBuilder with(InheritanceBRoot inheritanceBRoot) {
    return inheritanceBRoot(inheritanceBRoot);
  }

  public InheritanceBRootChildBuilder inheritanceBRoot(InheritanceBRootBuilder inheritanceBRoot) {
    return inheritanceBRoot(inheritanceBRoot == null ? null : inheritanceBRoot.get());
  }

  public InheritanceBRootChildBuilder with(InheritanceBRootBuilder inheritanceBRoot) {
    return inheritanceBRoot(inheritanceBRoot);
  }

  public InheritanceBRootChild get() {
    return (features.domain.InheritanceBRootChild) super.get();
  }

  @Override
  public InheritanceBRootChildBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceBRootChildBuilder) this;
  }

  @Override
  public InheritanceBRootChildBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceBRootChildBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    InheritanceBRootChild.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = InheritanceBRootChild.queries.findAllIds();
    for (Long id : ids) {
      InheritanceBRootChild.queries.delete(InheritanceBRootChild.queries.find(id));
    }
  }

}
