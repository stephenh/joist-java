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

public class ChildHAlias extends Alias<ChildH> {

  private final List<AliasColumn<ChildH, ?, ?>> columns = new ArrayList<AliasColumn<ChildH, ?, ?>>();
  public final IdAliasColumn<ChildH> id = new IdAliasColumn<ChildH>(this, "id", ChildHCodegen.Shims.id);
  public final StringAliasColumn<ChildH> name = new StringAliasColumn<ChildH>(this, "name", ChildHCodegen.Shims.name);
  public final LongAliasColumn<ChildH> quantity = new LongAliasColumn<ChildH>(this, "quantity", ChildHCodegen.Shims.quantity);
  public final LongAliasColumn<ChildH> version = new LongAliasColumn<ChildH>(this, "version", ChildHCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ChildH, ParentH> parent = new ForeignKeyAliasColumn<ChildH, ParentH>(this, "parent_id", ChildHCodegen.Shims.parentId);

  public ChildHAlias() {
    this("ch0", null, true);
  }

  public ChildHAlias(String alias) {
    this(alias, null, true);
  }

  public ChildHAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ChildH.class, "child_h", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.quantity);
    this.columns.add(this.version);
    this.columns.add(this.parent);
  }

  public List<AliasColumn<ChildH, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ChildH> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ChildH> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ChildH> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 45;
  }

  public <T extends DomainObject> JoinClause<T, ChildH> on(ForeignKeyAliasColumn<T, ChildH> on) {
    return new JoinClause<T, ChildH>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ChildH> leftOn(ForeignKeyAliasColumn<T, ChildH> on) {
    return new JoinClause<T, ChildH>("LEFT OUTER JOIN", this, on);
  }

}
