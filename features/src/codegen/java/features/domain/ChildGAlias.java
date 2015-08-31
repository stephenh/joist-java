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

public class ChildGAlias extends Alias<ChildG> {

  private final List<AliasColumn<ChildG, ?, ?>> columns = new ArrayList<AliasColumn<ChildG, ?, ?>>();
  public final IdAliasColumn<ChildG> id = new IdAliasColumn<ChildG>(this, "id", ChildGCodegen.Shims.id);
  public final StringAliasColumn<ChildG> name = new StringAliasColumn<ChildG>(this, "name", ChildGCodegen.Shims.name);
  public final LongAliasColumn<ChildG> version = new LongAliasColumn<ChildG>(this, "version", ChildGCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ChildG, ParentG> parentOne = new ForeignKeyAliasColumn<ChildG, ParentG>(this, "parent_one_id", ChildGCodegen.Shims.parentOneId);
  public final ForeignKeyAliasColumn<ChildG, ParentG> parentTwo = new ForeignKeyAliasColumn<ChildG, ParentG>(this, "parent_two_id", ChildGCodegen.Shims.parentTwoId);

  public ChildGAlias() {
    this("cg0", null, true);
  }

  public ChildGAlias(String alias) {
    this(alias, null, true);
  }

  public ChildGAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ChildG.class, "child_g", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentOne);
    this.columns.add(this.parentTwo);
  }

  public List<AliasColumn<ChildG, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ChildG> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ChildG> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ChildG> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 43;
  }

  public <T extends DomainObject> JoinClause<T, ChildG> on(ForeignKeyAliasColumn<T, ChildG> on) {
    return new JoinClause<T, ChildG>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ChildG> leftOn(ForeignKeyAliasColumn<T, ChildG> on) {
    return new JoinClause<T, ChildG>("LEFT OUTER JOIN", this, on);
  }

}
