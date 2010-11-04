package features.domain.builders;

import features.domain.InheritanceBMiddle;

@SuppressWarnings("all")
public abstract class InheritanceBMiddleBuilderCodegen extends InheritanceBRootBuilder {

    public InheritanceBMiddleBuilderCodegen(InheritanceBMiddle instance) {
        super(instance);
    }

    public InheritanceBMiddleBuilder middleName(String middleName) {
        get().setMiddleName(middleName);
        return (InheritanceBMiddleBuilder) this;
    }

    public InheritanceBMiddleBuilder with(String middleName) {
        get().setMiddleName(middleName);
        return (InheritanceBMiddleBuilder) this;
    }

    public InheritanceBMiddle get() {
        return (features.domain.InheritanceBMiddle) super.get();
    }

}
