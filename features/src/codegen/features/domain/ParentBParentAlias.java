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

public class ParentBParentAlias extends Alias<ParentBParent> {

    private final List<AliasColumn<ParentBParent, ?, ?>> columns = new ArrayList<AliasColumn<ParentBParent, ?, ?>>();
    public final IdAliasColumn<ParentBParent> id = new IdAliasColumn<ParentBParent>(this, "id", ParentBParentCodegen.Shims.id);
    public final StringAliasColumn<ParentBParent> name = new StringAliasColumn<ParentBParent>(this, "name", ParentBParentCodegen.Shims.name);
    public final IntAliasColumn<ParentBParent> version = new IntAliasColumn<ParentBParent>(this, "version", ParentBParentCodegen.Shims.version);

    public ParentBParentAlias(String alias) {
        super(ParentBParent.class, "parent_b_parent", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public <T extends DomainObject> JoinClause<T, ParentBParent> on(ForeignKeyAliasColumn<T, ParentBParent> on) {
        return new JoinClause<T, ParentBParent>("INNER JOIN", this, on);
    }

    public List<AliasColumn<ParentBParent, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ParentBParent> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ParentBParent> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ParentBParent> getSubClassIdColumn() {
        return null;
    }

}
