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

public class OneToOneABarAlias extends Alias<OneToOneABar> {

  private final List<AliasColumn<OneToOneABar, ?, ?>> columns = new ArrayList<AliasColumn<OneToOneABar, ?, ?>>();
  public final IdAliasColumn<OneToOneABar> id = new IdAliasColumn<OneToOneABar>(this, "id", OneToOneABarCodegen.Shims.id);
  public final StringAliasColumn<OneToOneABar> name = new StringAliasColumn<OneToOneABar>(this, "name", OneToOneABarCodegen.Shims.name);
  public final LongAliasColumn<OneToOneABar> version = new LongAliasColumn<OneToOneABar>(this, "version", OneToOneABarCodegen.Shims.version);
  public final ForeignKeyAliasColumn<OneToOneABar, OneToOneAFoo> oneToOneAFoo = new ForeignKeyAliasColumn<OneToOneABar, OneToOneAFoo>(this, "one_to_one_a_foo_id", OneToOneABarCodegen.Shims.oneToOneAFooId);

  public OneToOneABarAlias() {
    this("otoab0", null, true);
  }

  public OneToOneABarAlias(String alias) {
    this(alias, null, true);
  }

  public OneToOneABarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(OneToOneABar.class, "one_to_one_a_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.oneToOneAFoo);
  }

  public List<AliasColumn<OneToOneABar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<OneToOneABar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<OneToOneABar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<OneToOneABar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 23;
  }

  public <T extends DomainObject> JoinClause<T, OneToOneABar> on(ForeignKeyAliasColumn<T, OneToOneABar> on) {
    return new JoinClause<T, OneToOneABar>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, OneToOneABar> leftOn(ForeignKeyAliasColumn<T, OneToOneABar> on) {
    return new JoinClause<T, OneToOneABar>("LEFT OUTER JOIN", this, on);
  }

}
