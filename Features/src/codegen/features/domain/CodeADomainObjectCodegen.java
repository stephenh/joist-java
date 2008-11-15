package features.domain;

import features.domain.CodeADomainObjectAlias;
import features.domain.queries.CodeADomainObjectQueries;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyCodeHolder;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.rules.MaxLength;
import org.exigencecorp.domainobjects.validation.rules.NotNull;

abstract class CodeADomainObjectCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(CodeADomainObject.class, new CodeADomainObjectAlias("a"));
    }

    public static final CodeADomainObjectQueries queries = new CodeADomainObjectQueries();
    private Integer id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyCodeHolder<CodeASize> codeASize = new ForeignKeyCodeHolder<CodeASize>(CodeASize.class);
    private ForeignKeyCodeHolder<CodeAColor> codeAColor = new ForeignKeyCodeHolder<CodeAColor>(CodeAColor.class);

    protected CodeADomainObjectCodegen() {
        this.addExtraRules();
    }

    private void addExtraRules() {
        this.addRule(new NotNull<CodeADomainObject>("name", Shims.name));
        this.addRule(new MaxLength<CodeADomainObject>("name", 100, Shims.name));
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(java.lang.Integer id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
        if (UoW.isOpen()) {
            UoW.getIdentityMap().store(this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.recordIfChanged("name", this.name, name);
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public CodeASize getCodeASize() {
        return this.codeASize.get();
    }

    public void setCodeASize(CodeASize codeASize) {
        this.setCodeASizeWithoutPercolation(codeASize);
    }

    public void setCodeASizeWithoutPercolation(CodeASize codeASize) {
        this.recordIfChanged("codeASize", this.codeASize, codeASize);
        this.codeASize.set(codeASize);
    }

    public CodeAColor getCodeAColor() {
        return this.codeAColor.get();
    }

    public void setCodeAColor(CodeAColor codeAColor) {
        this.setCodeAColorWithoutPercolation(codeAColor);
    }

    public void setCodeAColorWithoutPercolation(CodeAColor codeAColor) {
        this.recordIfChanged("codeAColor", this.codeAColor, codeAColor);
        this.codeAColor.set(codeAColor);
    }

    public static class Shims {
        public static final Shim<CodeADomainObject, java.lang.Integer> id = new Shim<CodeADomainObject, java.lang.Integer>() {
            public void set(CodeADomainObject instance, java.lang.Integer id) {
                ((CodeADomainObjectCodegen) instance).id = id;
            }
            public Integer get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).id;
            }
        };
        public static final Shim<CodeADomainObject, java.lang.String> name = new Shim<CodeADomainObject, java.lang.String>() {
            public void set(CodeADomainObject instance, java.lang.String name) {
                ((CodeADomainObjectCodegen) instance).name = name;
            }
            public String get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).name;
            }
        };
        public static final Shim<CodeADomainObject, java.lang.Integer> version = new Shim<CodeADomainObject, java.lang.Integer>() {
            public void set(CodeADomainObject instance, java.lang.Integer version) {
                ((CodeADomainObjectCodegen) instance).version = version;
            }
            public Integer get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).version;
            }
        };
        public static final Shim<CodeADomainObject, Integer> codeASizeId = new Shim<CodeADomainObject, Integer>() {
            public void set(CodeADomainObject instance, Integer codeASizeId) {
                ((CodeADomainObjectCodegen) instance).codeASize.setId(codeASizeId);
            }
            public Integer get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).codeASize.getId();
            }
        };
        public static final Shim<CodeADomainObject, Integer> codeAColorId = new Shim<CodeADomainObject, Integer>() {
            public void set(CodeADomainObject instance, Integer codeAColorId) {
                ((CodeADomainObjectCodegen) instance).codeAColor.setId(codeAColorId);
            }
            public Integer get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).codeAColor.getId();
            }
        };
    }

}
