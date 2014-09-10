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

public class ManyToManyABarAlias extends Alias<ManyToManyABar> {

  private final List<AliasColumn<ManyToManyABar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyABar, ?, ?>>();
  public final IdAliasColumn<ManyToManyABar> id = new IdAliasColumn<ManyToManyABar>(this, "id", ManyToManyABarCodegen.Shims.id);
  public final StringAliasColumn<ManyToManyABar> name = new StringAliasColumn<ManyToManyABar>(this, "name", ManyToManyABarCodegen.Shims.name);
  public final LongAliasColumn<ManyToManyABar> version = new LongAliasColumn<ManyToManyABar>(this, "version", ManyToManyABarCodegen.Shims.version);

  public ManyToManyABarAlias() {
    this("mtmab0", null, true);
  }

  public ManyToManyABarAlias(String alias) {
    this(alias, null, true);
  }

  public ManyToManyABarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ManyToManyABar.class, "many_to_many_a_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ManyToManyABar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ManyToManyABar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ManyToManyABar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ManyToManyABar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 16;
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyABar> on(ForeignKeyAliasColumn<T, ManyToManyABar> on) {
    return new JoinClause<T, ManyToManyABar>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ManyToManyABar> leftOn(ForeignKeyAliasColumn<T, ManyToManyABar> on) {
    return new JoinClause<T, ManyToManyABar>("LEFT OUTER JOIN", this, on);
  }

}
