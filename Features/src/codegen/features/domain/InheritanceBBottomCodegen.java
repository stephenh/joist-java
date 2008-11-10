package features.domain;

import features.domain.mappers.InheritanceBBottomAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;

public abstract class InheritanceBBottomCodegen extends InheritanceBMiddle {

    static {
        AliasRegistry.register(InheritanceBBottom.class, new InheritanceBBottomAlias("a"));
    }

    private String bottomName = null;

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
