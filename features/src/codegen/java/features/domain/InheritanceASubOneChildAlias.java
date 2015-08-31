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

public class InheritanceASubOneChildAlias extends Alias<InheritanceASubOneChild> {

  private final List<AliasColumn<InheritanceASubOneChild, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubOneChild, ?, ?>>();
  public final IdAliasColumn<InheritanceASubOneChild> id = new IdAliasColumn<InheritanceASubOneChild>(this, "id", InheritanceASubOneChildCodegen.Shims.id);
  public final StringAliasColumn<InheritanceASubOneChild> name = new StringAliasColumn<InheritanceASubOneChild>(this, "name", InheritanceASubOneChildCodegen.Shims.name);
  public final LongAliasColumn<InheritanceASubOneChild> version = new LongAliasColumn<InheritanceASubOneChild>(this, "version", InheritanceASubOneChildCodegen.Shims.version);
  public final ForeignKeyAliasColumn<InheritanceASubOneChild, InheritanceASubOne> sub = new ForeignKeyAliasColumn<InheritanceASubOneChild, InheritanceASubOne>(this, "sub_id", InheritanceASubOneChildCodegen.Shims.subId);

  public InheritanceASubOneChildAlias() {
    this("iasoc0", null, true);
  }

  public InheritanceASubOneChildAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceASubOneChildAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(InheritanceASubOneChild.class, "inheritance_a_sub_one_child", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.sub);
  }

  public List<AliasColumn<InheritanceASubOneChild, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceASubOneChild> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceASubOneChild> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceASubOneChild> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 7;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceASubOneChild> on(ForeignKeyAliasColumn<T, InheritanceASubOneChild> on) {
    return new JoinClause<T, InheritanceASubOneChild>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceASubOneChild> leftOn(ForeignKeyAliasColumn<T, InheritanceASubOneChild> on) {
    return new JoinClause<T, InheritanceASubOneChild>("LEFT OUTER JOIN", this, on);
  }

}
