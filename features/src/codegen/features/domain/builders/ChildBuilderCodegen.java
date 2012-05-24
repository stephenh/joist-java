package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildBuilderCodegen extends AbstractBuilder<Child> {

  public ChildBuilderCodegen(Child instance) {
    super(instance);
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

  @Override
  public ChildBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (parent() == null) {
      parent(Builders.aParent().defaults());
    }
    return (ChildBuilder) super.defaults();
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
    return parent(parent.get());
  }

  public ChildBuilder with(ParentBuilder parent) {
    return parent(parent);
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
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (ChildBuilder) this;
  }

}
