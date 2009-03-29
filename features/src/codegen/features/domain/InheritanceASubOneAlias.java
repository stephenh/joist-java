package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceASubOneAlias extends Alias<InheritanceASubOne> {

    private final List<AliasColumn<InheritanceASubOne, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubOne, ?, ?>>();
    private final IdAliasColumn<InheritanceASubOne> subClassId = new IdAliasColumn<InheritanceASubOne>(this, "id", null);
    public final StringAliasColumn<InheritanceASubOne> one = new StringAliasColumn<InheritanceASubOne>(this, "one", InheritanceASubOneCodegen.Shims.one);
    private final InheritanceABaseAlias baseAlias;
    public final IdAliasColumn<InheritanceABase> id;
    public final StringAliasColumn<InheritanceABase> name;
    public final IntAliasColumn<InheritanceABase> version;

    public InheritanceASubOneAlias(String alias) {
        super(InheritanceASubOne.class, "inheritance_a_sub_one", alias);
        this.baseAlias = new InheritanceABaseAlias(alias + "_b");
        this.columns.add(this.one);
        this.id = (this.baseAlias == null) ? null : this.baseAlias.id;
        this.name = (this.baseAlias == null) ? null : this.baseAlias.name;
        this.version = (this.baseAlias == null) ? null : this.baseAlias.version;
    }

    public InheritanceASubOneAlias(Alias<?> rootAlias, String alias) {
        super(InheritanceASubOne.class, "inheritance_a_sub_one", alias);
        this.baseAlias = null;
        this.columns.add(this.one);
        this.id = (this.baseAlias == null) ? null : this.baseAlias.id;
        this.name = (this.baseAlias == null) ? null : this.baseAlias.name;
        this.version = (this.baseAlias == null) ? null : this.baseAlias.version;
    }

    public List<AliasColumn<InheritanceASubOne, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceABase> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceABase> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceASubOne> getSubClassIdColumn() {
        return this.subClassId;
    }

    public Alias<InheritanceABase> getBaseClassAlias() {
        return this.baseAlias;
    }

    public int getOrder() {
        return 4;
    }

}
