package features.domain.builders;

import features.domain.ChildH;
import features.domain.ParentH;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildHBuilderCodegen extends AbstractBuilder<ChildH> {

  public ChildHBuilderCodegen(ChildH instance) {
    super(instance);
  }

  @Override
  public ChildHBuilder defaults() {
    return (ChildHBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
    if (quantity() == null) {
      quantity(defaultQuantity());
    }
    c.rememberIfSet(parent());
    if (parent() == null) {
      parent(c.getIfAvailable(ParentH.class));
      if (parent() == null) {
        parent(defaultParent());
        c.rememberIfSet(parent());
      }
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ChildHBuilder id(Long id) {
    get().setId(id);
    return (ChildHBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ChildHBuilder name(String name) {
    get().setName(name);
    return (ChildHBuilder) this;
  }

  public ChildHBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public Long quantity() {
    return get().getQuantity();
  }

  public ChildHBuilder quantity(Long quantity) {
    get().setQuantity(quantity);
    return (ChildHBuilder) this;
  }

  public ChildHBuilder with(Long quantity) {
    return quantity(quantity);
  }

  protected Long defaultQuantity() {
    return 0l;
  }

  public ParentHBuilder parent() {
    if (get().getParent() == null) {
      return null;
    }
    return Builders.existing(get().getParent());
  }

  public ChildHBuilder parent(ParentH parent) {
    get().setParent(parent);
    return (ChildHBuilder) this;
  }

  public ChildHBuilder with(ParentH parent) {
    return parent(parent);
  }

  public ChildHBuilder parent(ParentHBuilder parent) {
    return parent(parent == null ? null : parent.get());
  }

  public ChildHBuilder with(ParentHBuilder parent) {
    return parent(parent);
  }

  protected ParentHBuilder defaultParent() {
    return Builders.aParentH().defaults();
  }

  public ChildH get() {
    return (features.domain.ChildH) super.get();
  }

  @Override
  public ChildHBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildHBuilder) this;
  }

  @Override
  public ChildHBuilder use(AbstractBuilder<?> builder) {
    return (ChildHBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ChildH.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ChildH.queries.findAllIds();
    for (Long id : ids) {
      ChildH.queries.delete(ChildH.queries.find(id));
    }
  }

}
