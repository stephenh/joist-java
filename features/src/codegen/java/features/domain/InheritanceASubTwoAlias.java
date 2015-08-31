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

public class InheritanceASubTwoAlias extends Alias<InheritanceASubTwo> {

  private final List<AliasColumn<InheritanceASubTwo, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubTwo, ?, ?>>();
  private final IdAliasColumn<InheritanceASubTwo> subClassId = new IdAliasColumn<InheritanceASubTwo>(this, "id", null);
  public final StringAliasColumn<InheritanceASubTwo> two = new StringAliasColumn<InheritanceASubTwo>(this, "two", InheritanceASubTwoCodegen.Shims.two);
  public final ForeignKeyAliasColumn<InheritanceASubTwo, InheritanceAThing> inheritanceAThing = new ForeignKeyAliasColumn<InheritanceASubTwo, InheritanceAThing>(this, "inheritance_a_thing_id", InheritanceASubTwoCodegen.Shims.inheritanceAThingId);
  private final InheritanceABaseAlias baseAlias;
  public final IdAliasColumn<InheritanceABase> id;
  public final StringAliasColumn<InheritanceABase> name;
  public final LongAliasColumn<InheritanceABase> version;
  public final ForeignKeyAliasColumn<InheritanceABase, InheritanceAOwner> inheritanceAOwner;

  public InheritanceASubTwoAlias() {
    this("iast0", null, true);
  }

  public InheritanceASubTwoAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceASubTwoAlias(String alias, InheritanceABaseAlias baseAlias, boolean addSubClasses) {
    super(InheritanceASubTwo.class, "inheritance_a_sub_two", alias);
    this.baseAlias = (baseAlias != null) ? baseAlias : new InheritanceABaseAlias(alias + "_b", null, false);
    this.columns.add(this.two);
    this.columns.add(this.inheritanceAThing);
    this.id = this.baseAlias.id;
    this.name = this.baseAlias.name;
    this.version = this.baseAlias.version;
    this.inheritanceAOwner = this.baseAlias.inheritanceAOwner;
  }

  public List<AliasColumn<InheritanceASubTwo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceABase> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceABase> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceASubTwo> getSubClassIdColumn() {
    return this.subClassId;
  }

  public Alias<InheritanceABase> getBaseClassAlias() {
    return this.baseAlias;
  }

  public int getOrder() {
    return 8;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceASubTwo> on(ForeignKeyAliasColumn<T, InheritanceASubTwo> on) {
    return new JoinClause<T, InheritanceASubTwo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceASubTwo> leftOn(ForeignKeyAliasColumn<T, InheritanceASubTwo> on) {
    return new JoinClause<T, InheritanceASubTwo>("LEFT OUTER JOIN", this, on);
  }

}
