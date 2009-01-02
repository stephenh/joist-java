package features.domain;

import features.domain.queries.InheritanceASubTwoQueries;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class InheritanceASubTwoCodegen extends InheritanceABase {

    static {
        AliasRegistry.register(InheritanceASubTwo.class, new InheritanceASubTwoAlias("a"));
    }

    public static final @SuppressWarnings("hiding") InheritanceASubTwoQueries queries = new InheritanceASubTwoQueries();
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

    public void setTwo(String two) {
        this.getChanged().record("two", this.two, two);
        this.two = two;
    }

    public InheritanceASubTwoChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceASubTwoChanged((InheritanceASubTwo) this);
        }
        return (InheritanceASubTwoChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<InheritanceASubTwo, String> two = new Shim<InheritanceASubTwo, String>() {
            public void set(InheritanceASubTwo instance, String two) {
                ((InheritanceASubTwoCodegen) instance).two = two;
            }
            public String get(InheritanceASubTwo instance) {
                return ((InheritanceASubTwoCodegen) instance).two;
            }
        };
    }

    public static class InheritanceASubTwoChanged extends InheritanceABaseChanged {
        public InheritanceASubTwoChanged(InheritanceASubTwo instance) {
            super(instance);
        }
        public boolean hasTwo() {
            return this.contains("two");
        }
        public String getOriginalTwo() {
            return (java.lang.String) this.getOriginal("two");
        }
    }

}
