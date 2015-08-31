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

public class InheritanceCAlias extends Alias<InheritanceC> {

  private final List<AliasColumn<InheritanceC, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceC, ?, ?>>();
  public final IdAliasColumn<InheritanceC> id = new IdAliasColumn<InheritanceC>(this, "id", InheritanceCCodegen.Shims.id);
  public final StringAliasColumn<InheritanceC> name = new StringAliasColumn<InheritanceC>(this, "name", InheritanceCCodegen.Shims.name);
  public final LongAliasColumn<InheritanceC> version = new LongAliasColumn<InheritanceC>(this, "version", InheritanceCCodegen.Shims.version);

  public InheritanceCAlias() {
    this("ic0", null, true);
  }

  public InheritanceCAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceCAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(InheritanceC.class, "inheritance_c", alias);
    InheritanceCAlias inheritanceC = this;
    if (addSubClasses) {
      InheritanceCFoo1Alias inheritanceCFoo1 = new InheritanceCFoo1Alias(alias + "_0", inheritanceC, false);
      this.addSubClassAlias(inheritanceCFoo1);
      InheritanceCFoo2Alias inheritanceCFoo2 = new InheritanceCFoo2Alias(alias + "_1", inheritanceC, false);
      this.addSubClassAlias(inheritanceCFoo2);
    }
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

  public LongAliasColumn<InheritanceC> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceC> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 13;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceC> on(ForeignKeyAliasColumn<T, InheritanceC> on) {
    return new JoinClause<T, InheritanceC>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceC> leftOn(ForeignKeyAliasColumn<T, InheritanceC> on) {
    return new JoinClause<T, InheritanceC>("LEFT OUTER JOIN", this, on);
  }

}
