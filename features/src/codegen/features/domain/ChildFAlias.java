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

public class ChildFAlias extends Alias<ChildF> {

  private final List<AliasColumn<ChildF, ?, ?>> columns = new ArrayList<AliasColumn<ChildF, ?, ?>>();
  public final IdAliasColumn<ChildF> id = new IdAliasColumn<ChildF>(this, "id", ChildFCodegen.Shims.id);
  public final StringAliasColumn<ChildF> name = new StringAliasColumn<ChildF>(this, "name", ChildFCodegen.Shims.name);
  public final LongAliasColumn<ChildF> version = new LongAliasColumn<ChildF>(this, "version", ChildFCodegen.Shims.version);

  public ChildFAlias() {
    this("cf0", null, true);
  }

  public ChildFAlias(String alias) {
    this(alias, null, true);
  }

  public ChildFAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ChildF.class, "child_f", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ChildF, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ChildF> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ChildF> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ChildF> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 0;
  }

  public <T extends DomainObject> JoinClause<T, ChildF> on(ForeignKeyAliasColumn<T, ChildF> on) {
    return new JoinClause<T, ChildF>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ChildF> leftOn(ForeignKeyAliasColumn<T, ChildF> on) {
    return new JoinClause<T, ChildF>("LEFT OUTER JOIN", this, on);
  }

}
