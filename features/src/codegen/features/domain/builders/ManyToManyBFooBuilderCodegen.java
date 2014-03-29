package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyBFooBuilderCodegen extends AbstractBuilder<ManyToManyBFoo> {

  public ManyToManyBFooBuilderCodegen(ManyToManyBFoo instance) {
    super(instance);
  }

  @Override
  public ManyToManyBFooBuilder defaults() {
    return (ManyToManyBFooBuilder) super.defaults();
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

  public ManyToManyBFooBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyBFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ManyToManyBFooBuilder name(String name) {
    get().setName(name);
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public List<ManyToManyBBarBuilder> owneds() {
    List<ManyToManyBBarBuilder> b = new ArrayList<ManyToManyBBarBuilder>();
    for (ManyToManyBBar e : get().getOwneds()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ManyToManyBBarBuilder owned(int i) {
    return Builders.existing(get().getOwneds().get(i));
  }

  public ManyToManyBFooBuilder owned(ManyToManyBBar owneds) {
    get().addOwned(owneds);
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder owned(ManyToManyBBarBuilder owneds) {
    get().addOwned(owneds.get());
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder with(ManyToManyBBar owneds) {
    get().addOwned(owneds);
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder with(ManyToManyBBarBuilder owneds) {
    get().addOwned(owneds.get());
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFoo get() {
    return (features.domain.ManyToManyBFoo) super.get();
  }

  @Override
  public ManyToManyBFooBuilder ensureSaved() {
    doEnsureSaved();
    return (ManyToManyBFooBuilder) this;
  }

  @Override
  public ManyToManyBFooBuilder use(AbstractBuilder<?> builder) {
    return (ManyToManyBFooBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ManyToManyBFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ManyToManyBFoo.queries.findAllIds();
    for (Long id : ids) {
      ManyToManyBFoo.queries.delete(ManyToManyBFoo.queries.find(id));
    }
  }

}
