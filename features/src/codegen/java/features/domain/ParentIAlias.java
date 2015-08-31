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

public class ParentIAlias extends Alias<ParentI> {

  private final List<AliasColumn<ParentI, ?, ?>> columns = new ArrayList<AliasColumn<ParentI, ?, ?>>();
  public final IdAliasColumn<ParentI> id = new IdAliasColumn<ParentI>(this, "id", ParentICodegen.Shims.id);
  public final LongAliasColumn<ParentI> version = new LongAliasColumn<ParentI>(this, "version", ParentICodegen.Shims.version);

  public ParentIAlias() {
    this("pi0", null, true);
  }

  public ParentIAlias(String alias) {
    this(alias, null, true);
  }

  public ParentIAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ParentI.class, "parent_i", alias);
    this.columns.add(this.id);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ParentI, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ParentI> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ParentI> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ParentI> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 46;
  }

  public <T extends DomainObject> JoinClause<T, ParentI> on(ForeignKeyAliasColumn<T, ParentI> on) {
    return new JoinClause<T, ParentI>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ParentI> leftOn(ForeignKeyAliasColumn<T, ParentI> on) {
    return new JoinClause<T, ParentI>("LEFT OUTER JOIN", this, on);
  }

}
