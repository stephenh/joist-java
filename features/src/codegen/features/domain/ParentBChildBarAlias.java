package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.ForeignKeyAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;
import joist.domain.queries.columns.StringAliasColumn;

public class ParentBChildBarAlias extends Alias<ParentBChildBar> {

    private final List<AliasColumn<ParentBChildBar, ?, ?>> columns = new ArrayList<AliasColumn<ParentBChildBar, ?, ?>>();
    public final IdAliasColumn<ParentBChildBar> id = new IdAliasColumn<ParentBChildBar>(this, "id", ParentBChildBarCodegen.Shims.id);
    public final StringAliasColumn<ParentBChildBar> name = new StringAliasColumn<ParentBChildBar>(this, "name", ParentBChildBarCodegen.Shims.name);
    public final IntAliasColumn<ParentBChildBar> version = new IntAliasColumn<ParentBChildBar>(this, "version", ParentBChildBarCodegen.Shims.version);
    public final ForeignKeyAliasColumn<ParentBChildBar, ParentBParent> parentBParent = new ForeignKeyAliasColumn<ParentBChildBar, ParentBParent>(this, "parent_b_parent_id", ParentBChildBarCodegen.Shims.parentBParentId);

    public ParentBChildBarAlias(String alias) {
        super(ParentBChildBar.class, "parent_b_child_bar", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
        this.columns.add(this.parentBParent);
    }

    public List<AliasColumn<ParentBChildBar, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ParentBChildBar> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ParentBChildBar> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ParentBChildBar> getSubClassIdColumn() {
        return null;
    }

}
