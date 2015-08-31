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

public class ParentCFooAlias extends Alias<ParentCFoo> {

  private final List<AliasColumn<ParentCFoo, ?, ?>> columns = new ArrayList<AliasColumn<ParentCFoo, ?, ?>>();
  public final IdAliasColumn<ParentCFoo> id = new IdAliasColumn<ParentCFoo>(this, "id", ParentCFooCodegen.Shims.id);
  public final StringAliasColumn<ParentCFoo> name = new StringAliasColumn<ParentCFoo>(this, "name", ParentCFooCodegen.Shims.name);
  public final LongAliasColumn<ParentCFoo> version = new LongAliasColumn<ParentCFoo>(this, "version", ParentCFooCodegen.Shims.version);

  public ParentCFooAlias() {
    this("pcf0", null, true);
  }

  public ParentCFooAlias(String alias) {
    this(alias, null, true);
  }

  public ParentCFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentCFoo.class, "parent_c_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentCFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentCFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentCFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentCFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 33;
  }

  public <T extends DomainObject> JoinClause<T, ParentCFoo> on(ForeignKeyAliasColumn<T, ParentCFoo> on) {
    return new JoinClause<T, ParentCFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentCFoo> leftOn(ForeignKeyAliasColumn<T, ParentCFoo> on) {
    return new JoinClause<T, ParentCFoo>("LEFT OUTER JOIN", this, on);
  }

}
