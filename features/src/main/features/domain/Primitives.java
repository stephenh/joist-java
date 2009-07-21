package features.domain;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class Primitives extends PrimitivesCodegen {

    public Primitives() {
    }

    public Primitives(String name) {
        this.setName(name);
    }

    public Primitives flag(boolean flag) {
        this.setFlag(flag);
        return this;
    }

}
