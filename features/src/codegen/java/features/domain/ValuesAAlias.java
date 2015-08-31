package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class ValuesAAlias extends Alias<ValuesA> {

  private final List<AliasColumn<ValuesA, ?, ?>> columns = new ArrayList<AliasColumn<ValuesA, ?, ?>>();
  public final StringAliasColumn<ValuesA> a = new StringAliasColumn<ValuesA>(this, "a", ValuesACodegen.Shims.a);
  public final StringAliasColumn<ValuesA> b = new StringAliasColumn<ValuesA>(this, "b", ValuesACodegen.Shims.b);
  public final IntAliasColumn<ValuesA> i = new IntAliasColumn<ValuesA>(this, "i", ValuesACodegen.Shims.i);
  public final IdAliasColumn<ValuesA> id = new IdAliasColumn<ValuesA>(this, "id", ValuesACodegen.Shims.id);
  public final IntAliasColumn<ValuesA> j = new IntAliasColumn<ValuesA>(this, "j", ValuesACodegen.Shims.j);
  public final StringAliasColumn<ValuesA> name = new StringAliasColumn<ValuesA>(this, "name", ValuesACodegen.Shims.name);
  public final LongAliasColumn<ValuesA> version = new LongAliasColumn<ValuesA>(this, "version", ValuesACodegen.Shims.version);

  public ValuesAAlias() {
    this("va0", null, true);
  }

  public ValuesAAlias(String alias) {
    this(alias, null, true);
  }

  public ValuesAAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ValuesA.class, "values_a", alias);
    this.columns.add(this.a);
    this.columns.add(this.b);
    this.columns.add(this.i);
    this.columns.add(this.id);
    this.columns.add(this.j);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ValuesA, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ValuesA> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ValuesA> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ValuesA> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 54;
  }

  public <T extends DomainObject> JoinClause<T, ValuesA> on(ForeignKeyAliasColumn<T, ValuesA> on) {
    return new JoinClause<T, ValuesA>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ValuesA> leftOn(ForeignKeyAliasColumn<T, ValuesA> on) {
    return new JoinClause<T, ValuesA>("LEFT OUTER JOIN", this, on);
  }

}
