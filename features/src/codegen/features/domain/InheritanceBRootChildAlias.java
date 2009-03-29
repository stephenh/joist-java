package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.ForeignKeyAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;
import joist.domain.queries.columns.StringAliasColumn;

public class InheritanceBRootChildAlias extends Alias<InheritanceBRootChild> {

    private final List<AliasColumn<InheritanceBRootChild, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBRootChild, ?, ?>>();
    public final IdAliasColumn<InheritanceBRootChild> id = new IdAliasColumn<InheritanceBRootChild>(this, "id", InheritanceBRootChildCodegen.Shims.id);
    public final StringAliasColumn<InheritanceBRootChild> name = new StringAliasColumn<InheritanceBRootChild>(this, "name", InheritanceBRootChildCodegen.Shims.name);
    public final IntAliasColumn<InheritanceBRootChild> version = new IntAliasColumn<InheritanceBRootChild>(this, "version", InheritanceBRootChildCodegen.Shims.version);
    public final ForeignKeyAliasColumn<InheritanceBRootChild, InheritanceBRoot> inheritanceBRoot = new ForeignKeyAliasColumn<InheritanceBRootChild, InheritanceBRoot>(this, "inheritance_b_root_id", InheritanceBRootChildCodegen.Shims.inheritanceBRootId);

    public InheritanceBRootChildAlias(String alias) {
        super(InheritanceBRootChild.class, "inheritance_b_root_child", alias);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
        this.columns.add(this.inheritanceBRoot);
    }

    public List<AliasColumn<InheritanceBRootChild, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceBRootChild> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceBRootChild> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceBRootChild> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 9;
    }

}
