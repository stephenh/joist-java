package features.domain.builders;

import features.domain.Child;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentBuilderCodegen extends AbstractBuilder<Parent> {

  public ParentBuilderCodegen(Parent instance) {
    super(instance);
  }

  public Long id() {
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

}
