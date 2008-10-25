package features.domain.mappers;

import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootCodegen;
import features.domain.mappers.InheritanceBMiddleAlias;
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

public class InheritanceBRootAlias extends Alias<InheritanceBRoot> {

    private final List<AliasColumn<InheritanceBRoot, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBRoot, ?, ?>>();
    public final IdAliasColumn<InheritanceBRoot> id = new IdAliasColumn<InheritanceBRoot>(this, "id", InheritanceBRootCodegen.Shims.id);
    public final StringAliasColumn<InheritanceBRoot> name = new StringAliasColumn<InheritanceBRoot>(this, "name", InheritanceBRootCodegen.Shims.name);
    public final IntAliasColumn<InheritanceBRoot> version = new IntAliasColumn<InheritanceBRoot>(this, "version", InheritanceBRootCodegen.Shims.version);

    public InheritanceBRootAlias(String alias) {
        super(InheritanceBRoot.class, InheritanceBRoot.class, "inheritance_b_root", alias);
        this.addSubClassAlias(new InheritanceBMiddleAlias(this, alias + "_0"));
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
