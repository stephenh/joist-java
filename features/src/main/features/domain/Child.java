package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class Child extends ChildCodegen {

    public Child() {
    }

    public Child(Parent parent, String name) {
        this.setParent(parent);
        this.setName(name);
    }

}
