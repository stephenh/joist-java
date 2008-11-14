package features.domain;

import features.domain.ManyToManyAFoo;
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

public class ManyToManyAFooAlias extends Alias<ManyToManyAFoo> {

    private final List<AliasColumn<ManyToManyAFoo, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyAFoo, ?, ?>>();
    public final IdAliasColumn<ManyToManyAFoo> id = new IdAliasColumn<ManyToManyAFoo>(this, "id", ManyToManyAFooCodegen.Shims.id);
    public final StringAliasColumn<ManyToManyAFoo> name = new StringAliasColumn<ManyToManyAFoo>(this, "name", ManyToManyAFooCodegen.Shims.name);
    public final IntAliasColumn<ManyToManyAFoo> version = new IntAliasColumn<ManyToManyAFoo>(this, "version", ManyToManyAFooCodegen.Shims.version);

    public ManyToManyAFooAlias(String alias) {
        super(ManyToManyAFoo.class, "many_to_many_a_foo", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<ManyToManyAFoo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ManyToManyAFoo> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ManyToManyAFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ManyToManyAFoo> getSubClassIdColumn() {
        return null;
    }

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, ManyToManyAFoo> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
