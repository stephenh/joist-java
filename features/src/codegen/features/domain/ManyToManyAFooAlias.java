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

public class ManyToManyAFooAlias extends Alias<ManyToManyAFoo> {

  private final List<AliasColumn<ManyToManyAFoo, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyAFoo, ?, ?>>();
  public final IdAliasColumn<ManyToManyAFoo> id = new IdAliasColumn<ManyToManyAFoo>(this, "id", ManyToManyAFooCodegen.Shims.id);
  public final StringAliasColumn<ManyToManyAFoo> name = new StringAliasColumn<ManyToManyAFoo>(this, "name", ManyToManyAFooCodegen.Shims.name);
  public final LongAliasColumn<ManyToManyAFoo> version = new LongAliasColumn<ManyToManyAFoo>(this, "version", ManyToManyAFooCodegen.Shims.version);

  public ManyToManyAFooAlias() {
    this("mtmaf0", null, true);
  }

  public ManyToManyAFooAlias(String alias) {
    this(alias, null, true);
  }

  public ManyToManyAFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ManyToManyAFoo.class, "many_to_many_a_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ManyToManyAFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ManyToManyAFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ManyToManyAFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ManyToManyAFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 17;
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyAFoo> on(ForeignKeyAliasColumn<T, ManyToManyAFoo> on) {
    return new JoinClause<T, ManyToManyAFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyAFoo> leftOn(ForeignKeyAliasColumn<T, ManyToManyAFoo> on) {
    return new JoinClause<T, ManyToManyAFoo>("LEFT OUTER JOIN", this, on);
  }

}
