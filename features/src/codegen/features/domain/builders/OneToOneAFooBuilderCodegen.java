package features.domain.builders;

import features.domain.OneToOneAFoo;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class OneToOneAFooBuilderCodegen extends AbstractBuilder<OneToOneAFoo> {

  public OneToOneAFooBuilderCodegen(OneToOneAFoo instance) {
    super(instance);
  }

  @Override
  public OneToOneAFooBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      return (OneToOneAFooBuilder) super.defaults();
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

  public OneToOneAFooBuilder id(Long id) {
    get().setId(id);
    return (OneToOneAFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public OneToOneAFooBuilder name(String name) {
    get().setName(name);
    return (OneToOneAFooBuilder) this;
  }

  public OneToOneAFooBuilder with(String name) {
    return name(name);
  }

  public OneToOneABarBuilder oneToOneABar() {
    if (get().getOneToOneABar() == null) {
      return null;
    }
    return Builders.existing(get().getOneToOneABar());
  }

  public OneToOneABarBuilder newOneToOneABar() {
    return Builders.aOneToOneABar().oneToOneAFoo((OneToOneAFooBuilder) this);
  }

  public OneToOneAFoo get() {
    return (features.domain.OneToOneAFoo) super.get();
  }

  @Override
  public OneToOneAFooBuilder ensureSaved() {
    doEnsureSaved();
    return (OneToOneAFooBuilder) this;
  }

  @Override
  public void delete() {
    OneToOneAFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = OneToOneAFoo.queries.findAllIds();
    for (Long id : ids) {
      OneToOneAFoo.queries.delete(OneToOneAFoo.queries.find(id));
    }
  }

}
