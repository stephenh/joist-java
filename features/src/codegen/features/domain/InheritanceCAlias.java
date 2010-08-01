package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceCAlias extends Alias<InheritanceC> {

    private final List<AliasColumn<InheritanceC, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceC, ?, ?>>();
    public final IdAliasColumn<InheritanceC> id = new IdAliasColumn<InheritanceC>(this, "id", InheritanceCCodegen.Shims.id);
    public final StringAliasColumn<InheritanceC> name = new StringAliasColumn<InheritanceC>(this, "name", InheritanceCCodegen.Shims.name);
    public final IntAliasColumn<InheritanceC> version = new IntAliasColumn<InheritanceC>(this, "version", InheritanceCCodegen.Shims.version);

    public InheritanceCAlias(String alias) {
        super(InheritanceC.class, "inheritance_c", alias);
        InheritanceCAlias inheritanceC = this;
        InheritanceCFoo1Alias inheritanceCFoo1 = new InheritanceCFoo1Alias(inheritanceC, alias + "_0");
        this.addSubClassAlias(inheritanceCFoo1);
        InheritanceCFoo2Alias inheritanceCFoo2 = new InheritanceCFoo2Alias(inheritanceC, alias + "_1");
        this.addSubClassAlias(inheritanceCFoo2);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<InheritanceC, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceC> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceC> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceC> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 10;
    }

}
