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

public class ParentDAlias extends Alias<ParentD> {

  private final List<AliasColumn<ParentD, ?, ?>> columns = new ArrayList<AliasColumn<ParentD, ?, ?>>();
  public final IdAliasColumn<ParentD> id = new IdAliasColumn<ParentD>(this, "id", ParentDCodegen.Shims.id);
  public final StringAliasColumn<ParentD> name = new StringAliasColumn<ParentD>(this, "name", ParentDCodegen.Shims.name);
  public final LongAliasColumn<ParentD> version = new LongAliasColumn<ParentD>(this, "version", ParentDCodegen.Shims.version);

  public ParentDAlias() {
    this("pd0", null, true);
  }

  public ParentDAlias(String alias) {
    this(alias, null, true);
  }

  public ParentDAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentD.class, "parent_d", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentD, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentD> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentD> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentD> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 35;
  }

  public <T extends DomainObject> JoinClause<T, ParentD> on(ForeignKeyAliasColumn<T, ParentD> on) {
    return new JoinClause<T, ParentD>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentD> leftOn(ForeignKeyAliasColumn<T, ParentD> on) {
    return new JoinClause<T, ParentD>("LEFT OUTER JOIN", this, on);
  }

}
