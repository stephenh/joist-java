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

public class InheritanceBRootAlias extends Alias<InheritanceBRoot> {

    private final List<AliasColumn<InheritanceBRoot, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBRoot, ?, ?>>();
    public final IdAliasColumn<InheritanceBRoot> id = new IdAliasColumn<InheritanceBRoot>(this, "id", InheritanceBRootCodegen.Shims.id);
    public final StringAliasColumn<InheritanceBRoot> name = new StringAliasColumn<InheritanceBRoot>(this, "name", InheritanceBRootCodegen.Shims.name);
    public final IntAliasColumn<InheritanceBRoot> version = new IntAliasColumn<InheritanceBRoot>(this, "version", InheritanceBRootCodegen.Shims.version);

    public InheritanceBRootAlias(String alias) {
        super(InheritanceBRoot.class, "inheritance_b_root", alias);
        this.addSubClassAlias(new InheritanceBMiddleAlias(this, alias + "_0"));
        this.addSubClassAlias(new InheritanceBBottomAlias(this, alias + "_1"));
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<InheritanceBRoot, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceBRoot> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceBRoot> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceBRoot> getSubClassIdColumn() {
        return null;
    }

    public JoinClause on(ForeignKeyAliasColumn<? extends DomainObject, InheritanceBRoot> on) {
        return new JoinClause("INNER JOIN", this, on);
    }

}
