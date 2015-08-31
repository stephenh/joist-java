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

public class InheritanceBMiddleAlias extends Alias<InheritanceBMiddle> {

  private final List<AliasColumn<InheritanceBMiddle, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBMiddle, ?, ?>>();
  private final IdAliasColumn<InheritanceBMiddle> subClassId = new IdAliasColumn<InheritanceBMiddle>(this, "id", null);
  public final StringAliasColumn<InheritanceBMiddle> middleName = new StringAliasColumn<InheritanceBMiddle>(this, "middle_name", InheritanceBMiddleCodegen.Shims.middleName);
  private final InheritanceBRootAlias baseAlias;
  public final IdAliasColumn<InheritanceBRoot> id;
  public final StringAliasColumn<InheritanceBRoot> name;
  public final LongAliasColumn<InheritanceBRoot> version;

  public InheritanceBMiddleAlias() {
    this("ibm0", null, true);
  }

  public InheritanceBMiddleAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceBMiddleAlias(String alias, InheritanceBRootAlias baseAlias, boolean addSubClasses) {
    super(InheritanceBMiddle.class, "inheritance_b_middle", alias);
    this.baseAlias = (baseAlias != null) ? baseAlias : new InheritanceBRootAlias(alias + "_b", null, false);
    InheritanceBMiddleAlias inheritanceBMiddle = this;
    if (addSubClasses) {
      InheritanceBBottomAlias inheritanceBBottom = new InheritanceBBottomAlias(alias + "_0", inheritanceBMiddle, false);
      this.addSubClassAlias(inheritanceBBottom);
    }
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

  public LongAliasColumn<InheritanceBRoot> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceBMiddle> getSubClassIdColumn() {
    return this.subClassId;
  }

  public Alias<InheritanceBRoot> getBaseClassAlias() {
    return this.baseAlias;
  }

  public int getOrder() {
    return 10;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBMiddle> on(ForeignKeyAliasColumn<T, InheritanceBMiddle> on) {
    return new JoinClause<T, InheritanceBMiddle>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBMiddle> leftOn(ForeignKeyAliasColumn<T, InheritanceBMiddle> on) {
    return new JoinClause<T, InheritanceBMiddle>("LEFT OUTER JOIN", this, on);
  }

}
