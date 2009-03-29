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

public class ParentCFooAlias extends Alias<ParentCFoo> {

    private final List<AliasColumn<ParentCFoo, ?, ?>> columns = new ArrayList<AliasColumn<ParentCFoo, ?, ?>>();
    public final IdAliasColumn<ParentCFoo> id = new IdAliasColumn<ParentCFoo>(this, "id", ParentCFooCodegen.Shims.id);
    public final StringAliasColumn<ParentCFoo> name = new StringAliasColumn<ParentCFoo>(this, "name", ParentCFooCodegen.Shims.name);
    public final IntAliasColumn<ParentCFoo> version = new IntAliasColumn<ParentCFoo>(this, "version", ParentCFooCodegen.Shims.version);

    public ParentCFooAlias(String alias) {
        super(ParentCFoo.class, "parent_c_foo", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public <T extends DomainObject> JoinClause<T, ParentCFoo> on(ForeignKeyAliasColumn<T, ParentCFoo> on) {
        return new JoinClause<T, ParentCFoo>("INNER JOIN", this, on);
    }

    public List<AliasColumn<ParentCFoo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ParentCFoo> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ParentCFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ParentCFoo> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 25;
    }

}
