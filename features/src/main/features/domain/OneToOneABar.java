package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class OneToOneABar extends OneToOneABarCodegen {

    public OneToOneABar() {
    }

    public OneToOneABar(String name) {
        this.setName(name);
    }

}
