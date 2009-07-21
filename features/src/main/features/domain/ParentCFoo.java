package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class ParentCFoo extends ParentCFooCodegen {

    public ParentCFoo() {
    }

    public ParentCFoo(String name) {
        this.setName(name);
    }

}
