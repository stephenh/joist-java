package features.domain;

import features.domain.mappers.InheritanceASubTwoAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;

public abstract class InheritanceASubTwoCodegen extends InheritanceABase {

    static {
        AliasRegistry.register(InheritanceASubTwo.class, new InheritanceASubTwoAlias("a"));
    }

    private String two = null;

    public String getTwo() {
        return this.two;
    }

    public void setTwo(java.lang.String two) {
        this.recordIfChanged("two", this.two, two);
        this.two = two;
    }

    public static class Shims {
        public static final Shim<InheritanceASubTwo, java.lang.String> two = new Shim<InheritanceASubTwo, java.lang.String>() {
            public void set(InheritanceASubTwo instance, java.lang.String two) {
                ((InheritanceASubTwoCodegen) instance).two = two;
            }
            public String get(InheritanceASubTwo instance) {
                return ((InheritanceASubTwoCodegen) instance).two;
            }
        };
    }

}
