package features.domain.builders;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceBRootChildBuilderCodegen extends AbstractBuilder<InheritanceBRootChild> {

  public InheritanceBRootChildBuilderCodegen(InheritanceBRootChild instance) {
    super(instance);
  }

  public Long id() {
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
    return inheritanceBRoot(inheritanceBRoot.get());
  }

  public InheritanceBRootChildBuilder with(InheritanceBRootBuilder inheritanceBRoot) {
    return inheritanceBRoot(inheritanceBRoot);
  }

  public InheritanceBRootChild get() {
    return (features.domain.InheritanceBRootChild) super.get();
  }

}
