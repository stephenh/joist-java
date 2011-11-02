package features.domain.builders;

import java.util.ArrayList;
import java.util.List;

import joist.domain.builders.AbstractBuilder;
import features.domain.OneToOneBBar;
import features.domain.OneToOneBFoo;

@SuppressWarnings("all")
public abstract class OneToOneBFooBuilderCodegen extends AbstractBuilder<OneToOneBFoo> {

  public OneToOneBFooBuilderCodegen(OneToOneBFoo instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public OneToOneBFooBuilder id(Long id) {
    get().setId(id);
    return (OneToOneBFooBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public OneToOneBFooBuilder name(String name) {
    get().setName(name);
    return (OneToOneBFooBuilder) this;
  }

  public OneToOneBFooBuilder with(String name) {
    get().setName(name);
    return (OneToOneBFooBuilder) this;
  }

  public List<OneToOneBBarBuilder> oneToOneBBars() {
    List<OneToOneBBarBuilder> b = new ArrayList<OneToOneBBarBuilder>();
    for (OneToOneBBar e : get().getOneToOneBBars()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public OneToOneBBarBuilder oneToOneBBar(int i) {
    return Builders.existing(get().getOneToOneBBars().get(i));
  }

  public OneToOneBFoo get() {
    return (features.domain.OneToOneBFoo) super.get();
  }

}
