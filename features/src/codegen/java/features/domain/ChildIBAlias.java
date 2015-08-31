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

public class ChildIBAlias extends Alias<ChildIB> {

  private final List<AliasColumn<ChildIB, ?, ?>> columns = new ArrayList<AliasColumn<ChildIB, ?, ?>>();
  public final IdAliasColumn<ChildIB> id = new IdAliasColumn<ChildIB>(this, "id", ChildIBCodegen.Shims.id);
  public final LongAliasColumn<ChildIB> version = new LongAliasColumn<ChildIB>(this, "version", ChildIBCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ChildIB, ParentI> parent = new ForeignKeyAliasColumn<ChildIB, ParentI>(this, "parent_id", ChildIBCodegen.Shims.parentId);

  public ChildIBAlias() {
    this("cib0", null, true);
  }

  public ChildIBAlias(String alias) {
    this(alias, null, true);
  }

  public ChildIBAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ChildIB.class, "child_i_b", alias);
    this.columns.add(this.id);
    this.columns.add(this.version);
    this.columns.add(this.parent);
  }

  public List<AliasColumn<ChildIB, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ChildIB> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ChildIB> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ChildIB> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 48;
  }

  public <T extends DomainObject> JoinClause<T, ChildIB> on(ForeignKeyAliasColumn<T, ChildIB> on) {
    return new JoinClause<T, ChildIB>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ChildIB> leftOn(ForeignKeyAliasColumn<T, ChildIB> on) {
    return new JoinClause<T, ChildIB>("LEFT OUTER JOIN", this, on);
  }

}
