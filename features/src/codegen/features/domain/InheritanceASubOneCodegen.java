package features.domain;

import bindgen.features.domain.InheritanceASubOneBinding;
import features.domain.queries.InheritanceASubOneQueries;
import joist.domain.Shim;
import joist.domain.orm.AliasRegistry;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotNull;
import joist.domain.validation.rules.Rule;

public abstract class InheritanceASubOneCodegen extends InheritanceABase {

    private static InheritanceASubOneBinding b = new InheritanceASubOneBinding();
    @SuppressWarnings("hiding")
    protected static InheritanceASubOneAlias alias;
    @SuppressWarnings("hiding")
    public static final InheritanceASubOneQueries queries;
    private String one = null;
    private static Rule<InheritanceASubOne> oneNotNull = new NotNull<InheritanceASubOne>(b.one());
    private static Rule<InheritanceASubOne> oneMaxLength = new MaxLength<InheritanceASubOne>(b.one(), 100);

    static {
        alias = new InheritanceASubOneAlias("a");
        AliasRegistry.register(InheritanceASubOne.class, alias);
        queries = new InheritanceASubOneQueries();
    }

    protected InheritanceASubOneCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(oneNotNull);
        this.addRule(oneMaxLength);
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

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<InheritanceASubOne, String> one = new Shim<InheritanceASubOne, String>() {
            public void set(InheritanceASubOne instance, String one) {
                ((InheritanceASubOneCodegen) instance).one = one;
            }
            public String get(InheritanceASubOne instance) {
                return ((InheritanceASubOneCodegen) instance).one;
            }
            public String getName() {
                return "one";
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
