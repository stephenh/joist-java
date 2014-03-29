package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyBBarBuilderCodegen extends AbstractBuilder<ManyToManyBBar> {

  public ManyToManyBBarBuilderCodegen(ManyToManyBBar instance) {
    super(instance);
  }

  @Override
  public ManyToManyBBarBuilder defaults() {
    return (ManyToManyBBarBuilder) super.defaults();
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

  public ManyToManyBBarBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyBBarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ManyToManyBBarBuilder name(String name) {
    get().setName(name);
    return (ManyToManyBBarBuilder) this;
  }

  public ManyToManyBBarBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public List<ManyToManyBFooBuilder> ownerManyToManyBFoos() {
    List<ManyToManyBFooBuilder> b = new ArrayList<ManyToManyBFooBuilder>();
    for (ManyToManyBFoo e : get().getOwnerManyToManyBFoos()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ManyToManyBFooBuilder ownerManyToManyBFoo(int i) {
    return Builders.existing(get().getOwnerManyToManyBFoos().get(i));
  }

  public ManyToManyBBarBuilder ownerManyToManyBFoo(ManyToManyBFoo ownerManyToManyBFoos) {
    get().addOwnerManyToManyBFoo(ownerManyToManyBFoos);
    return (ManyToManyBBarBuilder) this;
  }

  public ManyToManyBBarBuilder ownerManyToManyBFoo(ManyToManyBFooBuilder ownerManyToManyBFoos) {
    get().addOwnerManyToManyBFoo(ownerManyToManyBFoos.get());
    return (ManyToManyBBarBuilder) this;
  }

  public ManyToManyBBarBuilder with(ManyToManyBFoo ownerManyToManyBFoos) {
    get().addOwnerManyToManyBFoo(ownerManyToManyBFoos);
    return (ManyToManyBBarBuilder) this;
  }

  public ManyToManyBBarBuilder with(ManyToManyBFooBuilder ownerManyToManyBFoos) {
    get().addOwnerManyToManyBFoo(ownerManyToManyBFoos.get());
    return (ManyToManyBBarBuilder) this;
  }

  public ManyToManyBBar get() {
    return (features.domain.ManyToManyBBar) super.get();
  }

  @Override
  public ManyToManyBBarBuilder ensureSaved() {
    doEnsureSaved();
    return (ManyToManyBBarBuilder) this;
  }

  @Override
  public ManyToManyBBarBuilder use(AbstractBuilder<?> builder) {
    return (ManyToManyBBarBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ManyToManyBBar.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ManyToManyBBar.queries.findAllIds();
    for (Long id : ids) {
      ManyToManyBBar.queries.delete(ManyToManyBBar.queries.find(id));
    }
  }

}
