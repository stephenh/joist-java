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

public class ParentDChildBAlias extends Alias<ParentDChildB> {

  private final List<AliasColumn<ParentDChildB, ?, ?>> columns = new ArrayList<AliasColumn<ParentDChildB, ?, ?>>();
  public final IdAliasColumn<ParentDChildB> id = new IdAliasColumn<ParentDChildB>(this, "id", ParentDChildBCodegen.Shims.id);
  public final StringAliasColumn<ParentDChildB> name = new StringAliasColumn<ParentDChildB>(this, "name", ParentDChildBCodegen.Shims.name);
  public final LongAliasColumn<ParentDChildB> version = new LongAliasColumn<ParentDChildB>(this, "version", ParentDChildBCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentDChildB, ParentD> parentD = new ForeignKeyAliasColumn<ParentDChildB, ParentD>(this, "parent_d_id", ParentDChildBCodegen.Shims.parentDId);

  public ParentDChildBAlias() {
    this("pdcb0", null, true);
  }

  public ParentDChildBAlias(String alias) {
    this(alias, null, true);
  }

  public ParentDChildBAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentDChildB.class, "parent_d_child_b", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentD);
  }

  public List<AliasColumn<ParentDChildB, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentDChildB> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentDChildB> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentDChildB> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 37;
  }

  public <T extends DomainObject> JoinClause<T, ParentDChildB> on(ForeignKeyAliasColumn<T, ParentDChildB> on) {
    return new JoinClause<T, ParentDChildB>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentDChildB> leftOn(ForeignKeyAliasColumn<T, ParentDChildB> on) {
    return new JoinClause<T, ParentDChildB>("LEFT OUTER JOIN", this, on);
  }

}
