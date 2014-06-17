package features.domain.builders;

import features.domain.Child;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentBuilderCodegen extends AbstractBuilder<Parent> {

  public ParentBuilderCodegen(Parent instance) {
    super(instance);
  }

  @Override
  public ParentBuilder defaults() {
    return (ParentBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentBuilder id(Long id) {
    get().setId(id);
    return (ParentBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentBuilder name(String name) {
    get().setName(name);
    return (ParentBuilder) this;
  }

  public ParentBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ChildBuilder newChild() {
    return Builders.aChild().parent((ParentBuilder) this);
  }

  public List<ChildBuilder> childs() {
    List<ChildBuilder> b = new ArrayList<ChildBuilder>();
    for (Child e : get().getChilds()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ChildBuilder child(int i) {
    return Builders.existing(get().getChilds().get(i));
  }

  public Parent get() {
    return (features.domain.Parent) super.get();
  }

  @Override
  public ParentBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentBuilder) this;
  }

  @Override
  public ParentBuilder use(AbstractBuilder<?> builder) {
    return (ParentBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    Parent.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = Parent.queries.findAllIds();
    for (Long id : ids) {
      Parent.queries.delete(Parent.queries.find(id));
    }
  }

}
