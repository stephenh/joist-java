package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;
import joist.domain.queries.columns.StringAliasColumn;

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

    public int getOrder() {
        return 30;
    }

}
