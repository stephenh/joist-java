package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildB;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDChildBBuilderCodegen extends AbstractBuilder<ParentDChildB> {

    public ParentDChildBBuilderCodegen(ParentDChildB instance) {
        super(instance);
    }

    public Integer id() {
        return get().getId();
    }

    public ParentDChildBBuilder id(Integer id) {
        get().setId(id);
        return (ParentDChildBBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ParentDChildBBuilder name(String name) {
        get().setName(name);
        return (ParentDChildBBuilder) this;
    }

    public ParentDChildBBuilder with(String name) {
        get().setName(name);
        return (ParentDChildBBuilder) this;
    }

    public ParentDBuilder parentD() {
        return Builders.existing(get().getParentD());
    }

    public ParentDChildBBuilder parentD(ParentD parentD) {
        get().setParentD(parentD);
        return (ParentDChildBBuilder) this;
    }

    public ParentDChildBBuilder with(ParentD parentD) {
        get().setParentD(parentD);
        return (ParentDChildBBuilder) this;
    }

    public ParentDChildBBuilder parentD(ParentDBuilder parentD) {
        get().setParentD(parentD.get());
        return (ParentDChildBBuilder) this;
    }

    public ParentDChildBBuilder with(ParentDBuilder parentD) {
        get().setParentD(parentD.get());
        return (ParentDChildBBuilder) this;
    }

    public ParentDChildB get() {
        return (features.domain.ParentDChildB) super.get();
    }

}
