package features.domain.mappers;

import features.domain.Parent;
import features.domain.ParentCodegen;
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

public class ParentAlias extends Alias<Parent> {

    private final List<AliasColumn<Parent, ?, ?>> columns = new ArrayList<AliasColumn<Parent, ?, ?>>();
    public final IdAliasColumn<Parent> id = new IdAliasColumn<Parent>(this, "id", ParentCodegen.Shims.id);
    public final StringAliasColumn<Parent> name = new StringAliasColumn<Parent>(this, "name", ParentCodegen.Shims.name);
    public final IntAliasColumn<Parent> version = new IntAliasColumn<Parent>(this, "version", ParentCodegen.Shims.version);

    public ParentAlias(String alias) {
        super(Parent.class, Parent.class, "parent", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
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

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, Parent> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
