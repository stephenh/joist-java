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

public class ParentHAlias extends Alias<ParentH> {

  private final List<AliasColumn<ParentH, ?, ?>> columns = new ArrayList<AliasColumn<ParentH, ?, ?>>();
  public final IdAliasColumn<ParentH> id = new IdAliasColumn<ParentH>(this, "id", ParentHCodegen.Shims.id);
  public final StringAliasColumn<ParentH> name = new StringAliasColumn<ParentH>(this, "name", ParentHCodegen.Shims.name);
  public final LongAliasColumn<ParentH> threshold = new LongAliasColumn<ParentH>(this, "threshold", ParentHCodegen.Shims.threshold);
  public final LongAliasColumn<ParentH> version = new LongAliasColumn<ParentH>(this, "version", ParentHCodegen.Shims.version);

  public ParentHAlias() {
    this("ph0", null, true);
  }

  public ParentHAlias(String alias) {
    this(alias, null, true);
  }

  public ParentHAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentH.class, "parent_h", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.threshold);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentH, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentH> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentH> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentH> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 44;
  }

  public <T extends DomainObject> JoinClause<T, ParentH> on(ForeignKeyAliasColumn<T, ParentH> on) {
    return new JoinClause<T, ParentH>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentH> leftOn(ForeignKeyAliasColumn<T, ParentH> on) {
    return new JoinClause<T, ParentH>("LEFT OUTER JOIN", this, on);
  }

}
