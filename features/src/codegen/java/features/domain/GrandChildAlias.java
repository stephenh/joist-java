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

public class GrandChildAlias extends Alias<GrandChild> {

  private final List<AliasColumn<GrandChild, ?, ?>> columns = new ArrayList<AliasColumn<GrandChild, ?, ?>>();
  public final IdAliasColumn<GrandChild> id = new IdAliasColumn<GrandChild>(this, "id", GrandChildCodegen.Shims.id);
  public final StringAliasColumn<GrandChild> name = new StringAliasColumn<GrandChild>(this, "name", GrandChildCodegen.Shims.name);
  public final LongAliasColumn<GrandChild> version = new LongAliasColumn<GrandChild>(this, "version", GrandChildCodegen.Shims.version);
  public final ForeignKeyAliasColumn<GrandChild, Child> child = new ForeignKeyAliasColumn<GrandChild, Child>(this, "child_id", GrandChildCodegen.Shims.childId);

  public GrandChildAlias() {
    this("gc0", null, true);
  }

  public GrandChildAlias(String alias) {
    this(alias, null, true);
  }

  public GrandChildAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(GrandChild.class, "grand_child", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.child);
  }

  public List<AliasColumn<GrandChild, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<GrandChild> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<GrandChild> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<GrandChild> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 28;
  }

  public <T extends DomainObject> JoinClause<T, GrandChild> on(ForeignKeyAliasColumn<T, GrandChild> on) {
    return new JoinClause<T, GrandChild>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, GrandChild> leftOn(ForeignKeyAliasColumn<T, GrandChild> on) {
    return new JoinClause<T, GrandChild>("LEFT OUTER JOIN", this, on);
  }

}
