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

public class ParentBParentAlias extends Alias<ParentBParent> {

  private final List<AliasColumn<ParentBParent, ?, ?>> columns = new ArrayList<AliasColumn<ParentBParent, ?, ?>>();
  public final IdAliasColumn<ParentBParent> id = new IdAliasColumn<ParentBParent>(this, "id", ParentBParentCodegen.Shims.id);
  public final StringAliasColumn<ParentBParent> name = new StringAliasColumn<ParentBParent>(this, "name", ParentBParentCodegen.Shims.name);
  public final LongAliasColumn<ParentBParent> version = new LongAliasColumn<ParentBParent>(this, "version", ParentBParentCodegen.Shims.version);

  public ParentBParentAlias() {
    this("pbp0", null, true);
  }

  public ParentBParentAlias(String alias) {
    this(alias, null, true);
  }

  public ParentBParentAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentBParent.class, "parent_b_parent", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentBParent, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentBParent> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentBParent> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentBParent> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 29;
  }

  public <T extends DomainObject> JoinClause<T, ParentBParent> on(ForeignKeyAliasColumn<T, ParentBParent> on) {
    return new JoinClause<T, ParentBParent>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentBParent> leftOn(ForeignKeyAliasColumn<T, ParentBParent> on) {
    return new JoinClause<T, ParentBParent>("LEFT OUTER JOIN", this, on);
  }

}
