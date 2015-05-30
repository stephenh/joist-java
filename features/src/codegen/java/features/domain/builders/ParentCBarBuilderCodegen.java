package features.domain.builders;

import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentCBarBuilderCodegen extends AbstractBuilder<ParentCBar> {

  public ParentCBarBuilderCodegen(ParentCBar instance) {
    super(instance);
  }

  @Override
  public ParentCBarBuilder defaults() {
    return (ParentCBarBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(firstParent());
    c.rememberIfSet(secondParent());
    if (firstParent() == null) {
      firstParent(c.getIfAvailable(ParentCFoo.class));
      if (firstParent() == null) {
        firstParent(defaultFirstParent());
        c.rememberIfSet(firstParent());
      }
    }
    if (secondParent() == null) {
      secondParent(c.getIfAvailable(ParentCFoo.class));
      if (secondParent() == null) {
        secondParent(defaultSecondParent());
        c.rememberIfSet(secondParent());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentCBarBuilder id(Long id) {
    get().setId(id);
    return (ParentCBarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentCBarBuilder name(String name) {
    get().setName(name);
    return (ParentCBarBuilder) this;
  }

  public ParentCBarBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentCFooBuilder firstParent() {
    if (get().getFirstParent() == null) {
      return null;
    }
    return Builders.existing(get().getFirstParent());
  }

  public ParentCBarBuilder firstParent(ParentCFoo firstParent) {
    get().setFirstParent(firstParent);
    return (ParentCBarBuilder) this;
  }

  public ParentCBarBuilder firstParent(ParentCFooBuilder firstParent) {
    return firstParent(firstParent == null ? null : firstParent.get());
  }

  protected ParentCFooBuilder defaultFirstParent() {
    return Builders.aParentCFoo().defaults();
  }

  public ParentCFooBuilder secondParent() {
    if (get().getSecondParent() == null) {
      return null;
    }
    return Builders.existing(get().getSecondParent());
  }

  public ParentCBarBuilder secondParent(ParentCFoo secondParent) {
    get().setSecondParent(secondParent);
    return (ParentCBarBuilder) this;
  }

  public ParentCBarBuilder secondParent(ParentCFooBuilder secondParent) {
    return secondParent(secondParent == null ? null : secondParent.get());
  }

  protected ParentCFooBuilder defaultSecondParent() {
    return Builders.aParentCFoo().defaults();
  }

  public ParentCBar get() {
    return (features.domain.ParentCBar) super.get();
  }

  @Override
  public ParentCBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentCBarBuilder) this;
  }

  @Override
  public ParentCBarBuilder use(AbstractBuilder<?> builder) {
    return (ParentCBarBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentCBar.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentCBar.queries.findAllIds();
    for (Long id : ids) {
      ParentCBar.queries.delete(ParentCBar.queries.find(id));
    }
  }

}
