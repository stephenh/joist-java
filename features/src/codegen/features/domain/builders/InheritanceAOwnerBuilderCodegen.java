package features.domain.builders;

import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceAOwnerBuilderCodegen extends AbstractBuilder<InheritanceAOwner> {

  public InheritanceAOwnerBuilderCodegen(InheritanceAOwner instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
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
    return name(name);
  }

  @Override
  public InheritanceAOwnerBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (InheritanceAOwnerBuilder) super.defaults();
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

  @Override
  public InheritanceAOwnerBuilder ensureSaved() {
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (InheritanceAOwnerBuilder) this;
  }

}
