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

public class ManyToManyBFooAlias extends Alias<ManyToManyBFoo> {

  private final List<AliasColumn<ManyToManyBFoo, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyBFoo, ?, ?>>();
  public final IdAliasColumn<ManyToManyBFoo> id = new IdAliasColumn<ManyToManyBFoo>(this, "id", ManyToManyBFooCodegen.Shims.id);
  public final StringAliasColumn<ManyToManyBFoo> name = new StringAliasColumn<ManyToManyBFoo>(this, "name", ManyToManyBFooCodegen.Shims.name);
  public final LongAliasColumn<ManyToManyBFoo> version = new LongAliasColumn<ManyToManyBFoo>(this, "version", ManyToManyBFooCodegen.Shims.version);

  public ManyToManyBFooAlias() {
    this("mtmbf0", null, true);
  }

  public ManyToManyBFooAlias(String alias) {
    this(alias, null, true);
  }

  public ManyToManyBFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ManyToManyBFoo.class, "many_to_many_b_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ManyToManyBFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ManyToManyBFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ManyToManyBFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ManyToManyBFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 20;
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyBFoo> on(ForeignKeyAliasColumn<T, ManyToManyBFoo> on) {
    return new JoinClause<T, ManyToManyBFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyBFoo> leftOn(ForeignKeyAliasColumn<T, ManyToManyBFoo> on) {
    return new JoinClause<T, ManyToManyBFoo>("LEFT OUTER JOIN", this, on);
  }

}
