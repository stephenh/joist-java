package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.BooleanAliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class PrimitivesAlias extends Alias<Primitives> {

  private final List<AliasColumn<Primitives, ?, ?>> columns = new ArrayList<AliasColumn<Primitives, ?, ?>>();
  public final BooleanAliasColumn<Primitives> flag = new BooleanAliasColumn<Primitives>(this, "flag", PrimitivesCodegen.Shims.flag);
  public final IdAliasColumn<Primitives> id = new IdAliasColumn<Primitives>(this, "id", PrimitivesCodegen.Shims.id);
  public final StringAliasColumn<Primitives> name = new StringAliasColumn<Primitives>(this, "name", PrimitivesCodegen.Shims.name);
  public final LongAliasColumn<Primitives> version = new LongAliasColumn<Primitives>(this, "version", PrimitivesCodegen.Shims.version);

  public PrimitivesAlias() {
    this("p0", null, true);
  }

  public PrimitivesAlias(String alias) {
    this(alias, null, true);
  }

  public PrimitivesAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(Primitives.class, "primitives", alias);
    this.columns.add(this.flag);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<Primitives, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<Primitives> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<Primitives> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<Primitives> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 49;
  }

  public <T extends DomainObject> JoinClause<T, Primitives> on(ForeignKeyAliasColumn<T, Primitives> on) {
    return new JoinClause<T, Primitives>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, Primitives> leftOn(ForeignKeyAliasColumn<T, Primitives> on) {
    return new JoinClause<T, Primitives>("LEFT OUTER JOIN", this, on);
  }

}
