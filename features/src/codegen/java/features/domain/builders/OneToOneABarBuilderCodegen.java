package features.domain.builders;

import features.domain.OneToOneABar;
import features.domain.OneToOneAFoo;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class OneToOneABarBuilderCodegen extends AbstractBuilder<OneToOneABar> {

  public OneToOneABarBuilderCodegen(OneToOneABar instance) {
    super(instance);
  }

  @Override
  public OneToOneABarBuilder defaults() {
    return (OneToOneABarBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(oneToOneAFoo());
    if (oneToOneAFoo() == null) {
      oneToOneAFoo(c.getIfAvailable(OneToOneAFoo.class));
      if (oneToOneAFoo() == null) {
        oneToOneAFoo(defaultOneToOneAFoo());
        c.rememberIfSet(oneToOneAFoo());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public OneToOneABarBuilder id(Long id) {
    get().setId(id);
    return (OneToOneABarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public OneToOneABarBuilder name(String name) {
    get().setName(name);
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABarBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public OneToOneAFooBuilder oneToOneAFoo() {
    if (get().getOneToOneAFoo() == null) {
      return null;
    }
    return Builders.existing(get().getOneToOneAFoo());
  }

  public OneToOneABarBuilder oneToOneAFoo(OneToOneAFoo oneToOneAFoo) {
    get().setOneToOneAFoo(oneToOneAFoo);
    return (OneToOneABarBuilder) this;
  }

  public OneToOneABarBuilder with(OneToOneAFoo oneToOneAFoo) {
    return oneToOneAFoo(oneToOneAFoo);
  }

  public OneToOneABarBuilder oneToOneAFoo(OneToOneAFooBuilder oneToOneAFoo) {
    return oneToOneAFoo(oneToOneAFoo == null ? null : oneToOneAFoo.get());
  }

  public OneToOneABarBuilder with(OneToOneAFooBuilder oneToOneAFoo) {
    return oneToOneAFoo(oneToOneAFoo);
  }

  protected OneToOneAFooBuilder defaultOneToOneAFoo() {
    return Builders.aOneToOneAFoo().defaults();
  }

  public OneToOneABar get() {
    return (features.domain.OneToOneABar) super.get();
  }

  @Override
  public OneToOneABarBuilder ensureSaved() {
    doEnsureSaved();
    return (OneToOneABarBuilder) this;
  }

  @Override
  public OneToOneABarBuilder use(AbstractBuilder<?> builder) {
    return (OneToOneABarBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    OneToOneABar.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = OneToOneABar.queries.findAllIds();
    for (Long id : ids) {
      OneToOneABar.queries.delete(OneToOneABar.queries.find(id));
    }
  }

}
