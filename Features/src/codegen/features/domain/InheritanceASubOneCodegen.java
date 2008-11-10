package features.domain;

import features.domain.mappers.InheritanceASubOneAlias;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;

public abstract class InheritanceASubOneCodegen extends InheritanceABase {

    static {
        AliasRegistry.register(InheritanceASubOne.class, new InheritanceASubOneAlias("a"));
    }

    private String one = null;

    public String getOne() {
        return this.one;
    }

    public void setOne(java.lang.String one) {
        this.recordIfChanged("one", this.one, one);
        this.one = one;
    }

    public static class Shims {
        public static final Shim<InheritanceASubOne, java.lang.String> one = new Shim<InheritanceASubOne, java.lang.String>() {
            public void set(InheritanceASubOne instance, java.lang.String one) {
                ((InheritanceASubOneCodegen) instance).one = one;
            }
            public String get(InheritanceASubOne instance) {
                return ((InheritanceASubOneCodegen) instance).one;
            }
        };
    }

}
