package features.domain.queries;

import features.domain.ParentBParent;
import features.domain.ParentBParentCodegen;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.JoinClause;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.ForeignKeyAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class ParentBParentAlias extends Alias<ParentBParent> {

    private final List<AliasColumn<ParentBParent, ?, ?>> columns = new ArrayList<AliasColumn<ParentBParent, ?, ?>>();
    public final IdAliasColumn<ParentBParent> id = new IdAliasColumn<ParentBParent>(this, "id", ParentBParentCodegen.Shims.id);
    public final StringAliasColumn<ParentBParent> name = new StringAliasColumn<ParentBParent>(this, "name", ParentBParentCodegen.Shims.name);
    public final IntAliasColumn<ParentBParent> version = new IntAliasColumn<ParentBParent>(this, "version", ParentBParentCodegen.Shims.version);

    public ParentBParentAlias(String alias) {
        super(ParentBParent.class, ParentBParent.class, "parent_b_parent", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
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

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, ParentBParent> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
