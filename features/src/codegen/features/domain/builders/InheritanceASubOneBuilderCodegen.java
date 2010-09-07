package features.domain.builders;

import features.domain.InheritanceASubOne;

@SuppressWarnings("all")
public abstract class InheritanceASubOneBuilderCodegen extends InheritanceABaseBuilder {

    public InheritanceASubOneBuilderCodegen(InheritanceASubOne instance) {
        super(instance);
    }

    public InheritanceASubOneBuilder one(String one) {
        get().setOne(one);
        return (InheritanceASubOneBuilder) this;
    }

    public InheritanceASubOne get() {
        return (features.domain.InheritanceASubOne) super.get();
    }

}
