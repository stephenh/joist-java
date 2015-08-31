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

public class ParentEAlias extends Alias<ParentE> {

  private final List<AliasColumn<ParentE, ?, ?>> columns = new ArrayList<AliasColumn<ParentE, ?, ?>>();
  public final IdAliasColumn<ParentE> id = new IdAliasColumn<ParentE>(this, "id", ParentECodegen.Shims.id);
  public final StringAliasColumn<ParentE> name = new StringAliasColumn<ParentE>(this, "name", ParentECodegen.Shims.name);
  public final LongAliasColumn<ParentE> version = new LongAliasColumn<ParentE>(this, "version", ParentECodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentE, ParentE> parentE = new ForeignKeyAliasColumn<ParentE, ParentE>(this, "parent_e_id", ParentECodegen.Shims.parentEId);

  public ParentEAlias() {
    this("pe0", null, true);
  }

  public ParentEAlias(String alias) {
    this(alias, null, true);
  }

  public ParentEAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentE.class, "parent_e", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentE);
  }

  public List<AliasColumn<ParentE, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentE> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentE> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentE> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 40;
  }

  public <T extends DomainObject> JoinClause<T, ParentE> on(ForeignKeyAliasColumn<T, ParentE> on) {
    return new JoinClause<T, ParentE>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentE> leftOn(ForeignKeyAliasColumn<T, ParentE> on) {
    return new JoinClause<T, ParentE>("LEFT OUTER JOIN", this, on);
  }

}
