package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.CalendarDateAliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.MoneyAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;
import joist.domain.orm.queries.columns.TimePointAliasColumn;

public class PrimitivesCAlias extends Alias<PrimitivesC> {

  private final List<AliasColumn<PrimitivesC, ?, ?>> columns = new ArrayList<AliasColumn<PrimitivesC, ?, ?>>();
  public final CalendarDateAliasColumn<PrimitivesC> day = new CalendarDateAliasColumn<PrimitivesC>(this, "day", PrimitivesCCodegen.Shims.day);
  public final MoneyAliasColumn<PrimitivesC> dollarAmount = new MoneyAliasColumn<PrimitivesC>(this, "dollar_amount", PrimitivesCCodegen.Shims.dollarAmount);
  public final IdAliasColumn<PrimitivesC> id = new IdAliasColumn<PrimitivesC>(this, "id", PrimitivesCCodegen.Shims.id);
  public final StringAliasColumn<PrimitivesC> name = new StringAliasColumn<PrimitivesC>(this, "name", PrimitivesCCodegen.Shims.name);
  public final TimePointAliasColumn<PrimitivesC> timestamp = new TimePointAliasColumn<PrimitivesC>(this, "timestamp", PrimitivesCCodegen.Shims.timestamp);
  public final LongAliasColumn<PrimitivesC> version = new LongAliasColumn<PrimitivesC>(this, "version", PrimitivesCCodegen.Shims.version);

  public PrimitivesCAlias() {
    this("pc0", null, true);
  }

  public PrimitivesCAlias(String alias) {
    this(alias, null, true);
  }

  public PrimitivesCAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(PrimitivesC.class, "primitives_c", alias);
    this.columns.add(this.day);
    this.columns.add(this.dollarAmount);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.timestamp);
    this.columns.add(this.version);
  }

  public List<AliasColumn<PrimitivesC, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<PrimitivesC> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<PrimitivesC> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<PrimitivesC> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 51;
  }

  public <T extends DomainObject> JoinClause<T, PrimitivesC> on(ForeignKeyAliasColumn<T, PrimitivesC> on) {
    return new JoinClause<T, PrimitivesC>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, PrimitivesC> leftOn(ForeignKeyAliasColumn<T, PrimitivesC> on) {
    return new JoinClause<T, PrimitivesC>("LEFT OUTER JOIN", this, on);
  }

}
