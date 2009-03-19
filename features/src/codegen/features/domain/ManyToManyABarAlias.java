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

    public <T extends DomainObject> JoinClause<T, ManyToManyABar> on(ForeignKeyAliasColumn<T, ManyToManyABar> on) {
        return new JoinClause<T, ManyToManyABar>("INNER JOIN", this, on);
    }

}
