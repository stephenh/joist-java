package features.domain.builders;

import features.domain.ParentBChildBar;
import features.domain.ParentBChildZaz;
import features.domain.ParentBParent;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentBChildZazBuilderCodegen extends AbstractBuilder<ParentBChildZaz> {

  public ParentBChildZazBuilderCodegen(ParentBChildZaz instance) {
    super(instance);
  }

  @Override
  public ParentBChildZazBuilder defaults() {
    return (ParentBChildZazBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    c.rememberIfSet(parentBChildBar());
    c.rememberIfSet(parentBParent());
    if (parentBChildBar() == null) {
      parentBChildBar(c.getIfAvailable(ParentBChildBar.class));
      if (parentBChildBar() == null) {
        parentBChildBar(defaultParentBChildBar());
        c.rememberIfSet(parentBChildBar());
      }
    }
    if (parentBParent() == null) {
      parentBParent(c.getIfAvailable(ParentBParent.class));
      if (parentBParent() == null) {
        parentBParent(defaultParentBParent());
        c.rememberIfSet(parentBParent());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ParentBChildZazBuilder id(Long id) {
    get().setId(id);
    return (ParentBChildZazBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentBChildZazBuilder name(String name) {
    get().setName(name);
    return (ParentBChildZazBuilder) this;
  }

  public ParentBChildZazBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentBChildBarBuilder parentBChildBar() {
    if (get().getParentBChildBar() == null) {
      return null;
    }
    return Builders.existing(get().getParentBChildBar());
  }

  public ParentBChildZazBuilder parentBChildBar(ParentBChildBar parentBChildBar) {
    get().setParentBChildBar(parentBChildBar);
    return (ParentBChildZazBuilder) this;
  }

  public ParentBChildZazBuilder with(ParentBChildBar parentBChildBar) {
    return parentBChildBar(parentBChildBar);
  }

  public ParentBChildZazBuilder parentBChildBar(ParentBChildBarBuilder parentBChildBar) {
    return parentBChildBar(parentBChildBar == null ? null : parentBChildBar.get());
  }

  public ParentBChildZazBuilder with(ParentBChildBarBuilder parentBChildBar) {
    return parentBChildBar(parentBChildBar);
  }

  protected ParentBChildBarBuilder defaultParentBChildBar() {
    return Builders.aParentBChildBar().defaults();
  }

  public ParentBParentBuilder parentBParent() {
    if (get().getParentBParent() == null) {
      return null;
    }
    return Builders.existing(get().getParentBParent());
  }

  public ParentBChildZazBuilder parentBParent(ParentBParent parentBParent) {
    get().setParentBParent(parentBParent);
    return (ParentBChildZazBuilder) this;
  }

  public ParentBChildZazBuilder with(ParentBParent parentBParent) {
    return parentBParent(parentBParent);
  }

  public ParentBChildZazBuilder parentBParent(ParentBParentBuilder parentBParent) {
    return parentBParent(parentBParent == null ? null : parentBParent.get());
  }

  public ParentBChildZazBuilder with(ParentBParentBuilder parentBParent) {
    return parentBParent(parentBParent);
  }

  protected ParentBParentBuilder defaultParentBParent() {
    return Builders.aParentBParent().defaults();
  }

  public ParentBChildZaz get() {
    return (features.domain.ParentBChildZaz) super.get();
  }

  @Override
  public ParentBChildZazBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentBChildZazBuilder) this;
  }

  @Override
  public ParentBChildZazBuilder use(AbstractBuilder<?> builder) {
    return (ParentBChildZazBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentBChildZaz.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentBChildZaz.queries.findAllIds();
    for (Long id : ids) {
      ParentBChildZaz.queries.delete(ParentBChildZaz.queries.find(id));
    }
  }

}
