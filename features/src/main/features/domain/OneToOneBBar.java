package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class OneToOneBBar extends OneToOneBBarCodegen {

    public OneToOneBBar() {
    }

    public OneToOneBBar(String name) {
        this.setName(name);
    }

}
