package features.domain.mappers;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

import features.domain.InheritanceABase;
import features.domain.InheritanceASubTwo;
import features.domain.InheritanceASubTwoCodegen;

public class InheritanceASubTwoAlias extends Alias<InheritanceASubTwo> {

    private final List<AliasColumn<InheritanceASubTwo, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubTwo, ?, ?>>();
    private final IdAliasColumn<InheritanceASubTwo> subClassId = new IdAliasColumn<InheritanceASubTwo>(this, "id", null);
    public final StringAliasColumn<InheritanceASubTwo> two = new StringAliasColumn<InheritanceASubTwo>(
        this,
        "two",
        InheritanceASubTwoCodegen.Shims.two);
    private final InheritanceABaseAlias baseAlias;
    public final IdAliasColumn<InheritanceABase> id;
    public final StringAliasColumn<InheritanceABase> name;
    public final IntAliasColumn<InheritanceABase> version;

    public InheritanceASubTwoAlias(String alias) {
        super(InheritanceASubTwo.class, InheritanceABase.class, "inheritance_a_sub_two", alias);
        this.baseAlias = new InheritanceABaseAlias(alias + "_b");
        this.columns.add(this.two);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public InheritanceASubTwoAlias(InheritanceABaseAlias baseAlias, String alias) {
        super(InheritanceASubTwo.class, InheritanceABase.class, "inheritance_a_sub_two", alias);
        this.baseAlias = baseAlias;
        this.columns.add(this.two);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public List<AliasColumn<InheritanceASubTwo, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceABase> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceABase> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceASubTwo> getSubClassIdColumn() {
        return this.subClassId;
    }

    public Alias<InheritanceABase> getBaseClassAlias() {
        return this.baseAlias;
    }

}
