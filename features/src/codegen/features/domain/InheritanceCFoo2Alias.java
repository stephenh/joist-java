package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceCFoo2Alias extends Alias<InheritanceCFoo2> {

    private final List<AliasColumn<InheritanceCFoo2, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceCFoo2, ?, ?>>();
    private final IdAliasColumn<InheritanceCFoo2> subClassId = new IdAliasColumn<InheritanceCFoo2>(this, "id", null);
    public final StringAliasColumn<InheritanceCFoo2> foo = new StringAliasColumn<InheritanceCFoo2>(this, "foo", InheritanceCFoo2Codegen.Shims.foo);
    private final InheritanceCAlias baseAlias;
    public final IdAliasColumn<InheritanceC> id;
    public final StringAliasColumn<InheritanceC> name;
    public final IntAliasColumn<InheritanceC> version;

    public InheritanceCFoo2Alias(String alias) {
        super(InheritanceCFoo2.class, "inheritance_c_foo2", alias);
        this.baseAlias = new InheritanceCAlias(alias + "_b");
        this.columns.add(this.foo);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public InheritanceCFoo2Alias(InheritanceCAlias baseAlias, String alias) {
        super(InheritanceCFoo2.class, "inheritance_c_foo2", alias);
        this.baseAlias = baseAlias;
        this.columns.add(this.foo);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public List<AliasColumn<InheritanceCFoo2, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceC> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceC> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceCFoo2> getSubClassIdColumn() {
        return this.subClassId;
    }

    public Alias<InheritanceC> getBaseClassAlias() {
        return this.baseAlias;
    }

    public int getOrder() {
        return 10;
    }

}
