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

public class ParentDChildAAlias extends Alias<ParentDChildA> {

  private final List<AliasColumn<ParentDChildA, ?, ?>> columns = new ArrayList<AliasColumn<ParentDChildA, ?, ?>>();
  public final IdAliasColumn<ParentDChildA> id = new IdAliasColumn<ParentDChildA>(this, "id", ParentDChildACodegen.Shims.id);
  public final StringAliasColumn<ParentDChildA> name = new StringAliasColumn<ParentDChildA>(this, "name", ParentDChildACodegen.Shims.name);
  public final LongAliasColumn<ParentDChildA> version = new LongAliasColumn<ParentDChildA>(this, "version", ParentDChildACodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentDChildA, ParentD> parentD = new ForeignKeyAliasColumn<ParentDChildA, ParentD>(this, "parent_d_id", ParentDChildACodegen.Shims.parentDId);

  public ParentDChildAAlias() {
    this("pdca0", null, true);
  }

  public ParentDChildAAlias(String alias) {
    this(alias, null, true);
  }

  public ParentDChildAAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentDChildA.class, "parent_d_child_a", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentD);
  }

  public List<AliasColumn<ParentDChildA, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentDChildA> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentDChildA> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentDChildA> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 36;
  }

  public <T extends DomainObject> JoinClause<T, ParentDChildA> on(ForeignKeyAliasColumn<T, ParentDChildA> on) {
    return new JoinClause<T, ParentDChildA>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentDChildA> leftOn(ForeignKeyAliasColumn<T, ParentDChildA> on) {
    return new JoinClause<T, ParentDChildA>("LEFT OUTER JOIN", this, on);
  }

}
