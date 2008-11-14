package features.domain;

import features.domain.ManyToManyABar;
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

public class ManyToManyABarAlias extends Alias<ManyToManyABar> {

    private final List<AliasColumn<ManyToManyABar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyABar, ?, ?>>();
    public final IdAliasColumn<ManyToManyABar> id = new IdAliasColumn<ManyToManyABar>(this, "id", ManyToManyABarCodegen.Shims.id);
    public final StringAliasColumn<ManyToManyABar> name = new StringAliasColumn<ManyToManyABar>(this, "name", ManyToManyABarCodegen.Shims.name);
    public final IntAliasColumn<ManyToManyABar> version = new IntAliasColumn<ManyToManyABar>(this, "version", ManyToManyABarCodegen.Shims.version);

    public ManyToManyABarAlias(String alias) {
        super(ManyToManyABar.class, "many_to_many_a_bar", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<ManyToManyABar, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ManyToManyABar> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ManyToManyABar> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ManyToManyABar> getSubClassIdColumn() {
        return null;
    }

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, ManyToManyABar> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
