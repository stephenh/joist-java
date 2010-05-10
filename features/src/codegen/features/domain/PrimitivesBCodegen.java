package features.domain;

import features.domain.queries.PrimitivesBQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.NotNull;

public abstract class PrimitivesBCodegen extends AbstractDomainObject {

    public static final PrimitivesBQueries queries;
    private Long big1 = null;
    private Long big2 = null;
    private Boolean bool1 = false;
    private Boolean bool2 = false;
    private Integer id = null;
    private Integer int1 = null;
    private Integer int2 = null;
    private Short small1 = null;
    private Short small2 = null;
    private Integer version = null;
    protected Changed changed;

    static {
        Aliases.init();
        queries = new PrimitivesBQueries();
    }

    protected PrimitivesBCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<PrimitivesB>(Shims.big2));
        this.addRule(new NotNull<PrimitivesB>(Shims.bool2));
        this.addRule(new NotNull<PrimitivesB>(Shims.int2));
        this.addRule(new NotNull<PrimitivesB>(Shims.small2));
    }

    public Long getBig1() {
        return this.big1;
    }

    public void setBig1(Long big1) {
        this.getChanged().record("big1", this.big1, big1);
        this.big1 = big1;
    }

    public Long getBig2() {
        return this.big2;
    }

    public void setBig2(Long big2) {
        this.getChanged().record("big2", this.big2, big2);
        this.big2 = big2;
    }

    public Boolean getBool1() {
        return this.bool1;
    }

    public void setBool1(Boolean bool1) {
        this.getChanged().record("bool1", this.bool1, bool1);
        this.bool1 = bool1;
    }

    public Boolean getBool2() {
        return this.bool2;
    }

    public void setBool2(Boolean bool2) {
        this.getChanged().record("bool2", this.bool2, bool2);
        this.bool2 = bool2;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.getChanged().record("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public Integer getInt1() {
        return this.int1;
    }

    public void setInt1(Integer int1) {
        this.getChanged().record("int1", this.int1, int1);
        this.int1 = int1;
    }

    public Integer getInt2() {
        return this.int2;
    }

    public void setInt2(Integer int2) {
        this.getChanged().record("int2", this.int2, int2);
        this.int2 = int2;
    }

    public Short getSmall1() {
        return this.small1;
    }

    public void setSmall1(Short small1) {
        this.getChanged().record("small1", this.small1, small1);
        this.small1 = small1;
    }

    public Short getSmall2() {
        return this.small2;
    }

    public void setSmall2(Short small2) {
        this.getChanged().record("small2", this.small2, small2);
        this.small2 = small2;
    }

    public Integer getVersion() {
        return this.version;
    }

    public PrimitivesBChanged getChanged() {
        if (this.changed == null) {
            this.changed = new PrimitivesBChanged((PrimitivesB) this);
        }
        return (PrimitivesBChanged) this.changed;
    }

    @Override
    public void clearAssociations() {
        super.clearAssociations();
    }

    static class Shims {
        protected static final Shim<PrimitivesB, Long> big1 = new Shim<PrimitivesB, Long>() {
            public void set(PrimitivesB instance, Long big1) {
                ((PrimitivesBCodegen) instance).big1 = big1;
            }
            public Long get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).big1;
            }
            public String getName() {
                return "big1";
            }
        };
        protected static final Shim<PrimitivesB, Long> big2 = new Shim<PrimitivesB, Long>() {
            public void set(PrimitivesB instance, Long big2) {
                ((PrimitivesBCodegen) instance).big2 = big2;
            }
            public Long get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).big2;
            }
            public String getName() {
                return "big2";
            }
        };
        protected static final Shim<PrimitivesB, Boolean> bool1 = new Shim<PrimitivesB, Boolean>() {
            public void set(PrimitivesB instance, Boolean bool1) {
                ((PrimitivesBCodegen) instance).bool1 = bool1;
            }
            public Boolean get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).bool1;
            }
            public String getName() {
                return "bool1";
            }
        };
        protected static final Shim<PrimitivesB, Boolean> bool2 = new Shim<PrimitivesB, Boolean>() {
            public void set(PrimitivesB instance, Boolean bool2) {
                ((PrimitivesBCodegen) instance).bool2 = bool2;
            }
            public Boolean get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).bool2;
            }
            public String getName() {
                return "bool2";
            }
        };
        protected static final Shim<PrimitivesB, Integer> id = new Shim<PrimitivesB, Integer>() {
            public void set(PrimitivesB instance, Integer id) {
                ((PrimitivesBCodegen) instance).id = id;
            }
            public Integer get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).id;
            }
            public String getName() {
                return "id";
            }
        };
        protected static final Shim<PrimitivesB, Integer> int1 = new Shim<PrimitivesB, Integer>() {
            public void set(PrimitivesB instance, Integer int1) {
                ((PrimitivesBCodegen) instance).int1 = int1;
            }
            public Integer get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).int1;
            }
            public String getName() {
                return "int1";
            }
        };
        protected static final Shim<PrimitivesB, Integer> int2 = new Shim<PrimitivesB, Integer>() {
            public void set(PrimitivesB instance, Integer int2) {
                ((PrimitivesBCodegen) instance).int2 = int2;
            }
            public Integer get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).int2;
            }
            public String getName() {
                return "int2";
            }
        };
        protected static final Shim<PrimitivesB, Short> small1 = new Shim<PrimitivesB, Short>() {
            public void set(PrimitivesB instance, Short small1) {
                ((PrimitivesBCodegen) instance).small1 = small1;
            }
            public Short get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).small1;
            }
            public String getName() {
                return "small1";
            }
        };
        protected static final Shim<PrimitivesB, Short> small2 = new Shim<PrimitivesB, Short>() {
            public void set(PrimitivesB instance, Short small2) {
                ((PrimitivesBCodegen) instance).small2 = small2;
            }
            public Short get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).small2;
            }
            public String getName() {
                return "small2";
            }
        };
        protected static final Shim<PrimitivesB, Integer> version = new Shim<PrimitivesB, Integer>() {
            public void set(PrimitivesB instance, Integer version) {
                ((PrimitivesBCodegen) instance).version = version;
            }
            public Integer get(PrimitivesB instance) {
                return ((PrimitivesBCodegen) instance).version;
            }
            public String getName() {
                return "version";
            }
        };
    }

    public static class PrimitivesBChanged extends AbstractChanged {
        public PrimitivesBChanged(PrimitivesB instance) {
            super(instance);
        }
        public boolean hasBig1() {
            return this.contains("big1");
        }
        public Long getOriginalBig1() {
            return (java.lang.Long) this.getOriginal("big1");
        }
        public boolean hasBig2() {
            return this.contains("big2");
        }
        public Long getOriginalBig2() {
            return (java.lang.Long) this.getOriginal("big2");
        }
        public boolean hasBool1() {
            return this.contains("bool1");
        }
        public Boolean getOriginalBool1() {
            return (java.lang.Boolean) this.getOriginal("bool1");
        }
        public boolean hasBool2() {
            return this.contains("bool2");
        }
        public Boolean getOriginalBool2() {
            return (java.lang.Boolean) this.getOriginal("bool2");
        }
        public boolean hasId() {
            return this.contains("id");
        }
        public Integer getOriginalId() {
            return (java.lang.Integer) this.getOriginal("id");
        }
        public boolean hasInt1() {
            return this.contains("int1");
        }
        public Integer getOriginalInt1() {
            return (java.lang.Integer) this.getOriginal("int1");
        }
        public boolean hasInt2() {
            return this.contains("int2");
        }
        public Integer getOriginalInt2() {
            return (java.lang.Integer) this.getOriginal("int2");
        }
        public boolean hasSmall1() {
            return this.contains("small1");
        }
        public Short getOriginalSmall1() {
            return (java.lang.Short) this.getOriginal("small1");
        }
        public boolean hasSmall2() {
            return this.contains("small2");
        }
        public Short getOriginalSmall2() {
            return (java.lang.Short) this.getOriginal("small2");
        }
        public boolean hasVersion() {
            return this.contains("version");
        }
        public Integer getOriginalVersion() {
            return (java.lang.Integer) this.getOriginal("version");
        }
    }

}
