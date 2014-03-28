package features.domain.builders;

import features.domain.OneToOneBBar;
import features.domain.OneToOneBFoo;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class OneToOneBFooBuilderCodegen extends AbstractBuilder<OneToOneBFoo> {

  public OneToOneBFooBuilderCodegen(OneToOneBFoo instance) {
    super(instance);
  }

  @Override
  public OneToOneBFooBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      return (OneToOneBFooBuilder) super.defaults();
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

  public OneToOneBFooBuilder id(Long id) {
    get().setId(id);
    return (OneToOneBFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public OneToOneBFooBuilder name(String name) {
    get().setName(name);
    return (OneToOneBFooBuilder) this;
  }

  public OneToOneBFooBuilder with(String name) {
    return name(name);
  }

  public List<OneToOneBBarBuilder> oneToOneBBars() {
    List<OneToOneBBarBuilder> b = new ArrayList<OneToOneBBarBuilder>();
    for (OneToOneBBar e : get().getOneToOneBBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public OneToOneBBarBuilder oneToOneBBar(int i) {
    return Builders.existing(get().getOneToOneBBars().get(i));
  }

  public OneToOneBBarBuilder newOneToOneBBar() {
    return Builders.aOneToOneBBar().oneToOneBFoo((OneToOneBFooBuilder) this);
  }

  public OneToOneBFoo get() {
    return (features.domain.OneToOneBFoo) super.get();
  }

  @Override
  public OneToOneBFooBuilder ensureSaved() {
    doEnsureSaved();
    return (OneToOneBFooBuilder) this;
  }

  @Override
  public void delete() {
    OneToOneBFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = OneToOneBFoo.queries.findAllIds();
    for (Long id : ids) {
      OneToOneBFoo.queries.delete(OneToOneBFoo.queries.find(id));
    }
  }

}
