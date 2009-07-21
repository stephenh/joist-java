package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class OneToOneAFoo extends OneToOneAFooCodegen {

    public OneToOneAFoo() {
    }

    public OneToOneAFoo(String name) {
        this.setName(name);
    }

}
