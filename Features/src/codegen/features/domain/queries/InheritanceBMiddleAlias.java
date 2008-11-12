package features.domain.queries;

import features.domain.InheritanceBMiddle;
import features.domain.InheritanceBMiddleCodegen;
import features.domain.InheritanceBRoot;
import features.domain.queries.InheritanceBBottomAlias;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class InheritanceBMiddleAlias extends Alias<InheritanceBMiddle> {

    private final List<AliasColumn<InheritanceBMiddle, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBMiddle, ?, ?>>();
    private final IdAliasColumn<InheritanceBMiddle> subClassId = new IdAliasColumn<InheritanceBMiddle>(this, "id", null);
    public final StringAliasColumn<InheritanceBMiddle> middleName = new StringAliasColumn<InheritanceBMiddle>(this, "middle_name", InheritanceBMiddleCodegen.Shims.middleName);
    private final features.domain.queries.InheritanceBRootAlias baseAlias;
    public final IdAliasColumn<InheritanceBRoot> id;
    public final StringAliasColumn<InheritanceBRoot> name;
    public final IntAliasColumn<InheritanceBRoot> version;

    public InheritanceBMiddleAlias(String alias) {
        super(InheritanceBMiddle.class, InheritanceBRoot.class, "inheritance_b_middle", alias);
        this.addSubClassAlias(new InheritanceBBottomAlias(this, alias + "_0"));
        this.baseAlias = new InheritanceBRootAlias(alias + "_b");
        this.columns.add(this.middleName);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public InheritanceBMiddleAlias(InheritanceBRootAlias baseAlias, String alias) {
        super(InheritanceBMiddle.class, InheritanceBRoot.class, "inheritance_b_middle", alias);
        this.baseAlias = baseAlias;
        this.columns.add(this.middleName);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public List<AliasColumn<InheritanceBMiddle, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceBRoot> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceBRoot> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceBMiddle> getSubClassIdColumn() {
        return this.subClassId;
    }

    public Alias<InheritanceBRoot> getBaseClassAlias() {
        return this.baseAlias;
    }

}
