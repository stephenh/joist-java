package features.domain;

import features.domain.mappers.InheritanceBMiddleAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;

public abstract class InheritanceBMiddleCodegen extends InheritanceBRoot {

    static {
        AliasRegistry.register(InheritanceBMiddle.class, new InheritanceBMiddleAlias("a"));
    }

    private String middleName = null;

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
