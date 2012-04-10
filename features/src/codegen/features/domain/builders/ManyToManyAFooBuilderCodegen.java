package features.domain.builders;

import features.domain.ManyToManyAFoo;
import features.domain.ManyToManyAFooToBar;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyAFooBuilderCodegen extends AbstractBuilder<ManyToManyAFoo> {

  public ManyToManyAFooBuilderCodegen(ManyToManyAFoo instance) {
    super(instance);
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

  @Override
  public ManyToManyAFooBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ManyToManyAFooBuilder) super.defaults();
  }

  public List<ManyToManyAFooToBarBuilder> manyToManyAFooToBars() {
    List<ManyToManyAFooToBarBuilder> b = new ArrayList<ManyToManyAFooToBarBuilder>();
    for (ManyToManyAFooToBar e : get().getManyToManyAFooToBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ManyToManyAFooToBarBuilder manyToManyAFooToBar(int i) {
    return Builders.existing(get().getManyToManyAFooToBars().get(i));
  }

  public ManyToManyAFoo get() {
    return (features.domain.ManyToManyAFoo) super.get();
  }

}
