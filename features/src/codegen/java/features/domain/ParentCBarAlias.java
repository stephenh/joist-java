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

public class ParentCBarAlias extends Alias<ParentCBar> {

  private final List<AliasColumn<ParentCBar, ?, ?>> columns = new ArrayList<AliasColumn<ParentCBar, ?, ?>>();
  public final IdAliasColumn<ParentCBar> id = new IdAliasColumn<ParentCBar>(this, "id", ParentCBarCodegen.Shims.id);
  public final StringAliasColumn<ParentCBar> name = new StringAliasColumn<ParentCBar>(this, "name", ParentCBarCodegen.Shims.name);
  public final LongAliasColumn<ParentCBar> version = new LongAliasColumn<ParentCBar>(this, "version", ParentCBarCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentCBar, ParentCFoo> firstParent = new ForeignKeyAliasColumn<ParentCBar, ParentCFoo>(this, "first_parent_id", ParentCBarCodegen.Shims.firstParentId);
  public final ForeignKeyAliasColumn<ParentCBar, ParentCFoo> secondParent = new ForeignKeyAliasColumn<ParentCBar, ParentCFoo>(this, "second_parent_id", ParentCBarCodegen.Shims.secondParentId);

  public ParentCBarAlias() {
    this("pcb0", null, true);
  }

  public ParentCBarAlias(String alias) {
    this(alias, null, true);
  }

  public ParentCBarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentCBar.class, "parent_c_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.firstParent);
    this.columns.add(this.secondParent);
  }

  public List<AliasColumn<ParentCBar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentCBar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentCBar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentCBar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 34;
  }

  public <T extends DomainObject> JoinClause<T, ParentCBar> on(ForeignKeyAliasColumn<T, ParentCBar> on) {
    return new JoinClause<T, ParentCBar>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentCBar> leftOn(ForeignKeyAliasColumn<T, ParentCBar> on) {
    return new JoinClause<T, ParentCBar>("LEFT OUTER JOIN", this, on);
  }

}
