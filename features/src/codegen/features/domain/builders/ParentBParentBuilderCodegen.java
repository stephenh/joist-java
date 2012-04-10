package features.domain.builders;

import features.domain.ParentBChildBar;
import features.domain.ParentBChildFoo;
import features.domain.ParentBParent;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentBParentBuilderCodegen extends AbstractBuilder<ParentBParent> {

  public ParentBParentBuilderCodegen(ParentBParent instance) {
    super(instance);
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

  @Override
  public ParentBParentBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ParentBParentBuilder) super.defaults();
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

  public ParentBParent get() {
    return (features.domain.ParentBParent) super.get();
  }

}
