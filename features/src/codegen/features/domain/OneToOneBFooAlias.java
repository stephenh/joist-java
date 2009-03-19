package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.queries.Alias;
import joist.domain.queries.JoinClause;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.ForeignKeyAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;
import joist.domain.queries.columns.StringAliasColumn;

public class OneToOneBFooAlias extends Alias<OneToOneBFoo> {

    private final List<AliasColumn<OneToOneBFoo, ?, ?>> columns = new ArrayList<AliasColumn<OneToOneBFoo, ?, ?>>();
    public final IdAliasColumn<OneToOneBFoo> id = new IdAliasColumn<OneToOneBFoo>(this, "id", OneToOneBFooCodegen.Shims.id);
    public final StringAliasColumn<OneToOneBFoo> name = new StringAliasColumn<OneToOneBFoo>(this, "name", OneToOneBFooCodegen.Shims.name);
    public final IntAliasColumn<OneToOneBFoo> version = new IntAliasColumn<OneToOneBFoo>(this, "version", OneToOneBFooCodegen.Shims.version);

    public OneToOneBFooAlias(String alias) {
        super(OneToOneBFoo.class, "one_to_one_b_foo", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public <T extends DomainObject> JoinClause<T, OneToOneBFoo> on(ForeignKeyAliasColumn<T, OneToOneBFoo> on) {
        return new JoinClause<T, OneToOneBFoo>("INNER JOIN", this, on);
    }

    public List<AliasColumn<OneToOneBFoo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<OneToOneBFoo> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<OneToOneBFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<OneToOneBFoo> getSubClassIdColumn() {
        return null;
    }

}
