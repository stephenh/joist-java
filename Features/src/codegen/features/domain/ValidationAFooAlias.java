package features.domain;

import features.domain.ValidationAFoo;
import features.domain.ValidationAFooCodegen;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class ValidationAFooAlias extends Alias<ValidationAFoo> {

    private final List<AliasColumn<ValidationAFoo, ?, ?>> columns = new ArrayList<AliasColumn<ValidationAFoo, ?, ?>>();
    public final IdAliasColumn<ValidationAFoo> id = new IdAliasColumn<ValidationAFoo>(this, "id", ValidationAFooCodegen.Shims.id);
    public final StringAliasColumn<ValidationAFoo> name = new StringAliasColumn<ValidationAFoo>(this, "name", ValidationAFooCodegen.Shims.name);
    public final IntAliasColumn<ValidationAFoo> version = new IntAliasColumn<ValidationAFoo>(this, "version", ValidationAFooCodegen.Shims.version);

    public ValidationAFooAlias(String alias) {
        super(ValidationAFoo.class, "validation_a_foo", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<ValidationAFoo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ValidationAFoo> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ValidationAFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ValidationAFoo> getSubClassIdColumn() {
        return null;
    }

}
