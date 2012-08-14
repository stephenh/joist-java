package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class GrandChildBuilderCodegen extends AbstractBuilder<GrandChild> {

  public GrandChildBuilderCodegen(GrandChild instance) {
    super(instance);
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

  @Override
  public GrandChildBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (child() == null) {
      child(Builders.aChild().defaults());
    }
    return (GrandChildBuilder) super.defaults();
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
    return child(child.get());
  }

  public GrandChildBuilder with(ChildBuilder child) {
    return child(child);
  }

  public GrandChild get() {
    return (features.domain.GrandChild) super.get();
  }

  @Override
  public GrandChildBuilder ensureSaved() {
    doEnsureSaved();
    return (GrandChildBuilder) this;
  }

}
