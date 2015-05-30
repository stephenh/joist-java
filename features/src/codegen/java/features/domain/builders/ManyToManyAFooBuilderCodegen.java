package features.domain.builders;

import features.domain.ManyToManyABar;
import features.domain.ManyToManyAFoo;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyAFooBuilderCodegen extends AbstractBuilder<ManyToManyAFoo> {

  public ManyToManyAFooBuilderCodegen(ManyToManyAFoo instance) {
    super(instance);
  }

  @Override
  public ManyToManyAFooBuilder defaults() {
    return (ManyToManyAFooBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ManyToManyAFooBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyAFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ManyToManyAFooBuilder name(String name) {
    get().setName(name);
    return (ManyToManyAFooBuilder) this;
  }

  public ManyToManyAFooBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public List<ManyToManyABarBuilder> manyToManyABars() {
    List<ManyToManyABarBuilder> b = new ArrayList<ManyToManyABarBuilder>();
    for (ManyToManyABar e : get().getManyToManyABars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ManyToManyABarBuilder manyToManyABar(int i) {
    return Builders.existing(get().getManyToManyABars().get(i));
  }

  public ManyToManyAFooBuilder manyToManyABar(ManyToManyABar manyToManyABars) {
    get().addManyToManyABar(manyToManyABars);
    return (ManyToManyAFooBuilder) this;
  }

  public ManyToManyAFooBuilder manyToManyABar(ManyToManyABarBuilder manyToManyABars) {
    get().addManyToManyABar(manyToManyABars.get());
    return (ManyToManyAFooBuilder) this;
  }

  public ManyToManyAFooBuilder with(ManyToManyABar manyToManyABars) {
    get().addManyToManyABar(manyToManyABars);
    return (ManyToManyAFooBuilder) this;
  }

  public ManyToManyAFooBuilder with(ManyToManyABarBuilder manyToManyABars) {
    get().addManyToManyABar(manyToManyABars.get());
    return (ManyToManyAFooBuilder) this;
  }

  public ManyToManyAFoo get() {
    return (features.domain.ManyToManyAFoo) super.get();
  }

  @Override
  public ManyToManyAFooBuilder ensureSaved() {
    doEnsureSaved();
    return (ManyToManyAFooBuilder) this;
  }

  @Override
  public ManyToManyAFooBuilder use(AbstractBuilder<?> builder) {
    return (ManyToManyAFooBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ManyToManyAFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ManyToManyAFoo.queries.findAllIds();
    for (Long id : ids) {
      ManyToManyAFoo.queries.delete(ManyToManyAFoo.queries.find(id));
    }
  }

}
