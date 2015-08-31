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

public class ParentGAlias extends Alias<ParentG> {

  private final List<AliasColumn<ParentG, ?, ?>> columns = new ArrayList<AliasColumn<ParentG, ?, ?>>();
  public final IdAliasColumn<ParentG> id = new IdAliasColumn<ParentG>(this, "id", ParentGCodegen.Shims.id);
  public final StringAliasColumn<ParentG> name = new StringAliasColumn<ParentG>(this, "name", ParentGCodegen.Shims.name);
  public final LongAliasColumn<ParentG> version = new LongAliasColumn<ParentG>(this, "version", ParentGCodegen.Shims.version);

  public ParentGAlias() {
    this("pg0", null, true);
  }

  public ParentGAlias(String alias) {
    this(alias, null, true);
  }

  public ParentGAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentG.class, "parent_g", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentG, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentG> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentG> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentG> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 42;
  }

  public <T extends DomainObject> JoinClause<T, ParentG> on(ForeignKeyAliasColumn<T, ParentG> on) {
    return new JoinClause<T, ParentG>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentG> leftOn(ForeignKeyAliasColumn<T, ParentG> on) {
    return new JoinClause<T, ParentG>("LEFT OUTER JOIN", this, on);
  }

}
