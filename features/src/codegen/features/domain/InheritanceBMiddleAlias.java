package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceBMiddleAlias extends Alias<InheritanceBMiddle> {

    private final List<AliasColumn<InheritanceBMiddle, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBMiddle, ?, ?>>();
    private final IdAliasColumn<InheritanceBMiddle> subClassId = new IdAliasColumn<InheritanceBMiddle>(this, "id", null);
    public final StringAliasColumn<InheritanceBMiddle> middleName = new StringAliasColumn<InheritanceBMiddle>(this, "middle_name", InheritanceBMiddleCodegen.Shims.middleName);
    private final InheritanceBRootAlias baseAlias;
    public final IdAliasColumn<InheritanceBRoot> id;
    public final StringAliasColumn<InheritanceBRoot> name;
    public final IntAliasColumn<InheritanceBRoot> version;

    public InheritanceBMiddleAlias(String alias) {
        super(InheritanceBMiddle.class, "inheritance_b_middle", alias);
        InheritanceBMiddleAlias inheritanceBMiddle = this;
        InheritanceBBottomAlias inheritanceBBottom = new InheritanceBBottomAlias(inheritanceBMiddle, alias + "_0");
        this.addSubClassAlias(inheritanceBBottom);
        this.baseAlias = new InheritanceBRootAlias(alias + "_b");
        this.columns.add(this.middleName);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public InheritanceBMiddleAlias(InheritanceBRootAlias baseAlias, String alias) {
        super(InheritanceBMiddle.class, "inheritance_b_middle", alias);
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

    public int getOrder() {
        return 6;
    }

}
