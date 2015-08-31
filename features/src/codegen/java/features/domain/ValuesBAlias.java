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
import joist.domain.orm.queries.columns.TimePointAliasColumn;

public class ValuesBAlias extends Alias<ValuesB> {

  private final List<AliasColumn<ValuesB, ?, ?>> columns = new ArrayList<AliasColumn<ValuesB, ?, ?>>();
  public final IdAliasColumn<ValuesB> id = new IdAliasColumn<ValuesB>(this, "id", ValuesBCodegen.Shims.id);
  public final StringAliasColumn<ValuesB> name = new StringAliasColumn<ValuesB>(this, "name", ValuesBCodegen.Shims.name);
  public final TimePointAliasColumn<ValuesB> start = new TimePointAliasColumn<ValuesB>(this, "start", ValuesBCodegen.Shims.start);
  public final LongAliasColumn<ValuesB> version = new LongAliasColumn<ValuesB>(this, "version", ValuesBCodegen.Shims.version);

  public ValuesBAlias() {
    this("vb0", null, true);
  }

  public ValuesBAlias(String alias) {
    this(alias, null, true);
  }

  public ValuesBAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ValuesB.class, "values_b", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.start);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ValuesB, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ValuesB> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ValuesB> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ValuesB> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 55;
  }

  public <T extends DomainObject> JoinClause<T, ValuesB> on(ForeignKeyAliasColumn<T, ValuesB> on) {
    return new JoinClause<T, ValuesB>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ValuesB> leftOn(ForeignKeyAliasColumn<T, ValuesB> on) {
    return new JoinClause<T, ValuesB>("LEFT OUTER JOIN", this, on);
  }

}
