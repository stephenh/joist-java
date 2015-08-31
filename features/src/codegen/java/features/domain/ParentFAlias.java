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

public class ParentFAlias extends Alias<ParentF> {

  private final List<AliasColumn<ParentF, ?, ?>> columns = new ArrayList<AliasColumn<ParentF, ?, ?>>();
  public final IdAliasColumn<ParentF> id = new IdAliasColumn<ParentF>(this, "id", ParentFCodegen.Shims.id);
  public final StringAliasColumn<ParentF> name = new StringAliasColumn<ParentF>(this, "name", ParentFCodegen.Shims.name);
  public final LongAliasColumn<ParentF> version = new LongAliasColumn<ParentF>(this, "version", ParentFCodegen.Shims.version);
  public final ForeignKeyAliasColumn<ParentF, ChildF> childOne = new ForeignKeyAliasColumn<ParentF, ChildF>(this, "child_one_id", ParentFCodegen.Shims.childOneId);
  public final ForeignKeyAliasColumn<ParentF, ChildF> childTwo = new ForeignKeyAliasColumn<ParentF, ChildF>(this, "child_two_id", ParentFCodegen.Shims.childTwoId);

  public ParentFAlias() {
    this("pf0", null, true);
  }

  public ParentFAlias(String alias) {
    this(alias, null, true);
  }

  public ParentFAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentF.class, "parent_f", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.childOne);
    this.columns.add(this.childTwo);
  }

  public List<AliasColumn<ParentF, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentF> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentF> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentF> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 41;
  }

  public <T extends DomainObject> JoinClause<T, ParentF> on(ForeignKeyAliasColumn<T, ParentF> on) {
    return new JoinClause<T, ParentF>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentF> leftOn(ForeignKeyAliasColumn<T, ParentF> on) {
    return new JoinClause<T, ParentF>("LEFT OUTER JOIN", this, on);
  }

}
