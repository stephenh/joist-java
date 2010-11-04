package features.domain.builders;

import features.domain.InheritanceABase;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceABaseBuilderCodegen extends AbstractBuilder<InheritanceABase> {

    public InheritanceABaseBuilderCodegen(InheritanceABase instance) {
        super(instance);
    }

    public InheritanceABaseBuilder id(Integer id) {
        get().setId(id);
        return (InheritanceABaseBuilder) this;
    }

    public InheritanceABaseBuilder name(String name) {
        get().setName(name);
        return (InheritanceABaseBuilder) this;
    }

    public InheritanceABaseBuilder with(String name) {
        get().setName(name);
        return (InheritanceABaseBuilder) this;
    }

    public InheritanceABase get() {
        return (features.domain.InheritanceABase) super.get();
    }

}
