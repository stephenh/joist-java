package features.domain.builders;

import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceAOwnerBuilderCodegen extends AbstractBuilder<InheritanceAOwner> {

  public InheritanceAOwnerBuilderCodegen(InheritanceAOwner instance) {
    super(instance);
  }

  @Override
  public InheritanceAOwnerBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      return (InheritanceAOwnerBuilder) super.defaults();
    } finally {
      DefaultsContext.pop();
    }
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
    doEnsureSaved();
    return (InheritanceAOwnerBuilder) this;
  }

  @Override
  public void delete() {
    InheritanceAOwner.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = InheritanceAOwner.queries.findAllIds();
    for (Long id : ids) {
      InheritanceAOwner.queries.delete(InheritanceAOwner.queries.find(id));
    }
  }

}
