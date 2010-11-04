package features.domain.builders;

import features.domain.InheritanceASubTwo;

@SuppressWarnings("all")
public abstract class InheritanceASubTwoBuilderCodegen extends InheritanceABaseBuilder {

    public InheritanceASubTwoBuilderCodegen(InheritanceASubTwo instance) {
        super(instance);
    }

    public InheritanceASubTwoBuilder two(String two) {
        get().setTwo(two);
        return (InheritanceASubTwoBuilder) this;
    }

    public InheritanceASubTwoBuilder with(String two) {
        get().setTwo(two);
        return (InheritanceASubTwoBuilder) this;
    }

    public InheritanceASubTwo get() {
        return (features.domain.InheritanceASubTwo) super.get();
    }

}
