package features.domain.builders;

import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentCFooBuilderCodegen extends AbstractBuilder<ParentCFoo> {

  public ParentCFooBuilderCodegen(ParentCFoo instance) {
    super(instance);
  }

  @Override
  public ParentCFooBuilder defaults() {
    return (ParentCFooBuilder) super.defaults();
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

  public ParentCFooBuilder id(Long id) {
    get().setId(id);
    return (ParentCFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentCFooBuilder name(String name) {
    get().setName(name);
    return (ParentCFooBuilder) this;
  }

  public ParentCFooBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentCBarBuilder newFirstParentParentCBar() {
    return Builders.aParentCBar().firstParent((ParentCFooBuilder) this);
  }

  public List<ParentCBarBuilder> firstParentParentCBars() {
    List<ParentCBarBuilder> b = new ArrayList<ParentCBarBuilder>();
    for (ParentCBar e : get().getFirstParentParentCBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentCBarBuilder firstParentParentCBar(int i) {
    return Builders.existing(get().getFirstParentParentCBars().get(i));
  }

  public ParentCBarBuilder newSecondParentParentCBar() {
    return Builders.aParentCBar().secondParent((ParentCFooBuilder) this);
  }

  public List<ParentCBarBuilder> secondParentParentCBars() {
    List<ParentCBarBuilder> b = new ArrayList<ParentCBarBuilder>();
    for (ParentCBar e : get().getSecondParentParentCBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentCBarBuilder secondParentParentCBar(int i) {
    return Builders.existing(get().getSecondParentParentCBars().get(i));
  }

  public ParentCFoo get() {
    return (features.domain.ParentCFoo) super.get();
  }

  @Override
  public ParentCFooBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentCFooBuilder) this;
  }

  @Override
  public ParentCFooBuilder use(AbstractBuilder<?> builder) {
    return (ParentCFooBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentCFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentCFoo.queries.findAllIds();
    for (Long id : ids) {
      ParentCFoo.queries.delete(ParentCFoo.queries.find(id));
    }
  }

}
