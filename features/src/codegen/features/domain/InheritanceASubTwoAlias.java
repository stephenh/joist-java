package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceASubTwoAlias extends Alias<InheritanceASubTwo> {

    private final List<AliasColumn<InheritanceASubTwo, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubTwo, ?, ?>>();
    private final IdAliasColumn<InheritanceASubTwo> subClassId = new IdAliasColumn<InheritanceASubTwo>(this, "id", null);
    public final StringAliasColumn<InheritanceASubTwo> two = new StringAliasColumn<InheritanceASubTwo>(this, "two", InheritanceASubTwoCodegen.Shims.two);
    private final InheritanceABaseAlias baseAlias;
    public final IdAliasColumn<InheritanceABase> id;
    public final StringAliasColumn<InheritanceABase> name;
    public final IntAliasColumn<InheritanceABase> version;

    public InheritanceASubTwoAlias(String alias) {
        super(InheritanceASubTwo.class, "inheritance_a_sub_two", alias);
        this.baseAlias = new InheritanceABaseAlias(alias + "_b");
        this.columns.add(this.two);
        this.id = (this.baseAlias == null) ? null : this.baseAlias.id;
        this.name = (this.baseAlias == null) ? null : this.baseAlias.name;
        this.version = (this.baseAlias == null) ? null : this.baseAlias.version;
    }

    public InheritanceASubTwoAlias(Alias<?> rootAlias, String alias) {
        super(InheritanceASubTwo.class, "inheritance_a_sub_two", alias);
        this.baseAlias = null;
        this.columns.add(this.two);
        this.id = (this.baseAlias == null) ? null : this.baseAlias.id;
        this.name = (this.baseAlias == null) ? null : this.baseAlias.name;
        this.version = (this.baseAlias == null) ? null : this.baseAlias.version;
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

    public int getOrder() {
        return 5;
    }

}
