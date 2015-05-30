package features.domain.builders;

import features.domain.ParentBChildBar;
import features.domain.ParentBChildFoo;
import features.domain.ParentBChildZaz;
import features.domain.ParentBParent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentBParentBuilderCodegen extends AbstractBuilder<ParentBParent> {

  public ParentBParentBuilderCodegen(ParentBParent instance) {
    super(instance);
  }

  @Override
  public ParentBParentBuilder defaults() {
    return (ParentBParentBuilder) super.defaults();
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

  public ParentBParentBuilder id(Long id) {
    get().setId(id);
    return (ParentBParentBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentBParentBuilder name(String name) {
    get().setName(name);
    return (ParentBParentBuilder) this;
  }

  public ParentBParentBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentBChildBarBuilder newParentBChildBar() {
    return Builders.aParentBChildBar().parentBParent((ParentBParentBuilder) this);
  }

  public List<ParentBChildBarBuilder> parentBChildBars() {
    List<ParentBChildBarBuilder> b = new ArrayList<ParentBChildBarBuilder>();
    for (ParentBChildBar e : get().getParentBChildBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentBChildBarBuilder parentBChildBar(int i) {
    return Builders.existing(get().getParentBChildBars().get(i));
  }

  public ParentBChildFooBuilder newParentBChildFoo() {
    return Builders.aParentBChildFoo().parentBParent((ParentBParentBuilder) this);
  }

  public List<ParentBChildFooBuilder> parentBChildFoos() {
    List<ParentBChildFooBuilder> b = new ArrayList<ParentBChildFooBuilder>();
    for (ParentBChildFoo e : get().getParentBChildFoos()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentBChildFooBuilder parentBChildFoo(int i) {
    return Builders.existing(get().getParentBChildFoos().get(i));
  }

  public ParentBChildZazBuilder newParentBChildZaz() {
    return Builders.aParentBChildZaz().parentBParent((ParentBParentBuilder) this);
  }

  public List<ParentBChildZazBuilder> parentBChildZazs() {
    List<ParentBChildZazBuilder> b = new ArrayList<ParentBChildZazBuilder>();
    for (ParentBChildZaz e : get().getParentBChildZazs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentBChildZazBuilder parentBChildZaz(int i) {
    return Builders.existing(get().getParentBChildZazs().get(i));
  }

  public ParentBParent get() {
    return (features.domain.ParentBParent) super.get();
  }

  @Override
  public ParentBParentBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentBParentBuilder) this;
  }

  @Override
  public ParentBParentBuilder use(AbstractBuilder<?> builder) {
    return (ParentBParentBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentBParent.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentBParent.queries.findAllIds();
    for (Long id : ids) {
      ParentBParent.queries.delete(ParentBParent.queries.find(id));
    }
  }

}
