package features.domain;

import features.domain.queries.InheritanceASubTwoAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceASubTwoCodegen extends InheritanceABase {

    static {
        AliasRegistry.register(InheritanceASubTwo.class, new InheritanceASubTwoAlias("a"));
    }

    private String two = null;

    protected InheritanceASubTwoCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceASubTwo>("two", Shims.two));
        this.addRule(new MaxLength<InheritanceASubTwo>("two", 100, Shims.two));
    }

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
