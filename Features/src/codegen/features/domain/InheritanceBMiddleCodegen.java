package features.domain;

import features.domain.queries.InheritanceBMiddleAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

public abstract class InheritanceBMiddleCodegen extends InheritanceBRoot {

    static {
        AliasRegistry.register(InheritanceBMiddle.class, new InheritanceBMiddleAlias("a"));
    }

    private String middleName = null;

    protected InheritanceBMiddleCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceBMiddle>("middleName", Shims.middleName));
        this.addRule(new MaxLength<InheritanceBMiddle>("middleName", 100, Shims.middleName));
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(java.lang.String middleName) {
        this.recordIfChanged("middleName", this.middleName, middleName);
        this.middleName = middleName;
    }

    public static class Shims {
        public static final Shim<InheritanceBMiddle, java.lang.String> middleName = new Shim<InheritanceBMiddle, java.lang.String>() {
            public void set(InheritanceBMiddle instance, java.lang.String middleName) {
                ((InheritanceBMiddleCodegen) instance).middleName = middleName;
            }
            public String get(InheritanceBMiddle instance) {
                return ((InheritanceBMiddleCodegen) instance).middleName;
            }
        };
    }

}
