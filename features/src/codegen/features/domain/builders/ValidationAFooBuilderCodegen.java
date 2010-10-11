package features.domain.builders;

import features.domain.ValidationAFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class ValidationAFooBuilderCodegen extends AbstractBuilder<ValidationAFoo> {

    public ValidationAFooBuilderCodegen(ValidationAFoo instance) {
        super(instance);
    }

    public ValidationAFooBuilder id(Integer id) {
        get().setId(id);
        return (ValidationAFooBuilder) this;
    }

    public ValidationAFooBuilder name(String name) {
        get().setName(name);
        return (ValidationAFooBuilder) this;
    }

    public ValidationAFoo get() {
        return (features.domain.ValidationAFoo) super.get();
    }

}
