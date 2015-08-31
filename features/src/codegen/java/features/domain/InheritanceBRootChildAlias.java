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

public class InheritanceBRootChildAlias extends Alias<InheritanceBRootChild> {

  private final List<AliasColumn<InheritanceBRootChild, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBRootChild, ?, ?>>();
  public final IdAliasColumn<InheritanceBRootChild> id = new IdAliasColumn<InheritanceBRootChild>(this, "id", InheritanceBRootChildCodegen.Shims.id);
  public final StringAliasColumn<InheritanceBRootChild> name = new StringAliasColumn<InheritanceBRootChild>(this, "name", InheritanceBRootChildCodegen.Shims.name);
  public final LongAliasColumn<InheritanceBRootChild> version = new LongAliasColumn<InheritanceBRootChild>(this, "version", InheritanceBRootChildCodegen.Shims.version);
  public final ForeignKeyAliasColumn<InheritanceBRootChild, InheritanceBRoot> inheritanceBRoot = new ForeignKeyAliasColumn<InheritanceBRootChild, InheritanceBRoot>(this, "inheritance_b_root_id", InheritanceBRootChildCodegen.Shims.inheritanceBRootId);

  public InheritanceBRootChildAlias() {
    this("ibrc0", null, true);
  }

  public InheritanceBRootChildAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceBRootChildAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(InheritanceBRootChild.class, "inheritance_b_root_child", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.inheritanceBRoot);
  }

  public List<AliasColumn<InheritanceBRootChild, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceBRootChild> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceBRootChild> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceBRootChild> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 12;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBRootChild> on(ForeignKeyAliasColumn<T, InheritanceBRootChild> on) {
    return new JoinClause<T, InheritanceBRootChild>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBRootChild> leftOn(ForeignKeyAliasColumn<T, InheritanceBRootChild> on) {
    return new JoinClause<T, InheritanceBRootChild>("LEFT OUTER JOIN", this, on);
  }

}
