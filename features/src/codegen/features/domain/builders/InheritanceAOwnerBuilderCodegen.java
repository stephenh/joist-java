package features.domain.builders;

import java.util.ArrayList;
import java.util.List;

import joist.domain.builders.AbstractBuilder;
import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;

@SuppressWarnings("all")
public abstract class InheritanceAOwnerBuilderCodegen extends AbstractBuilder<InheritanceAOwner> {

  public InheritanceAOwnerBuilderCodegen(InheritanceAOwner instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public InheritanceAOwnerBuilder id(Long id) {
    get().setId(id);
    return (InheritanceAOwnerBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceAOwnerBuilder name(String name) {
    get().setName(name);
    return (InheritanceAOwnerBuilder) this;
  }

  public InheritanceAOwnerBuilder with(String name) {
    get().setName(name);
    return (InheritanceAOwnerBuilder) this;
  }

  public List<InheritanceABaseBuilder> inheritanceABases() {
    List<InheritanceABaseBuilder> b = new ArrayList<InheritanceABaseBuilder>();
    for (InheritanceABase e : get().getInheritanceABases()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public InheritanceABaseBuilder inheritanceABase(int i) {
    return Builders.existing(get().getInheritanceABases().get(i));
  }

  public InheritanceAOwner get() {
    return (features.domain.InheritanceAOwner) super.get();
  }

}
