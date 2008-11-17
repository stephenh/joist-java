package features.domain;

import features.domain.OneToOneAFoo;
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

public class OneToOneAFooAlias extends Alias<OneToOneAFoo> {

    private final List<AliasColumn<OneToOneAFoo, ?, ?>> columns = new ArrayList<AliasColumn<OneToOneAFoo, ?, ?>>();
    public final IdAliasColumn<OneToOneAFoo> id = new IdAliasColumn<OneToOneAFoo>(this, "id", OneToOneAFooCodegen.Shims.id);
    public final StringAliasColumn<OneToOneAFoo> name = new StringAliasColumn<OneToOneAFoo>(this, "name", OneToOneAFooCodegen.Shims.name);
    public final IntAliasColumn<OneToOneAFoo> version = new IntAliasColumn<OneToOneAFoo>(this, "version", OneToOneAFooCodegen.Shims.version);

    public OneToOneAFooAlias(String alias) {
        super(OneToOneAFoo.class, "one_to_one_a_foo", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, OneToOneAFoo> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

    public List<AliasColumn<OneToOneAFoo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<OneToOneAFoo> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<OneToOneAFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<OneToOneAFoo> getSubClassIdColumn() {
        return null;
    }

}
