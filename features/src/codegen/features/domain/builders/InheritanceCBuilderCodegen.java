package features.domain.builders;

import features.domain.InheritanceC;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceCBuilderCodegen extends AbstractBuilder<InheritanceC> {

  public InheritanceCBuilderCodegen(InheritanceC instance) {
    super(instance);
  }

  @Override
  public InheritanceCBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name(defaultName());
      }
      return (InheritanceCBuilder) super.defaults();
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

  public InheritanceCBuilder id(Long id) {
    get().setId(id);
    return (InheritanceCBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceCBuilder name(String name) {
    get().setName(name);
    return (InheritanceCBuilder) this;
  }

  protected String defaultName() {
    return "name";
  }

  public InheritanceC get() {
    return (features.domain.InheritanceC) super.get();
  }

  @Override
  public InheritanceCBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceCBuilder) this;
  }

  @Override
  public void delete() {
    InheritanceC.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = InheritanceC.queries.findAllIds();
    for (Long id : ids) {
      InheritanceC.queries.delete(InheritanceC.queries.find(id));
    }
  }

}
