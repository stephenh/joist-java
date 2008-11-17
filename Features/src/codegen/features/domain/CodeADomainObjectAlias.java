package features.domain;

import features.domain.CodeAColor;
import features.domain.CodeADomainObject;
import features.domain.CodeASize;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.CodeAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class CodeADomainObjectAlias extends Alias<CodeADomainObject> {

    private final List<AliasColumn<CodeADomainObject, ?, ?>> columns = new ArrayList<AliasColumn<CodeADomainObject, ?, ?>>();
    public final IdAliasColumn<CodeADomainObject> id = new IdAliasColumn<CodeADomainObject>(this, "id", CodeADomainObjectCodegen.Shims.id);
    public final StringAliasColumn<CodeADomainObject> name = new StringAliasColumn<CodeADomainObject>(this, "name", CodeADomainObjectCodegen.Shims.name);
    public final IntAliasColumn<CodeADomainObject> version = new IntAliasColumn<CodeADomainObject>(this, "version", CodeADomainObjectCodegen.Shims.version);
    public final CodeAliasColumn<CodeADomainObject, CodeAColor> codeAColor = new CodeAliasColumn<CodeADomainObject, CodeAColor>(this, "code_a_color_id", CodeADomainObjectCodegen.Shims.codeAColorId);
    public final CodeAliasColumn<CodeADomainObject, CodeASize> codeASize = new CodeAliasColumn<CodeADomainObject, CodeASize>(this, "code_a_size_id", CodeADomainObjectCodegen.Shims.codeASizeId);

    public CodeADomainObjectAlias(String alias) {
        super(CodeADomainObject.class, "code_a_domain_object", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
        this.columns.add(this.codeAColor);
        this.columns.add(this.codeASize);
    }

    public List<AliasColumn<CodeADomainObject, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<CodeADomainObject> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<CodeADomainObject> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<CodeADomainObject> getSubClassIdColumn() {
        return null;
    }

}
