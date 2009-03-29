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

public class ParentAlias extends Alias<Parent> {

    private final List<AliasColumn<Parent, ?, ?>> columns = new ArrayList<AliasColumn<Parent, ?, ?>>();
    public final IdAliasColumn<Parent> id = new IdAliasColumn<Parent>(this, "id", ParentCodegen.Shims.id);
    public final StringAliasColumn<Parent> name = new StringAliasColumn<Parent>(this, "name", ParentCodegen.Shims.name);
    public final IntAliasColumn<Parent> version = new IntAliasColumn<Parent>(this, "version", ParentCodegen.Shims.version);

    public ParentAlias(String alias) {
        super(Parent.class, "parent", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public <T extends DomainObject> JoinClause<T, Parent> on(ForeignKeyAliasColumn<T, Parent> on) {
        return new JoinClause<T, Parent>("INNER JOIN", this, on);
    }

    public List<AliasColumn<Parent, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<Parent> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<Parent> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<Parent> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 20;
    }

}
