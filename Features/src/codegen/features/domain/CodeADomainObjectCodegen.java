package features.domain;

import features.domain.mappers.CodeADomainObjectAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.ForeignKeyCodeHolder;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class CodeADomainObjectCodegen extends AbstractDomainObject {

    static {
        AliasRegistry.register(CodeADomainObject.class, new CodeADomainObjectAlias("a"));
    }

    private Id<CodeADomainObject> id = null;
    private String name = null;
    private Integer version = null;
    private ForeignKeyCodeHolder<CodeASize> codeASize = new ForeignKeyCodeHolder<CodeASize>(CodeASize.class);
    private ForeignKeyCodeHolder<CodeAColor> codeAColor = new ForeignKeyCodeHolder<CodeAColor>(CodeAColor.class);

    public Alias<? extends CodeADomainObject> newAlias(String alias) {
        return new CodeADomainObjectAlias(alias);
    }

    public Id<CodeADomainObject> getId() {
        return this.id;
    }

    public void setId(Id<CodeADomainObject> id) {
        this.recordIfChanged("id", this.id, id);
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
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
        this.recordIfChanged("codeASize", this.codeASize, codeASize);
        this.codeASize.set(codeASize);
    }

    public CodeAColor getCodeAColor() {
        return this.codeAColor.get();
    }

    public void setCodeAColor(CodeAColor codeAColor) {
        this.recordIfChanged("codeAColor", this.codeAColor, codeAColor);
        this.codeAColor.set(codeAColor);
    }

    public static class Shims {
        public static final Shim<CodeADomainObject, Id<CodeADomainObject>> id = new Shim<CodeADomainObject, Id<CodeADomainObject>>() {
            public void set(CodeADomainObject instance, Id<CodeADomainObject> id) {
                ((CodeADomainObjectCodegen) instance).id = id;
            }
            public Id<CodeADomainObject> get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).id;
            }
        };
        public static final Shim<CodeADomainObject, String> name = new Shim<CodeADomainObject, String>() {
            public void set(CodeADomainObject instance, String name) {
                ((CodeADomainObjectCodegen) instance).name = name;
            }
            public String get(CodeADomainObject instance) {
                return ((CodeADomainObjectCodegen) instance).name;
            }
        };
        public static final Shim<CodeADomainObject, Integer> version = new Shim<CodeADomainObject, Integer>() {
            public void set(CodeADomainObject instance, Integer version) {
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
