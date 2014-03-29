package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyBFooToBarBuilderCodegen extends AbstractBuilder<ManyToManyBFooToBar> {

  public ManyToManyBFooToBarBuilderCodegen(ManyToManyBFooToBar instance) {
    super(instance);
  }

  @Override
  public ManyToManyBFooToBarBuilder defaults() {
    return (ManyToManyBFooToBarBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    c.rememberIfSet(owned());
    c.rememberIfSet(ownerManyToManyBFoo());
    if (owned() == null) {
      owned(c.getIfAvailable(ManyToManyBBar.class));
      if (owned() == null) {
        owned(defaultOwned());
        c.rememberIfSet(owned());
      }
    }
    if (ownerManyToManyBFoo() == null) {
      ownerManyToManyBFoo(c.getIfAvailable(ManyToManyBFoo.class));
      if (ownerManyToManyBFoo() == null) {
        ownerManyToManyBFoo(defaultOwnerManyToManyBFoo());
        c.rememberIfSet(ownerManyToManyBFoo());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ManyToManyBFooToBarBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyBFooToBarBuilder) this;
  }

  public ManyToManyBBarBuilder owned() {
    if (get().getOwned() == null) {
      return null;
    }
    return Builders.existing(get().getOwned());
  }

  public ManyToManyBFooToBarBuilder owned(ManyToManyBBar owned) {
    get().setOwned(owned);
    return (ManyToManyBFooToBarBuilder) this;
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBBar owned) {
    return owned(owned);
  }

  public ManyToManyBFooToBarBuilder owned(ManyToManyBBarBuilder owned) {
    return owned(owned == null ? null : owned.get());
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBBarBuilder owned) {
    return owned(owned);
  }

  protected ManyToManyBBarBuilder defaultOwned() {
    return Builders.aManyToManyBBar().defaults();
  }

  public ManyToManyBFooBuilder ownerManyToManyBFoo() {
    if (get().getOwnerManyToManyBFoo() == null) {
      return null;
    }
    return Builders.existing(get().getOwnerManyToManyBFoo());
  }

  public ManyToManyBFooToBarBuilder ownerManyToManyBFoo(ManyToManyBFoo ownerManyToManyBFoo) {
    get().setOwnerManyToManyBFoo(ownerManyToManyBFoo);
    return (ManyToManyBFooToBarBuilder) this;
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBFoo ownerManyToManyBFoo) {
    return ownerManyToManyBFoo(ownerManyToManyBFoo);
  }

  public ManyToManyBFooToBarBuilder ownerManyToManyBFoo(ManyToManyBFooBuilder ownerManyToManyBFoo) {
    return ownerManyToManyBFoo(ownerManyToManyBFoo == null ? null : ownerManyToManyBFoo.get());
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBFooBuilder ownerManyToManyBFoo) {
    return ownerManyToManyBFoo(ownerManyToManyBFoo);
  }

  protected ManyToManyBFooBuilder defaultOwnerManyToManyBFoo() {
    return Builders.aManyToManyBFoo().defaults();
  }

  public ManyToManyBFooToBar get() {
    return (features.domain.ManyToManyBFooToBar) super.get();
  }

  @Override
  public ManyToManyBFooToBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ManyToManyBFooToBarBuilder) this;
  }

  @Override
  public ManyToManyBFooToBarBuilder use(AbstractBuilder<?> builder) {
    return (ManyToManyBFooToBarBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ManyToManyBFooToBar.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ManyToManyBFooToBar.queries.findAllIds();
    for (Long id : ids) {
      ManyToManyBFooToBar.queries.delete(ManyToManyBFooToBar.queries.find(id));
    }
  }

}
