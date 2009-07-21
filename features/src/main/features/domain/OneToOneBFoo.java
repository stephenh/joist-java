package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class OneToOneBFoo extends OneToOneBFooCodegen {

    public OneToOneBFoo() {
    }

    public OneToOneBFoo(String name) {
        this.setName(name);
    }

}
