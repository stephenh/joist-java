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

public class OneToOneAFooAlias extends Alias<OneToOneAFoo> {

  private final List<AliasColumn<OneToOneAFoo, ?, ?>> columns = new ArrayList<AliasColumn<OneToOneAFoo, ?, ?>>();
  public final IdAliasColumn<OneToOneAFoo> id = new IdAliasColumn<OneToOneAFoo>(this, "id", OneToOneAFooCodegen.Shims.id);
  public final StringAliasColumn<OneToOneAFoo> name = new StringAliasColumn<OneToOneAFoo>(this, "name", OneToOneAFooCodegen.Shims.name);
  public final LongAliasColumn<OneToOneAFoo> version = new LongAliasColumn<OneToOneAFoo>(this, "version", OneToOneAFooCodegen.Shims.version);

  public OneToOneAFooAlias() {
    this("otoaf0", null, true);
  }

  public OneToOneAFooAlias(String alias) {
    this(alias, null, true);
  }

  public OneToOneAFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(OneToOneAFoo.class, "one_to_one_a_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<OneToOneAFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<OneToOneAFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<OneToOneAFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<OneToOneAFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 22;
  }

  public <T extends DomainObject> JoinClause<T, OneToOneAFoo> on(ForeignKeyAliasColumn<T, OneToOneAFoo> on) {
    return new JoinClause<T, OneToOneAFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, OneToOneAFoo> leftOn(ForeignKeyAliasColumn<T, OneToOneAFoo> on) {
    return new JoinClause<T, OneToOneAFoo>("LEFT OUTER JOIN", this, on);
  }

}
