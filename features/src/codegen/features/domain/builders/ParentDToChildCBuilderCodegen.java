package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildC;
import features.domain.ParentDToChildC;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDToChildCBuilderCodegen extends AbstractBuilder<ParentDToChildC> {

    public ParentDToChildCBuilderCodegen(ParentDToChildC instance) {
        super(instance);
    }

    public ParentDToChildCBuilder id(Integer id) {
        get().setId(id);
        return (ParentDToChildCBuilder) this;
    }

    public ParentDToChildCBuilder parentDChildC(ParentDChildC parentDChildC) {
        get().setParentDChildC(parentDChildC);
        return (ParentDToChildCBuilder) this;
    }

    public ParentDToChildCBuilder parentD(ParentD parentD) {
        get().setParentD(parentD);
        return (ParentDToChildCBuilder) this;
    }

    public ParentDToChildC get() {
        return (features.domain.ParentDToChildC) super.get();
    }

}
