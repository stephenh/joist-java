package features.domain;

import features.domain.OneToOneBFoo;
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

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, OneToOneBFoo> on) {
        return new JoinClause("INNER JOIN", this, on);
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
