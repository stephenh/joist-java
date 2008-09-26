package features.domain.mappers;

import features.domain.Child;
import features.domain.ChildCodegen;
import features.domain.Parent;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.ForeignKeyAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class ChildAlias extends Alias<Child> {

    private final List<AliasColumn<Child, ?, ?>> columns = new ArrayList<AliasColumn<Child, ?, ?>>();
    public final IdAliasColumn<Child> id = new IdAliasColumn<Child>(this, "id", ChildCodegen.Shims.id);
    public final StringAliasColumn<Child> name = new StringAliasColumn<Child>(this, "name", ChildCodegen.Shims.name);
    public final IntAliasColumn<Child> version = new IntAliasColumn<Child>(this, "version", ChildCodegen.Shims.version);
    public final ForeignKeyAliasColumn<Child, Parent> parent = new ForeignKeyAliasColumn<Child, Parent>(this, "parent_id", ChildCodegen.Shims.parentId);

    public ChildAlias(String alias) {
        super(Child.class, Child.class, "child", alias);
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
