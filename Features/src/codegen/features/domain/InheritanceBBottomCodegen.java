package features.domain;

import features.domain.queries.InheritanceBBottomAlias;
import features.domain.queries.InheritanceBBottomQueries;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceBBottomCodegen extends InheritanceBMiddle {

    static {
        AliasRegistry.register(InheritanceBBottom.class, new InheritanceBBottomAlias("a"));
    }

    public static final @SuppressWarnings("hiding") InheritanceBBottomQueries queries = new InheritanceBBottomQueries();
    private String bottomName = null;

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

    public void setBottomName(java.lang.String bottomName) {
        this.recordIfChanged("bottomName", this.bottomName, bottomName);
        this.bottomName = bottomName;
    }

    public static class Shims {
        public static final Shim<InheritanceBBottom, java.lang.String> bottomName = new Shim<InheritanceBBottom, java.lang.String>() {
            public void set(InheritanceBBottom instance, java.lang.String bottomName) {
                ((InheritanceBBottomCodegen) instance).bottomName = bottomName;
            }
            public String get(InheritanceBBottom instance) {
                return ((InheritanceBBottomCodegen) instance).bottomName;
            }
        };
    }

}
