package features.domain.builders;

import features.domain.Primitives;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class PrimitivesBuilderCodegen extends AbstractBuilder<Primitives> {

    public PrimitivesBuilderCodegen(Primitives instance) {
        super(instance);
    }

    public PrimitivesBuilder flag(Boolean flag) {
        get().setFlag(flag);
        return (PrimitivesBuilder) this;
    }

    public PrimitivesBuilder id(Integer id) {
        get().setId(id);
        return (PrimitivesBuilder) this;
    }

    public PrimitivesBuilder name(String name) {
        get().setName(name);
        return (PrimitivesBuilder) this;
    }

    public Primitives get() {
        return (features.domain.Primitives) super.get();
    }

}
