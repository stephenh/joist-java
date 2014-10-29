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

public class ParentDChildCAlias extends Alias<ParentDChildC> {

  private final List<AliasColumn<ParentDChildC, ?, ?>> columns = new ArrayList<AliasColumn<ParentDChildC, ?, ?>>();
  public final IdAliasColumn<ParentDChildC> id = new IdAliasColumn<ParentDChildC>(this, "id", ParentDChildCCodegen.Shims.id);
  public final StringAliasColumn<ParentDChildC> name = new StringAliasColumn<ParentDChildC>(this, "name", ParentDChildCCodegen.Shims.name);
  public final LongAliasColumn<ParentDChildC> version = new LongAliasColumn<ParentDChildC>(this, "version", ParentDChildCCodegen.Shims.version);

  public ParentDChildCAlias() {
    this("pdcc0", null, true);
  }

  public ParentDChildCAlias(String alias) {
    this(alias, null, true);
  }

  public ParentDChildCAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentDChildC.class, "parent_d_child_c", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentDChildC, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentDChildC> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentDChildC> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentDChildC> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 38;
  }

  public <T extends DomainObject> JoinClause<T, ParentDChildC> on(ForeignKeyAliasColumn<T, ParentDChildC> on) {
    return new JoinClause<T, ParentDChildC>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentDChildC> leftOn(ForeignKeyAliasColumn<T, ParentDChildC> on) {
    return new JoinClause<T, ParentDChildC>("LEFT OUTER JOIN", this, on);
  }

}
