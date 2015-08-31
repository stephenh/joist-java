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

public class ParentDToChildCAlias extends Alias<ParentDToChildC> {

  private final List<AliasColumn<ParentDToChildC, ?, ?>> columns = new ArrayList<AliasColumn<ParentDToChildC, ?, ?>>();
  public final IdAliasColumn<ParentDToChildC> id = new IdAliasColumn<ParentDToChildC>(this, "id", ParentDToChildCCodegen.Shims.id);
  public final LongAliasColumn<ParentDToChildC> version = new LongAliasColumn<ParentDToChildC>(this, "version", ParentDToChildCCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentDToChildC, ParentDChildC> parentDChildC = new ForeignKeyAliasColumn<ParentDToChildC, ParentDChildC>(this, "parent_d_child_c_id", ParentDToChildCCodegen.Shims.parentDChildCId);
  public final ForeignKeyAliasColumn<ParentDToChildC, ParentD> parentD = new ForeignKeyAliasColumn<ParentDToChildC, ParentD>(this, "parent_d_id", ParentDToChildCCodegen.Shims.parentDId);

  public ParentDToChildCAlias() {
    this("pdtcc0", null, true);
  }

  public ParentDToChildCAlias(String alias) {
    this(alias, null, true);
  }

  public ParentDToChildCAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentDToChildC.class, "parent_d_to_child_c", alias);
    this.columns.add(this.id);
    this.columns.add(this.version);
    this.columns.add(this.parentDChildC);
    this.columns.add(this.parentD);
  }

  public List<AliasColumn<ParentDToChildC, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentDToChildC> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentDToChildC> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentDToChildC> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 39;
  }

  public <T extends DomainObject> JoinClause<T, ParentDToChildC> on(ForeignKeyAliasColumn<T, ParentDToChildC> on) {
    return new JoinClause<T, ParentDToChildC>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentDToChildC> leftOn(ForeignKeyAliasColumn<T, ParentDToChildC> on) {
    return new JoinClause<T, ParentDToChildC>("LEFT OUTER JOIN", this, on);
  }

}
