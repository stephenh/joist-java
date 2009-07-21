package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class InheritanceASubOne extends InheritanceASubOneCodegen {

    public InheritanceASubOne() {
    }

    public InheritanceASubOne(String name, String one) {
        this.setName(name);
        this.setOne(one);
    }

}
