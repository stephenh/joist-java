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

public class OneToOneBFooAlias extends Alias<OneToOneBFoo> {

  private final List<AliasColumn<OneToOneBFoo, ?, ?>> columns = new ArrayList<AliasColumn<OneToOneBFoo, ?, ?>>();
  public final IdAliasColumn<OneToOneBFoo> id = new IdAliasColumn<OneToOneBFoo>(this, "id", OneToOneBFooCodegen.Shims.id);
  public final StringAliasColumn<OneToOneBFoo> name = new StringAliasColumn<OneToOneBFoo>(this, "name", OneToOneBFooCodegen.Shims.name);
  public final LongAliasColumn<OneToOneBFoo> version = new LongAliasColumn<OneToOneBFoo>(this, "version", OneToOneBFooCodegen.Shims.version);

  public OneToOneBFooAlias() {
    this("otobf0", null, true);
  }

  public OneToOneBFooAlias(String alias) {
    this(alias, null, true);
  }

  public OneToOneBFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(OneToOneBFoo.class, "one_to_one_b_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<OneToOneBFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<OneToOneBFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<OneToOneBFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<OneToOneBFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 24;
  }

  public <T extends DomainObject> JoinClause<T, OneToOneBFoo> on(ForeignKeyAliasColumn<T, OneToOneBFoo> on) {
    return new JoinClause<T, OneToOneBFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, OneToOneBFoo> leftOn(ForeignKeyAliasColumn<T, OneToOneBFoo> on) {
    return new JoinClause<T, OneToOneBFoo>("LEFT OUTER JOIN", this, on);
  }

}
