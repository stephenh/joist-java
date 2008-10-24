package features.domain;

import features.domain.mappers.CodeADomainObjectAlias;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public abstract class CodeADomainObjectCodegen extends AbstractDomainObject {

    private Id<CodeADomainObject> id = null;
    private String name = null;
    private Integer version = null;
    private CodeASize codeASize = null;
    private Integer codeASizeId = null;
    private CodeAColor codeAColor = null;
    private Integer codeAColorId = null;

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
        if (this.codeASize == null && this.codeASizeId != null) {
            this.codeASize = CodeASize.fromId(this.codeASizeId);
        }
        return this.codeASize;
    }

    public void setCodeASize(CodeASize codeASize) {
        this.codeASize = codeASize;
    }

    public CodeAColor getCodeAColor() {
        if (this.codeAColor == null && this.codeAColorId != null) {
            this.codeAColor = CodeAColor.fromId(this.codeAColorId);
        }
        return this.codeAColor;
    }

    public void setCodeAColor(CodeAColor codeAColor) {
        this.codeAColor = codeAColor;
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
                ((CodeADomainObjectCodegen) instance).codeASizeId = codeASizeId;
            }
            public Integer get(CodeADomainObject instance) {
                CodeADomainObjectCodegen instanceCodegen = instance;
                if (instanceCodegen.codeASize != null) {
                    return instanceCodegen.codeASize.getId().intValue();
                }
                return ((CodeADomainObjectCodegen) instance).codeASizeId;
            }
        };
        public static final Shim<CodeADomainObject, Integer> codeAColorId = new Shim<CodeADomainObject, Integer>() {
            public void set(CodeADomainObject instance, Integer codeAColorId) {
                ((CodeADomainObjectCodegen) instance).codeAColorId = codeAColorId;
            }
            public Integer get(CodeADomainObject instance) {
                CodeADomainObjectCodegen instanceCodegen = instance;
                if (instanceCodegen.codeAColor != null) {
                    return instanceCodegen.codeAColor.getId().intValue();
                }
                return ((CodeADomainObjectCodegen) instance).codeAColorId;
            }
        };
    }

}
