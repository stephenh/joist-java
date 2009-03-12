package features.domain;

import features.domain.queries.InheritanceASubOneQueries;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;

public abstract class InheritanceASubOneCodegen extends InheritanceABase {

    @SuppressWarnings("hiding")
    protected static InheritanceASubOneAlias alias;
    @SuppressWarnings("hiding")
    public static final InheritanceASubOneQueries queries;
    private String one = null;

    static {
        alias = new InheritanceASubOneAlias("a");
        AliasRegistry.register(InheritanceASubOne.class, alias);
        queries = new InheritanceASubOneQueries();
    }

    protected InheritanceASubOneCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<InheritanceASubOne>("one", Shims.one));
        this.addRule(new MaxLength<InheritanceASubOne>("one", 100, Shims.one));
    }

    public String getOne() {
        return this.one;
    }

    public void setOne(String one) {
        this.getChanged().record("one", this.one, one);
        this.one = one;
    }

    public InheritanceASubOneChanged getChanged() {
        if (this.changed == null) {
            this.changed = new InheritanceASubOneChanged((InheritanceASubOne) this);
        }
        return (InheritanceASubOneChanged) this.changed;
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

    public static class InheritanceASubOneChanged extends InheritanceABaseChanged {
        public InheritanceASubOneChanged(InheritanceASubOne instance) {
            super(instance);
        }
        public boolean hasOne() {
            return this.contains("one");
        }
        public String getOriginalOne() {
            return (java.lang.String) this.getOriginal("one");
        }
    }

}
