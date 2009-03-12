package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.ForeignKeyAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;
import joist.domain.queries.columns.StringAliasColumn;

public class ChildAlias extends Alias<Child> {

    private final List<AliasColumn<Child, ?, ?>> columns = new ArrayList<AliasColumn<Child, ?, ?>>();
    public final IdAliasColumn<Child> id = new IdAliasColumn<Child>(this, "id", ChildCodegen.Shims.id);
    public final StringAliasColumn<Child> name = new StringAliasColumn<Child>(this, "name", ChildCodegen.Shims.name);
    public final IntAliasColumn<Child> version = new IntAliasColumn<Child>(this, "version", ChildCodegen.Shims.version);
    public final ForeignKeyAliasColumn<Child, Parent> parent = new ForeignKeyAliasColumn<Child, Parent>(this, "parent_id", ChildCodegen.Shims.parentId);

    public ChildAlias(String alias) {
        super(Child.class, "child", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
        this.columns.add(this.parent);
    }

    public List<AliasColumn<Child, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<Child> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<Child> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<Child> getSubClassIdColumn() {
        return null;
    }

}
