package features.domain.builders;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceBRootChildBuilderCodegen extends AbstractBuilder<InheritanceBRootChild> {

    public InheritanceBRootChildBuilderCodegen(InheritanceBRootChild instance) {
        super(instance);
    }

    public InheritanceBRootChildBuilder id(Integer id) {
        get().setId(id);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder name(String name) {
        get().setName(name);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder with(String name) {
        get().setName(name);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder inheritanceBRoot(InheritanceBRoot inheritanceBRoot) {
        get().setInheritanceBRoot(inheritanceBRoot);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder with(InheritanceBRoot inheritanceBRoot) {
        get().setInheritanceBRoot(inheritanceBRoot);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder inheritanceBRoot(InheritanceBRootBuilder inheritanceBRoot) {
        get().setInheritanceBRoot(inheritanceBRoot.get());
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder with(InheritanceBRootBuilder inheritanceBRoot) {
        get().setInheritanceBRoot(inheritanceBRoot.get());
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChild get() {
        return (features.domain.InheritanceBRootChild) super.get();
    }

}
