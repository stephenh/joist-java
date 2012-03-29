package features.domain.builders;

import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBFooBuilderCodegen extends AbstractBuilder<ManyToManyBFoo> {

  public ManyToManyBFooBuilderCodegen(ManyToManyBFoo instance) {
    super(instance);
  }

  public Long id() {
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

  public ManyToManyBFoo get() {
    return (features.domain.ManyToManyBFoo) super.get();
  }

}
