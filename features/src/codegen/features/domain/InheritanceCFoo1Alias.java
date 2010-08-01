package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceCFoo1Alias extends Alias<InheritanceCFoo1> {

    private final List<AliasColumn<InheritanceCFoo1, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceCFoo1, ?, ?>>();
    private final IdAliasColumn<InheritanceCFoo1> subClassId = new IdAliasColumn<InheritanceCFoo1>(this, "id", null);
    public final StringAliasColumn<InheritanceCFoo1> foo = new StringAliasColumn<InheritanceCFoo1>(this, "foo", InheritanceCFoo1Codegen.Shims.foo);
    private final InheritanceCAlias baseAlias;
    public final IdAliasColumn<InheritanceC> id;
    public final StringAliasColumn<InheritanceC> name;
    public final IntAliasColumn<InheritanceC> version;

    public InheritanceCFoo1Alias(String alias) {
        super(InheritanceCFoo1.class, "inheritance_c_foo1", alias);
        this.baseAlias = new InheritanceCAlias(alias + "_b");
        this.columns.add(this.foo);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public InheritanceCFoo1Alias(InheritanceCAlias baseAlias, String alias) {
        super(InheritanceCFoo1.class, "inheritance_c_foo1", alias);
        this.baseAlias = baseAlias;
        this.columns.add(this.foo);
        this.id = this.baseAlias.id;
        this.name = this.baseAlias.name;
        this.version = this.baseAlias.version;
    }

    public List<AliasColumn<InheritanceCFoo1, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceC> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<InheritanceC> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceCFoo1> getSubClassIdColumn() {
        return this.subClassId;
    }

    public Alias<InheritanceC> getBaseClassAlias() {
        return this.baseAlias;
    }

    public int getOrder() {
        return 10;
    }

}
