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

public class ChildIAAlias extends Alias<ChildIA> {

  private final List<AliasColumn<ChildIA, ?, ?>> columns = new ArrayList<AliasColumn<ChildIA, ?, ?>>();
  public final IdAliasColumn<ChildIA> id = new IdAliasColumn<ChildIA>(this, "id", ChildIACodegen.Shims.id);
  public final LongAliasColumn<ChildIA> version = new LongAliasColumn<ChildIA>(this, "version", ChildIACodegen.Shims.version);
  public final ForeignKeyAliasColumn<ChildIA, ParentI> parent = new ForeignKeyAliasColumn<ChildIA, ParentI>(this, "parent_id", ChildIACodegen.Shims.parentId);

  public ChildIAAlias() {
    this("cia0", null, true);
  }

  public ChildIAAlias(String alias) {
    this(alias, null, true);
  }

  public ChildIAAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ChildIA.class, "child_i_a", alias);
    this.columns.add(this.id);
    this.columns.add(this.version);
    this.columns.add(this.parent);
  }

  public List<AliasColumn<ChildIA, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ChildIA> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ChildIA> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ChildIA> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 47;
  }

  public <T extends DomainObject> JoinClause<T, ChildIA> on(ForeignKeyAliasColumn<T, ChildIA> on) {
    return new JoinClause<T, ChildIA>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ChildIA> leftOn(ForeignKeyAliasColumn<T, ChildIA> on) {
    return new JoinClause<T, ChildIA>("LEFT OUTER JOIN", this, on);
  }

}
