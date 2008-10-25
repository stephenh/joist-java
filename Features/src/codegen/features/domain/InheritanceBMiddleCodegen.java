package features.domain;

import features.domain.mappers.InheritanceBMiddleAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class InheritanceBMiddleCodegen extends InheritanceBRoot {

    private String middleName = null;

    public Alias<? extends InheritanceBMiddle> newAlias(String alias) {
        return new InheritanceBMiddleAlias(alias);
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.recordIfChanged("middleName", this.middleName, middleName);
        this.middleName = middleName;
    }

    public static class Shims {
        public static final Shim<InheritanceBMiddle, String> middleName = new Shim<InheritanceBMiddle, String>() {
            public void set(InheritanceBMiddle instance, String middleName) {
                ((InheritanceBMiddleCodegen) instance).middleName = middleName;
            }
            public String get(InheritanceBMiddle instance) {
                return ((InheritanceBMiddleCodegen) instance).middleName;
            }
        };
    }

}
