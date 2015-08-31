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

public class ParentBChildFooAlias extends Alias<ParentBChildFoo> {

  private final List<AliasColumn<ParentBChildFoo, ?, ?>> columns = new ArrayList<AliasColumn<ParentBChildFoo, ?, ?>>();
  public final IdAliasColumn<ParentBChildFoo> id = new IdAliasColumn<ParentBChildFoo>(this, "id", ParentBChildFooCodegen.Shims.id);
  public final StringAliasColumn<ParentBChildFoo> name = new StringAliasColumn<ParentBChildFoo>(this, "name", ParentBChildFooCodegen.Shims.name);
  public final LongAliasColumn<ParentBChildFoo> version = new LongAliasColumn<ParentBChildFoo>(this, "version", ParentBChildFooCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentBChildFoo, ParentBParent> parentBParent = new ForeignKeyAliasColumn<ParentBChildFoo, ParentBParent>(this, "parent_b_parent_id", ParentBChildFooCodegen.Shims.parentBParentId);

  public ParentBChildFooAlias() {
    this("pbcf0", null, true);
  }

  public ParentBChildFooAlias(String alias) {
    this(alias, null, true);
  }

  public ParentBChildFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentBChildFoo.class, "parent_b_child_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.parentBParent);
  }

  public List<AliasColumn<ParentBChildFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentBChildFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentBChildFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentBChildFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 31;
  }

  public <T extends DomainObject> JoinClause<T, ParentBChildFoo> on(ForeignKeyAliasColumn<T, ParentBChildFoo> on) {
    return new JoinClause<T, ParentBChildFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentBChildFoo> leftOn(ForeignKeyAliasColumn<T, ParentBChildFoo> on) {
    return new JoinClause<T, ParentBChildFoo>("LEFT OUTER JOIN", this, on);
  }

}
