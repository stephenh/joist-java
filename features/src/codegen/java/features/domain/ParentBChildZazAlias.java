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

public class ParentBChildZazAlias extends Alias<ParentBChildZaz> {

  private final List<AliasColumn<ParentBChildZaz, ?, ?>> columns = new ArrayList<AliasColumn<ParentBChildZaz, ?, ?>>();
  public final IdAliasColumn<ParentBChildZaz> id = new IdAliasColumn<ParentBChildZaz>(this, "id", ParentBChildZazCodegen.Shims.id);
  public final StringAliasColumn<ParentBChildZaz> name = new StringAliasColumn<ParentBChildZaz>(this, "name", ParentBChildZazCodegen.Shims.name);
  public final LongAliasColumn<ParentBChildZaz> version = new LongAliasColumn<ParentBChildZaz>(this, "version", ParentBChildZazCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentBChildZaz, ParentBChildBar> parentBChildBar = new ForeignKeyAliasColumn<ParentBChildZaz, ParentBChildBar>(this, "parent_b_child_bar_id", ParentBChildZazCodegen.Shims.parentBChildBarId);
  public final ForeignKeyAliasColumn<ParentBChildZaz, ParentBParent> parentBParent = new ForeignKeyAliasColumn<ParentBChildZaz, ParentBParent>(this, "parent_b_parent_id", ParentBChildZazCodegen.Shims.parentBParentId);

  public ParentBChildZazAlias() {
    this("pbcz0", null, true);
  }

  public ParentBChildZazAlias(String alias) {
    this(alias, null, true);
  }

  public ParentBChildZazAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentBChildZaz.class, "parent_b_child_zaz", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentBChildBar);
    this.columns.add(this.parentBParent);
  }

  public List<AliasColumn<ParentBChildZaz, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentBChildZaz> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentBChildZaz> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentBChildZaz> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 32;
  }

  public <T extends DomainObject> JoinClause<T, ParentBChildZaz> on(ForeignKeyAliasColumn<T, ParentBChildZaz> on) {
    return new JoinClause<T, ParentBChildZaz>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentBChildZaz> leftOn(ForeignKeyAliasColumn<T, ParentBChildZaz> on) {
    return new JoinClause<T, ParentBChildZaz>("LEFT OUTER JOIN", this, on);
  }

}
