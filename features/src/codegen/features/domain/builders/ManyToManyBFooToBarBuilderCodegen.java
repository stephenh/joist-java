package features.domain.builders;

import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ManyToManyBFooToBarBuilderCodegen extends AbstractBuilder<ManyToManyBFooToBar> {

  public ManyToManyBFooToBarBuilderCodegen(ManyToManyBFooToBar instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public ManyToManyBFooToBarBuilder id(Long id) {
    get().setId(id);
    return (ManyToManyBFooToBarBuilder) this;
  }

  public ManyToManyBFooBuilder blue() {
    if (get().getBlue() == null) {
      return null;
    }
    return Builders.existing(get().getBlue());
  }

  public ManyToManyBFooToBarBuilder blue(ManyToManyBFoo blue) {
    get().setBlue(blue);
    return (ManyToManyBFooToBarBuilder) this;
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBFoo blue) {
    return blue(blue);
  }

  public ManyToManyBFooToBarBuilder blue(ManyToManyBFooBuilder blue) {
    return blue(blue.get());
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBFooBuilder blue) {
    return blue(blue);
  }

  public ManyToManyBBarBuilder green() {
    if (get().getGreen() == null) {
      return null;
    }
    return Builders.existing(get().getGreen());
  }

  public ManyToManyBFooToBarBuilder green(ManyToManyBBar green) {
    get().setGreen(green);
    return (ManyToManyBFooToBarBuilder) this;
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBBar green) {
    return green(green);
  }

  public ManyToManyBFooToBarBuilder green(ManyToManyBBarBuilder green) {
    return green(green.get());
  }

  public ManyToManyBFooToBarBuilder with(ManyToManyBBarBuilder green) {
    return green(green);
  }

  public ManyToManyBFooToBar get() {
    return (features.domain.ManyToManyBFooToBar) super.get();
  }

}
