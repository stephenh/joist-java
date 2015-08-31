package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceCFoo1Alias extends Alias<InheritanceCFoo1> {

  private final List<AliasColumn<InheritanceCFoo1, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceCFoo1, ?, ?>>();
  private final IdAliasColumn<InheritanceCFoo1> subClassId = new IdAliasColumn<InheritanceCFoo1>(this, "id", null);
  public final StringAliasColumn<InheritanceCFoo1> foo = new StringAliasColumn<InheritanceCFoo1>(this, "foo", InheritanceCFoo1Codegen.Shims.foo);
  private final InheritanceCAlias baseAlias;
  public final IdAliasColumn<InheritanceC> id;
  public final StringAliasColumn<InheritanceC> name;
  public final LongAliasColumn<InheritanceC> version;

  public InheritanceCFoo1Alias() {
    this("icf0", null, true);
  }

  public InheritanceCFoo1Alias(String alias) {
    this(alias, null, true);
  }

  public InheritanceCFoo1Alias(String alias, InheritanceCAlias baseAlias, boolean addSubClasses) {
    super(InheritanceCFoo1.class, "inheritance_c_foo1", alias);
    this.baseAlias = (baseAlias != null) ? baseAlias : new InheritanceCAlias(alias + "_b", null, false);
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

  public LongAliasColumn<InheritanceC> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceCFoo1> getSubClassIdColumn() {
    return this.subClassId;
  }

  public Alias<InheritanceC> getBaseClassAlias() {
    return this.baseAlias;
  }

  public int getOrder() {
    return 14;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceCFoo1> on(ForeignKeyAliasColumn<T, InheritanceCFoo1> on) {
    return new JoinClause<T, InheritanceCFoo1>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceCFoo1> leftOn(ForeignKeyAliasColumn<T, InheritanceCFoo1> on) {
    return new JoinClause<T, InheritanceCFoo1>("LEFT OUTER JOIN", this, on);
  }

}
