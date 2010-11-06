package features.domain.builders;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class InheritanceBRootChildBuilderCodegen extends AbstractBuilder<InheritanceBRootChild> {

    public InheritanceBRootChildBuilderCodegen(InheritanceBRootChild instance) {
        super(instance);
    }

    public Integer id() {
        return get().getId();
    }

    public InheritanceBRootChildBuilder id(Integer id) {
        get().setId(id);
        return (InheritanceBRootChildBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public InheritanceBRootChildBuilder name(String name) {
        get().setName(name);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootChildBuilder with(String name) {
        get().setName(name);
        return (InheritanceBRootChildBuilder) this;
    }

    public InheritanceBRootBuilder inheritanceBRoot() {
        return Builders.existing(get().getInheritanceBRoot());
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
