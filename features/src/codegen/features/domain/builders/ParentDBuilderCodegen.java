package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildA;
import features.domain.ParentDChildB;
import features.domain.ParentDToChildC;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ParentDBuilderCodegen extends AbstractBuilder<ParentD> {

    public ParentDBuilderCodegen(ParentD instance) {
        super(instance);
    }

    public Long id() {
        return get().getId();
    }

    public ParentDBuilder id(Long id) {
        get().setId(id);
        return (ParentDBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public ParentDBuilder name(String name) {
        get().setName(name);
        return (ParentDBuilder) this;
    }

    public ParentDBuilder with(String name) {
        get().setName(name);
        return (ParentDBuilder) this;
    }

    public List<ParentDChildABuilder> parentDChildAs() {
        List<ParentDChildABuilder> b = new ArrayList<ParentDChildABuilder>();
        for (ParentDChildA e : get().getParentDChildAs()) {
            b.add(Builders.existing(e));
        }
        return b;
    }

    public ParentDChildABuilder parentDChildA(int i) {
        return Builders.existing(get().getParentDChildAs().get(i));
    }

    public List<ParentDChildBBuilder> parentDChildBs() {
        List<ParentDChildBBuilder> b = new ArrayList<ParentDChildBBuilder>();
        for (ParentDChildB e : get().getParentDChildBs()) {
            b.add(Builders.existing(e));
        }
        return b;
    }

    public ParentDChildBBuilder parentDChildB(int i) {
        return Builders.existing(get().getParentDChildBs().get(i));
    }

    public List<ParentDToChildCBuilder> parentDToChildCs() {
        List<ParentDToChildCBuilder> b = new ArrayList<ParentDToChildCBuilder>();
        for (ParentDToChildC e : get().getParentDToChildCs()) {
            b.add(Builders.existing(e));
        }
        return b;
    }

    public ParentDToChildCBuilder parentDToChildC(int i) {
        return Builders.existing(get().getParentDToChildCs().get(i));
    }

    public ParentD get() {
        return (features.domain.ParentD) super.get();
    }

}
