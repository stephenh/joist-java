package features.domain.builders;

import features.domain.ParentDChildC;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDChildCBuilderCodegen extends AbstractBuilder<ParentDChildC> {

    public ParentDChildCBuilderCodegen(ParentDChildC instance) {
        super(instance);
    }

    public ParentDChildCBuilder id(Integer id) {
        get().setId(id);
        return (ParentDChildCBuilder) this;
    }

    public ParentDChildCBuilder name(String name) {
        get().setName(name);
        return (ParentDChildCBuilder) this;
    }

    public ParentDChildC get() {
        return (features.domain.ParentDChildC) super.get();
    }

}
