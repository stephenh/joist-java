package features.domain;

import bindgen.features.domain.InheritanceBBottomBinding;
import features.domain.queries.InheritanceBBottomQueries;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.domain.validation.rules.Rule;

public abstract class InheritanceBBottomCodegen extends InheritanceBMiddle {

    private static InheritanceBBottomBinding b = new InheritanceBBottomBinding();
    @SuppressWarnings("hiding")
    protected static InheritanceBBottomAlias alias;
    @SuppressWarnings("hiding")
    public static final InheritanceBBottomQueries queries;
    private String bottomName = null;
    private static Rule<InheritanceBBottom> bottomNameNotNull = new NotNull<InheritanceBBottom>(b.bottomName());
    private static Rule<InheritanceBBottom> bottomNameMaxLength = new MaxLength<InheritanceBBottom>(b.bottomName(), 100);

    static {
        alias = new InheritanceBBottomAlias("a");
        AliasRegistry.register(InheritanceBBottom.class, alias);
        queries = new InheritanceBBottomQueries();
    }

    protected InheritanceBBottomCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(bottomNameNotNull);
        this.addRule(bottomNameMaxLength);
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

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<InheritanceBBottom, String> bottomName = new Shim<InheritanceBBottom, String>() {
            public void set(InheritanceBBottom instance, String bottomName) {
                ((InheritanceBBottomCodegen) instance).bottomName = bottomName;
            }
            public String get(InheritanceBBottom instance) {
                return ((InheritanceBBottomCodegen) instance).bottomName;
            }
            public String getName() {
                return "bottomName";
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
