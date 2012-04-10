package features.domain.builders;

import features.domain.ManyToManyABar;
import features.domain.ManyToManyAFooToBar;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ManyToManyABarBuilderCodegen extends AbstractBuilder<ManyToManyABar> {

  public ManyToManyABarBuilderCodegen(ManyToManyABar instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ManyToManyABarBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyABarBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ManyToManyABarBuilder name(String name) {
    get().setName(name);
    return (ManyToManyABarBuilder) this;
  }

  public ManyToManyABarBuilder with(String name) {
    return name(name);
  }

  @Override
  public ManyToManyABarBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ManyToManyABarBuilder) super.defaults();
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

  public ManyToManyABar get() {
    return (features.domain.ManyToManyABar) super.get();
  }

}
