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

public class ManyToManyBBarAlias extends Alias<ManyToManyBBar> {

    private final List<AliasColumn<ManyToManyBBar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyBBar, ?, ?>>();
    public final IdAliasColumn<ManyToManyBBar> id = new IdAliasColumn<ManyToManyBBar>(this, "id", ManyToManyBBarCodegen.Shims.id);
    public final StringAliasColumn<ManyToManyBBar> name = new StringAliasColumn<ManyToManyBBar>(this, "name", ManyToManyBBarCodegen.Shims.name);
    public final IntAliasColumn<ManyToManyBBar> version = new IntAliasColumn<ManyToManyBBar>(this, "version", ManyToManyBBarCodegen.Shims.version);

    public ManyToManyBBarAlias(String alias) {
        super(ManyToManyBBar.class, "many_to_many_b_bar", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<ManyToManyBBar, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ManyToManyBBar> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ManyToManyBBar> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ManyToManyBBar> getSubClassIdColumn() {
        return null;
    }

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, ManyToManyBBar> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
