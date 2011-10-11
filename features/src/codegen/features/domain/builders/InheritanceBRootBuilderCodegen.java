package features.domain.builders;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceBRootBuilderCodegen extends AbstractBuilder<InheritanceBRoot> {

  public InheritanceBRootBuilderCodegen(InheritanceBRoot instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public InheritanceBRootBuilder id(Long id) {
    get().setId(id);
    return (InheritanceBRootBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceBRootBuilder name(String name) {
    get().setName(name);
    return (InheritanceBRootBuilder) this;
  }

  public InheritanceBRootBuilder with(String name) {
    get().setName(name);
    return (InheritanceBRootBuilder) this;
  }

  public List<InheritanceBRootChildBuilder> inheritanceBRootChilds() {
    List<InheritanceBRootChildBuilder> b = new ArrayList<InheritanceBRootChildBuilder>();
    for (InheritanceBRootChild e : get().getInheritanceBRootChilds()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public InheritanceBRootChildBuilder inheritanceBRootChild(int i) {
    return Builders.existing(get().getInheritanceBRootChilds().get(i));
  }

  public InheritanceBRoot get() {
    return (features.domain.InheritanceBRoot) super.get();
  }

}
