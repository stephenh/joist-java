package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyBFooBuilderCodegen extends AbstractBuilder<ManyToManyBFoo> {

  public ManyToManyBFooBuilderCodegen(ManyToManyBFoo instance) {
    super(instance);
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

  @Override
  public ManyToManyBFooBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ManyToManyBFooBuilder) super.defaults();
  }

  public List<ManyToManyBFooToBarBuilder> blueManyToManyBFooToBars() {
    List<ManyToManyBFooToBarBuilder> b = new ArrayList<ManyToManyBFooToBarBuilder>();
    for (ManyToManyBFooToBar e : get().getBlueManyToManyBFooToBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ManyToManyBFooToBarBuilder blueManyToManyBFooToBar(int i) {
    return Builders.existing(get().getBlueManyToManyBFooToBars().get(i));
  }

  public List<ManyToManyBBarBuilder> greens() {
    List<ManyToManyBBarBuilder> b = new ArrayList<ManyToManyBBarBuilder>();
    for (ManyToManyBBar e : get().getGreens()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ManyToManyBBarBuilder green(int i) {
    return Builders.existing(get().getGreens().get(i));
  }

  public ManyToManyBFooBuilder green(ManyToManyBBar greens) {
    get().addGreen(greens);
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder green(ManyToManyBBarBuilder greens) {
    get().addGreen(greens.get());
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder with(ManyToManyBBar greens) {
    get().addGreen(greens);
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFooBuilder with(ManyToManyBBarBuilder greens) {
    get().addGreen(greens.get());
    return (ManyToManyBFooBuilder) this;
  }

  public ManyToManyBFoo get() {
    return (features.domain.ManyToManyBFoo) super.get();
  }

}
