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

public class ManyToManyBFooAlias extends Alias<ManyToManyBFoo> {

    private final List<AliasColumn<ManyToManyBFoo, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyBFoo, ?, ?>>();
    public final IdAliasColumn<ManyToManyBFoo> id = new IdAliasColumn<ManyToManyBFoo>(this, "id", ManyToManyBFooCodegen.Shims.id);
    public final StringAliasColumn<ManyToManyBFoo> name = new StringAliasColumn<ManyToManyBFoo>(this, "name", ManyToManyBFooCodegen.Shims.name);
    public final IntAliasColumn<ManyToManyBFoo> version = new IntAliasColumn<ManyToManyBFoo>(this, "version", ManyToManyBFooCodegen.Shims.version);

    public ManyToManyBFooAlias(String alias) {
        super(ManyToManyBFoo.class, "many_to_many_b_foo", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<ManyToManyBFoo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ManyToManyBFoo> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ManyToManyBFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ManyToManyBFoo> getSubClassIdColumn() {
        return null;
    }

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, ManyToManyBFoo> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
