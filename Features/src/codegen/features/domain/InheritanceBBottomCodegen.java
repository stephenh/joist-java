package features.domain;

import features.domain.queries.InheritanceBBottomQueries;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceBBottomCodegen extends InheritanceBMiddle {

    @SuppressWarnings("hiding")
    protected static InheritanceBBottomAlias alias;
    @SuppressWarnings("hiding")
    public static final InheritanceBBottomQueries queries;
    private String bottomName = null;

    static {
        alias = new InheritanceBBottomAlias("a");
        AliasRegistry.register(InheritanceBBottom.class, alias);
        queries = new InheritanceBBottomQueries();
    }

    protected InheritanceBBottomCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBBottom>("bottomName", Shims.bottomName));
        this.addRule(new MaxLength<InheritanceBBottom>("bottomName", 100, Shims.bottomName));
    }

    public String getBottomName() {
        return this.bottomName;
    }

    public void setBottomName(String bottomName) {
        this.getChanged().record("bottomName", this.bottomName, bottomName);
        this.bottomName = bottomName;
    }

    public InheritanceBBottomChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceBBottomChanged((InheritanceBBottom) this);
        }
        return (InheritanceBBottomChanged) this.changed;
    }

    public static class Shims {
        public static final Shim<InheritanceBBottom, String> bottomName = new Shim<InheritanceBBottom, String>() {
            public void set(InheritanceBBottom instance, String bottomName) {
                ((InheritanceBBottomCodegen) instance).bottomName = bottomName;
            }
            public String get(InheritanceBBottom instance) {
                return ((InheritanceBBottomCodegen) instance).bottomName;
            }
        };
    }

    public static class InheritanceBBottomChanged extends InheritanceBMiddleChanged {
        public InheritanceBBottomChanged(InheritanceBBottom instance) {
            super(instance);
        }
        public boolean hasBottomName() {
            return this.contains("bottomName");
        }
        public String getOriginalBottomName() {
            return (java.lang.String) this.getOriginal("bottomName");
        }
    }

}
