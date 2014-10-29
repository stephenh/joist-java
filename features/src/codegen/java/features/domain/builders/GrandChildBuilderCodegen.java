package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class GrandChildBuilderCodegen extends AbstractBuilder<GrandChild> {

  public GrandChildBuilderCodegen(GrandChild instance) {
    super(instance);
  }

  @Override
  public GrandChildBuilder defaults() {
    return (GrandChildBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(child());
    if (child() == null) {
      child(c.getIfAvailable(Child.class));
      if (child() == null) {
        child(defaultChild());
        c.rememberIfSet(child());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public GrandChildBuilder id(Long id) {
    get().setId(id);
    return (GrandChildBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public GrandChildBuilder name(String name) {
    get().setName(name);
    return (GrandChildBuilder) this;
  }

  public GrandChildBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ChildBuilder child() {
    if (get().getChild() == null) {
      return null;
    }
    return Builders.existing(get().getChild());
  }

  public GrandChildBuilder child(Child child) {
    get().setChild(child);
    return (GrandChildBuilder) this;
  }

  public GrandChildBuilder with(Child child) {
    return child(child);
  }

  public GrandChildBuilder child(ChildBuilder child) {
    return child(child == null ? null : child.get());
  }

  public GrandChildBuilder with(ChildBuilder child) {
    return child(child);
  }

  protected ChildBuilder defaultChild() {
    return Builders.aChild().defaults();
  }

  public GrandChild get() {
    return (features.domain.GrandChild) super.get();
  }

  @Override
  public GrandChildBuilder ensureSaved() {
    doEnsureSaved();
    return (GrandChildBuilder) this;
  }

  @Override
  public GrandChildBuilder use(AbstractBuilder<?> builder) {
    return (GrandChildBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    GrandChild.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = GrandChild.queries.findAllIds();
    for (Long id : ids) {
      GrandChild.queries.delete(GrandChild.queries.find(id));
    }
  }

}
