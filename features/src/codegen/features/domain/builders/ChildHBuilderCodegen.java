package features.domain.builders;

import features.domain.ChildH;
import features.domain.ParentH;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildHBuilderCodegen extends AbstractBuilder<ChildH> {

  public ChildHBuilderCodegen(ChildH instance) {
    super(instance);
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

  @Override
  public ChildHBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (quantity() == null) {
      quantity(0l);
    }
    if (parent() == null) {
      parent(Builders.aParentH().defaults());
    }
    return (ChildHBuilder) super.defaults();
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
    return parent(parent.get());
  }

  public ChildHBuilder with(ParentHBuilder parent) {
    return parent(parent);
  }

  public ChildH get() {
    return (features.domain.ChildH) super.get();
  }

  @Override
  public ChildHBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildHBuilder) this;
  }

}
