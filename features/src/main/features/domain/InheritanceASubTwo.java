package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class InheritanceASubTwo extends InheritanceASubTwoCodegen {

    public InheritanceASubTwo() {
    }

    public InheritanceASubTwo(String name, String two) {
        this.setName(name);
        this.setTwo(two);
    }

}
