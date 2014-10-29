package features.domain.builders;

import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubOneChild;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceASubOneChildBuilderCodegen extends AbstractBuilder<InheritanceASubOneChild> {

  public InheritanceASubOneChildBuilderCodegen(InheritanceASubOneChild instance) {
    super(instance);
  }

  @Override
  public InheritanceASubOneChildBuilder defaults() {
    return (InheritanceASubOneChildBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(sub());
    if (sub() == null) {
      sub(c.getIfAvailable(InheritanceASubOne.class));
      if (sub() == null) {
        sub(defaultSub());
        c.rememberIfSet(sub());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public InheritanceASubOneChildBuilder id(Long id) {
    get().setId(id);
    return (InheritanceASubOneChildBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceASubOneChildBuilder name(String name) {
    get().setName(name);
    return (InheritanceASubOneChildBuilder) this;
  }

  public InheritanceASubOneChildBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public InheritanceASubOneBuilder sub() {
    if (get().getSub() == null) {
      return null;
    }
    return Builders.existing(get().getSub());
  }

  public InheritanceASubOneChildBuilder sub(InheritanceASubOne sub) {
    get().setSub(sub);
    return (InheritanceASubOneChildBuilder) this;
  }

  public InheritanceASubOneChildBuilder with(InheritanceASubOne sub) {
    return sub(sub);
  }

  public InheritanceASubOneChildBuilder sub(InheritanceASubOneBuilder sub) {
    return sub(sub == null ? null : sub.get());
  }

  public InheritanceASubOneChildBuilder with(InheritanceASubOneBuilder sub) {
    return sub(sub);
  }

  protected InheritanceASubOneBuilder defaultSub() {
    return Builders.aInheritanceASubOne().defaults();
  }

  public InheritanceASubOneChild get() {
    return (features.domain.InheritanceASubOneChild) super.get();
  }

  @Override
  public InheritanceASubOneChildBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceASubOneChildBuilder) this;
  }

  @Override
  public InheritanceASubOneChildBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceASubOneChildBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    InheritanceASubOneChild.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = InheritanceASubOneChild.queries.findAllIds();
    for (Long id : ids) {
      InheritanceASubOneChild.queries.delete(InheritanceASubOneChild.queries.find(id));
    }
  }

}
