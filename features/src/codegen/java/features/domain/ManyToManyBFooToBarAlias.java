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

public class ManyToManyBFooToBarAlias extends Alias<ManyToManyBFooToBar> {

  private final List<AliasColumn<ManyToManyBFooToBar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyBFooToBar, ?, ?>>();
  public final IdAliasColumn<ManyToManyBFooToBar> id = new IdAliasColumn<ManyToManyBFooToBar>(this, "id", ManyToManyBFooToBarCodegen.Shims.id);
  public final LongAliasColumn<ManyToManyBFooToBar> version = new LongAliasColumn<ManyToManyBFooToBar>(this, "version", ManyToManyBFooToBarCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBBar> owned = new ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBBar>(this, "owned_id", ManyToManyBFooToBarCodegen.Shims.ownedId);
  public final ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBFoo> ownerManyToManyBFoo = new ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBFoo>(this, "owner_many_to_many_b_foo_id", ManyToManyBFooToBarCodegen.Shims.ownerManyToManyBFooId);

  public ManyToManyBFooToBarAlias() {
    this("mtmbftb0", null, true);
  }

  public ManyToManyBFooToBarAlias(String alias) {
    this(alias, null, true);
  }

  public ManyToManyBFooToBarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ManyToManyBFooToBar.class, "many_to_many_b_foo_to_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.version);
    this.columns.add(this.owned);
    this.columns.add(this.ownerManyToManyBFoo);
  }

  public List<AliasColumn<ManyToManyBFooToBar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ManyToManyBFooToBar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ManyToManyBFooToBar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ManyToManyBFooToBar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 21;
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyBFooToBar> on(ForeignKeyAliasColumn<T, ManyToManyBFooToBar> on) {
    return new JoinClause<T, ManyToManyBFooToBar>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyBFooToBar> leftOn(ForeignKeyAliasColumn<T, ManyToManyBFooToBar> on) {
    return new JoinClause<T, ManyToManyBFooToBar>("LEFT OUTER JOIN", this, on);
  }

}
