package features.domain.builders;

import features.domain.Child;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentBuilderCodegen extends AbstractBuilder<Parent> {

  public ParentBuilderCodegen(Parent instance) {
    super(instance);
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

  @Override
  public ParentBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ParentBuilder) super.defaults();
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

}
