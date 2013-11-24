package features.domain.builders;

import features.domain.ChildF;
import features.domain.ParentF;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ChildFBuilderCodegen extends AbstractBuilder<ChildF> {

  public ChildFBuilderCodegen(ChildF instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ChildFBuilder id(Long id) {
    get().setId(id);
    return (ChildFBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ChildFBuilder name(String name) {
    get().setName(name);
    return (ChildFBuilder) this;
  }

  public ChildFBuilder with(String name) {
    return name(name);
  }

  @Override
  public ChildFBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ChildFBuilder) super.defaults();
  }

  public List<ParentFBuilder> childOneParentFs() {
    List<ParentFBuilder> b = new ArrayList<ParentFBuilder>();
    for (ParentF e : get().getChildOneParentFs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentFBuilder childOneParentF(int i) {
    return Builders.existing(get().getChildOneParentFs().get(i));
  }

  public ParentFBuilder newChildOneParentF() {
    return Builders.aParentF().childOne((ChildFBuilder) this);
  }

  public List<ParentFBuilder> childTwoParentFs() {
    List<ParentFBuilder> b = new ArrayList<ParentFBuilder>();
    for (ParentF e : get().getChildTwoParentFs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentFBuilder childTwoParentF(int i) {
    return Builders.existing(get().getChildTwoParentFs().get(i));
  }

  public ParentFBuilder newChildTwoParentF() {
    return Builders.aParentF().childTwo((ChildFBuilder) this);
  }

  public ChildF get() {
    return (features.domain.ChildF) super.get();
  }

  @Override
  public ChildFBuilder ensureSaved() {
    doEnsureSaved();
    return (ChildFBuilder) this;
  }

  @Override
  public void delete() {
    ChildF.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ChildF.queries.findAllIds();
    for (Long id : ids) {
      ChildF.queries.delete(ChildF.queries.find(id));
    }
  }

}
