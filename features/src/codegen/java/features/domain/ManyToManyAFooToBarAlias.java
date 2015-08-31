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

public class ManyToManyAFooToBarAlias extends Alias<ManyToManyAFooToBar> {

  private final List<AliasColumn<ManyToManyAFooToBar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyAFooToBar, ?, ?>>();
  public final IdAliasColumn<ManyToManyAFooToBar> id = new IdAliasColumn<ManyToManyAFooToBar>(this, "id", ManyToManyAFooToBarCodegen.Shims.id);
  public final LongAliasColumn<ManyToManyAFooToBar> version = new LongAliasColumn<ManyToManyAFooToBar>(this, "version", ManyToManyAFooToBarCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyABar> manyToManyABar = new ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyABar>(this, "many_to_many_a_bar_id", ManyToManyAFooToBarCodegen.Shims.manyToManyABarId);
  public final ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyAFoo> manyToManyAFoo = new ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyAFoo>(this, "many_to_many_a_foo_id", ManyToManyAFooToBarCodegen.Shims.manyToManyAFooId);

  public ManyToManyAFooToBarAlias() {
    this("mtmaftb0", null, true);
  }

  public ManyToManyAFooToBarAlias(String alias) {
    this(alias, null, true);
  }

  public ManyToManyAFooToBarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ManyToManyAFooToBar.class, "many_to_many_a_foo_to_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.version);
    this.columns.add(this.manyToManyABar);
    this.columns.add(this.manyToManyAFoo);
  }

  public List<AliasColumn<ManyToManyAFooToBar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ManyToManyAFooToBar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ManyToManyAFooToBar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ManyToManyAFooToBar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 18;
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyAFooToBar> on(ForeignKeyAliasColumn<T, ManyToManyAFooToBar> on) {
    return new JoinClause<T, ManyToManyAFooToBar>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyAFooToBar> leftOn(ForeignKeyAliasColumn<T, ManyToManyAFooToBar> on) {
    return new JoinClause<T, ManyToManyAFooToBar>("LEFT OUTER JOIN", this, on);
  }

}
