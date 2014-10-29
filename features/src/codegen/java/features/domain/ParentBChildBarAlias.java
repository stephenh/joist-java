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

public class ParentBChildBarAlias extends Alias<ParentBChildBar> {

  private final List<AliasColumn<ParentBChildBar, ?, ?>> columns = new ArrayList<AliasColumn<ParentBChildBar, ?, ?>>();
  public final IdAliasColumn<ParentBChildBar> id = new IdAliasColumn<ParentBChildBar>(this, "id", ParentBChildBarCodegen.Shims.id);
  public final StringAliasColumn<ParentBChildBar> name = new StringAliasColumn<ParentBChildBar>(this, "name", ParentBChildBarCodegen.Shims.name);
  public final LongAliasColumn<ParentBChildBar> version = new LongAliasColumn<ParentBChildBar>(this, "version", ParentBChildBarCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentBChildBar, ParentBParent> parentBParent = new ForeignKeyAliasColumn<ParentBChildBar, ParentBParent>(this, "parent_b_parent_id", ParentBChildBarCodegen.Shims.parentBParentId);

  public ParentBChildBarAlias() {
    this("pbcb0", null, true);
  }

  public ParentBChildBarAlias(String alias) {
    this(alias, null, true);
  }

  public ParentBChildBarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentBChildBar.class, "parent_b_child_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentBParent);
  }

  public List<AliasColumn<ParentBChildBar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentBChildBar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentBChildBar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentBChildBar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 30;
  }

  public <T extends DomainObject> JoinClause<T, ParentBChildBar> on(ForeignKeyAliasColumn<T, ParentBChildBar> on) {
    return new JoinClause<T, ParentBChildBar>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentBChildBar> leftOn(ForeignKeyAliasColumn<T, ParentBChildBar> on) {
    return new JoinClause<T, ParentBChildBar>("LEFT OUTER JOIN", this, on);
  }

}
