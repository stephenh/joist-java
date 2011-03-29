package features.domain.builders;

import features.domain.OneToOneABar;
import features.domain.OneToOneAFoo;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class OneToOneAFooBuilderCodegen extends AbstractBuilder<OneToOneAFoo> {

    public OneToOneAFooBuilderCodegen(OneToOneAFoo instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public OneToOneAFooBuilder id(Long id) {
        get().setId(id);
        return (OneToOneAFooBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public OneToOneAFooBuilder name(String name) {
        get().setName(name);
        return (OneToOneAFooBuilder) this;
    }

    public OneToOneAFooBuilder with(String name) {
        get().setName(name);
        return (OneToOneAFooBuilder) this;
    }

    public List<OneToOneABarBuilder> oneToOneABars() {
        List<OneToOneABarBuilder> b = new ArrayList<OneToOneABarBuilder>();
        for (OneToOneABar e : get().getOneToOneABars()) {
            b.add(Builders.existing(e));
        }
        return b;
    }

    public OneToOneABarBuilder oneToOneABar(int i) {
        return Builders.existing(get().getOneToOneABars().get(i));
    }

    public OneToOneAFoo get() {
        return (features.domain.OneToOneAFoo) super.get();
    }

}
