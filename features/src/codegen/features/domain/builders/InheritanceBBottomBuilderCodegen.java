package features.domain.builders;

import features.domain.InheritanceBBottom;

@SuppressWarnings("all")
public abstract class InheritanceBBottomBuilderCodegen extends InheritanceBMiddleBuilder {

    public InheritanceBBottomBuilderCodegen(InheritanceBBottom instance) {
        super(instance);
    }

    public InheritanceBBottomBuilder bottomName(String bottomName) {
        get().setBottomName(bottomName);
        return (InheritanceBBottomBuilder) this;
    }

    public InheritanceBBottomBuilder with(String bottomName) {
        get().setBottomName(bottomName);
        return (InheritanceBBottomBuilder) this;
    }

    public InheritanceBBottom get() {
        return (features.domain.InheritanceBBottom) super.get();
    }

}
