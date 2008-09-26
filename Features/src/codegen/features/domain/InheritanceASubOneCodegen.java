package features.domain;

import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

import features.domain.mappers.InheritanceASubOneAlias;

public abstract class InheritanceASubOneCodegen extends InheritanceABase {

    private String one = null;

    public Alias<InheritanceASubOne> newAlias(String alias) {
        return new InheritanceASubOneAlias(alias);
    }

    public String getOne() {
        return this.one;
    }

    public void setOne(String one) {
        this.recordIfChanged("one", this.one, one);
        this.one = one;
    }

    public static class Shims {
        public static final Shim<InheritanceASubOne, String> one = new Shim<InheritanceASubOne, String>() {
            public void set(InheritanceASubOne instance, String one) {
                ((InheritanceASubOneCodegen) instance).one = one;
            }

            public String get(InheritanceASubOne instance) {
                return ((InheritanceASubOneCodegen) instance).one;
            }
        };
    }

}
