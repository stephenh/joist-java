package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyBFooToBarBuilderCodegen extends AbstractBuilder<ManyToManyBFooToBar> {

  public ManyToManyBFooToBarBuilderCodegen(ManyToManyBFooToBar instance) {
    super(instance);
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

  @Override
  public ManyToManyBFooToBarBuilder defaults() {
    if (owned() == null) {
      owned(Builders.aManyToManyBBar().defaults());
    }
    if (ownerManyToManyBFoo() == null) {
      ownerManyToManyBFoo(Builders.aManyToManyBFoo().defaults());
    }
    return (ManyToManyBFooToBarBuilder) super.defaults();
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

  public ManyToManyBFooToBar get() {
    return (features.domain.ManyToManyBFooToBar) super.get();
  }

  @Override
  public ManyToManyBFooToBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ManyToManyBFooToBarBuilder) this;
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
