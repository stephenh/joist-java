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

public class InheritanceBBottomAlias extends Alias<InheritanceBBottom> {

  private final List<AliasColumn<InheritanceBBottom, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBBottom, ?, ?>>();
  private final IdAliasColumn<InheritanceBBottom> subClassId = new IdAliasColumn<InheritanceBBottom>(this, "id", null);
  public final StringAliasColumn<InheritanceBBottom> bottomName = new StringAliasColumn<InheritanceBBottom>(this, "bottom_name", InheritanceBBottomCodegen.Shims.bottomName);
  private final InheritanceBMiddleAlias baseAlias;
  public final StringAliasColumn<InheritanceBMiddle> middleName;
  public final IdAliasColumn<InheritanceBRoot> id;
  public final StringAliasColumn<InheritanceBRoot> name;
  public final LongAliasColumn<InheritanceBRoot> version;

  public InheritanceBBottomAlias() {
    this("ibb0", null, true);
  }

  public InheritanceBBottomAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceBBottomAlias(String alias, InheritanceBMiddleAlias baseAlias, boolean addSubClasses) {
    super(InheritanceBBottom.class, "inheritance_b_bottom", alias);
    this.baseAlias = (baseAlias != null) ? baseAlias : new InheritanceBMiddleAlias(alias + "_b", null, false);
    this.columns.add(this.bottomName);
    this.middleName = this.baseAlias.middleName;
    this.id = this.baseAlias.id;
    this.name = this.baseAlias.name;
    this.version = this.baseAlias.version;
  }

  public List<AliasColumn<InheritanceBBottom, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceBRoot> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceBRoot> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceBBottom> getSubClassIdColumn() {
    return this.subClassId;
  }

  public Alias<InheritanceBMiddle> getBaseClassAlias() {
    return this.baseAlias;
  }

  public int getOrder() {
    return 11;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBBottom> on(ForeignKeyAliasColumn<T, InheritanceBBottom> on) {
    return new JoinClause<T, InheritanceBBottom>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBBottom> leftOn(ForeignKeyAliasColumn<T, InheritanceBBottom> on) {
    return new JoinClause<T, InheritanceBBottom>("LEFT OUTER JOIN", this, on);
  }

}
