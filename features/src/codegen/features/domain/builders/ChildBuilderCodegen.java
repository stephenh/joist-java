package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildBuilderCodegen extends AbstractBuilder<Child> {

  public ChildBuilderCodegen(Child instance) {
    super(instance);
  }

  @Override
  public ChildBuilder defaults() {
    return (ChildBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(parent());
    if (parent() == null) {
      parent(c.getIfAvailable(Parent.class));
      if (parent() == null) {
        parent(defaultParent());
        c.rememberIfSet(parent());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ChildBuilder id(Long id) {
    get().setId(id);
    return (ChildBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ChildBuilder name(String name) {
    get().setName(name);
    return (ChildBuilder) this;
  }

  public ChildBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentBuilder parent() {
    if (get().getParent() == null) {
      return null;
    }
    return Builders.existing(get().getParent());
  }

  public ChildBuilder parent(Parent parent) {
    get().setParent(parent);
    return (ChildBuilder) this;
  }

  public ChildBuilder with(Parent parent) {
    return parent(parent);
  }

  public ChildBuilder parent(ParentBuilder parent) {
    return parent(parent == null ? null : parent.get());
  }

  public ChildBuilder with(ParentBuilder parent) {
    return parent(parent);
  }

  protected ParentBuilder defaultParent() {
    return Builders.aParent().defaults();
  }

  public GrandChildBuilder newGrandChild() {
    return Builders.aGrandChild().child((ChildBuilder) this);
  }

  public List<GrandChildBuilder> grandChilds() {
    List<GrandChildBuilder> b = new ArrayList<GrandChildBuilder>();
    for (GrandChild e : get().getGrandChilds()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public GrandChildBuilder grandChild(int i) {
    return Builders.existing(get().getGrandChilds().get(i));
  }

  public Child get() {
    return (features.domain.Child) super.get();
  }

  @Override
  public ChildBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildBuilder) this;
  }

  @Override
  public ChildBuilder use(AbstractBuilder<?> builder) {
    return (ChildBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    Child.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = Child.queries.findAllIds();
    for (Long id : ids) {
      Child.queries.delete(Child.queries.find(id));
    }
  }

}
