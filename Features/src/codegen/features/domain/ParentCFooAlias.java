package features.domain;

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

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, ParentCFoo> on) {
        return new JoinClause("INNER JOIN", this, on);
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

}
