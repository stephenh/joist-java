package features.domain.mappers;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

import features.domain.InheritanceABase;
import features.domain.InheritanceABaseCodegen;

public class InheritanceABaseAlias extends Alias<InheritanceABase> {

    private final List<AliasColumn<InheritanceABase, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceABase, ?, ?>>();
    public final IdAliasColumn<InheritanceABase> id = new IdAliasColumn<InheritanceABase>(this, "id", InheritanceABaseCodegen.Shims.id);
    public final StringAliasColumn<InheritanceABase> name = new StringAliasColumn<InheritanceABase>(this, "name", InheritanceABaseCodegen.Shims.name);
    public final IntAliasColumn<InheritanceABase> version = new IntAliasColumn<InheritanceABase>(
        this,
        "version",
        InheritanceABaseCodegen.Shims.version);

    public InheritanceABaseAlias(String alias) {
        super(InheritanceABase.class, InheritanceABase.class, "inheritance_a_base", alias);
        this.addSubClassAlias(new InheritanceASubOneAlias(this, alias + "_0"));
        this.addSubClassAlias(new InheritanceASubTwoAlias(this, alias + "_1"));
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<InheritanceABase, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceABase> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceABase> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceABase> getSubClassIdColumn() {
        return null;
    }

}
