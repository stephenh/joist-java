package features.domain.builders;

import features.domain.ManyToManyABar;
import features.domain.ManyToManyAFoo;
import features.domain.ManyToManyAFooToBar;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyAFooToBarBuilderCodegen extends AbstractBuilder<ManyToManyAFooToBar> {

  public ManyToManyAFooToBarBuilderCodegen(ManyToManyAFooToBar instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ManyToManyAFooToBarBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyAFooToBarBuilder) this;
  }

  public ManyToManyABarBuilder manyToManyABar() {
    if (get().getManyToManyABar() == null) {
      return null;
    }
    return Builders.existing(get().getManyToManyABar());
  }

  public ManyToManyAFooToBarBuilder manyToManyABar(ManyToManyABar manyToManyABar) {
    get().setManyToManyABar(manyToManyABar);
    return (ManyToManyAFooToBarBuilder) this;
  }

  public ManyToManyAFooToBarBuilder with(ManyToManyABar manyToManyABar) {
    return manyToManyABar(manyToManyABar);
  }

  public ManyToManyAFooToBarBuilder manyToManyABar(ManyToManyABarBuilder manyToManyABar) {
    return manyToManyABar(manyToManyABar == null ? null : manyToManyABar.get());
  }

  public ManyToManyAFooToBarBuilder with(ManyToManyABarBuilder manyToManyABar) {
    return manyToManyABar(manyToManyABar);
  }

  @Override
  public ManyToManyAFooToBarBuilder defaults() {
    if (manyToManyABar() == null) {
      manyToManyABar(Builders.aManyToManyABar().defaults());
    }
    if (manyToManyAFoo() == null) {
      manyToManyAFoo(Builders.aManyToManyAFoo().defaults());
    }
    return (ManyToManyAFooToBarBuilder) super.defaults();
  }

  public ManyToManyAFooBuilder manyToManyAFoo() {
    if (get().getManyToManyAFoo() == null) {
      return null;
    }
    return Builders.existing(get().getManyToManyAFoo());
  }

  public ManyToManyAFooToBarBuilder manyToManyAFoo(ManyToManyAFoo manyToManyAFoo) {
    get().setManyToManyAFoo(manyToManyAFoo);
    return (ManyToManyAFooToBarBuilder) this;
  }

  public ManyToManyAFooToBarBuilder with(ManyToManyAFoo manyToManyAFoo) {
    return manyToManyAFoo(manyToManyAFoo);
  }

  public ManyToManyAFooToBarBuilder manyToManyAFoo(ManyToManyAFooBuilder manyToManyAFoo) {
    return manyToManyAFoo(manyToManyAFoo == null ? null : manyToManyAFoo.get());
  }

  public ManyToManyAFooToBarBuilder with(ManyToManyAFooBuilder manyToManyAFoo) {
    return manyToManyAFoo(manyToManyAFoo);
  }

  public ManyToManyAFooToBar get() {
    return (features.domain.ManyToManyAFooToBar) super.get();
  }

  @Override
  public ManyToManyAFooToBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ManyToManyAFooToBarBuilder) this;
  }

  @Override
  public void delete() {
    ManyToManyAFooToBar.queries.delete(get());
  }

}
