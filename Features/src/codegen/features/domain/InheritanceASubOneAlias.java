package features.domain;

import features.domain.InheritanceABase;
import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubOneCodegen;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class InheritanceASubOneAlias extends Alias<InheritanceASubOne> {

    private final List<AliasColumn<InheritanceASubOne, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubOne, ?, ?>>();
    private final IdAliasColumn<InheritanceASubOne> subClassId = new IdAliasColumn<InheritanceASubOne>(this, "id", null);
    public final StringAliasColumn<InheritanceASubOne> one = new StringAliasColumn<InheritanceASubOne>(this, "one", InheritanceASubOneCodegen.Shims.one);
    private final features.domain.InheritanceABaseAlias baseAlias;
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

}
