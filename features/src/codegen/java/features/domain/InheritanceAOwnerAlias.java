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

public class InheritanceAOwnerAlias extends Alias<InheritanceAOwner> {

  private final List<AliasColumn<InheritanceAOwner, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceAOwner, ?, ?>>();
  public final IdAliasColumn<InheritanceAOwner> id = new IdAliasColumn<InheritanceAOwner>(this, "id", InheritanceAOwnerCodegen.Shims.id);
  public final StringAliasColumn<InheritanceAOwner> name = new StringAliasColumn<InheritanceAOwner>(this, "name", InheritanceAOwnerCodegen.Shims.name);
  public final LongAliasColumn<InheritanceAOwner> version = new LongAliasColumn<InheritanceAOwner>(this, "version", InheritanceAOwnerCodegen.Shims.version);

  public InheritanceAOwnerAlias() {
    this("iao0", null, true);
  }

  public InheritanceAOwnerAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceAOwnerAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(InheritanceAOwner.class, "inheritance_a_owner", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<InheritanceAOwner, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceAOwner> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceAOwner> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceAOwner> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 3;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceAOwner> on(ForeignKeyAliasColumn<T, InheritanceAOwner> on) {
    return new JoinClause<T, InheritanceAOwner>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceAOwner> leftOn(ForeignKeyAliasColumn<T, InheritanceAOwner> on) {
    return new JoinClause<T, InheritanceAOwner>("LEFT OUTER JOIN", this, on);
  }

}
