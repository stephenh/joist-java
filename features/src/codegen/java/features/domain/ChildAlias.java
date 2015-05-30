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

public class ChildAlias extends Alias<Child> {

  private final List<AliasColumn<Child, ?, ?>> columns = new ArrayList<AliasColumn<Child, ?, ?>>();
  public final IdAliasColumn<Child> id = new IdAliasColumn<Child>(this, "id", ChildCodegen.Shims.id);
  public final StringAliasColumn<Child> name = new StringAliasColumn<Child>(this, "name", ChildCodegen.Shims.name);
  public final LongAliasColumn<Child> version = new LongAliasColumn<Child>(this, "version", ChildCodegen.Shims.version);
  public final ForeignKeyAliasColumn<Child, Parent> parent = new ForeignKeyAliasColumn<Child, Parent>(this, "parent_id", ChildCodegen.Shims.parentId);

  public ChildAlias() {
    this("c0", null, true);
  }

  public ChildAlias(String alias) {
    this(alias, null, true);
  }

  public ChildAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(Child.class, "child", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parent);
  }

  public List<AliasColumn<Child, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<Child> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<Child> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<Child> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 27;
  }

  public <T extends DomainObject> JoinClause<T, Child> on(ForeignKeyAliasColumn<T, Child> on) {
    return new JoinClause<T, Child>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, Child> leftOn(ForeignKeyAliasColumn<T, Child> on) {
    return new JoinClause<T, Child>("LEFT OUTER JOIN", this, on);
  }

}
