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

public class InheritanceBRootAlias extends Alias<InheritanceBRoot> {

  private final List<AliasColumn<InheritanceBRoot, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceBRoot, ?, ?>>();
  public final IdAliasColumn<InheritanceBRoot> id = new IdAliasColumn<InheritanceBRoot>(this, "id", InheritanceBRootCodegen.Shims.id);
  public final StringAliasColumn<InheritanceBRoot> name = new StringAliasColumn<InheritanceBRoot>(this, "name", InheritanceBRootCodegen.Shims.name);
  public final LongAliasColumn<InheritanceBRoot> version = new LongAliasColumn<InheritanceBRoot>(this, "version", InheritanceBRootCodegen.Shims.version);

  public InheritanceBRootAlias() {
    this("ibr0", null, true);
  }

  public InheritanceBRootAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceBRootAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(InheritanceBRoot.class, "inheritance_b_root", alias);
    InheritanceBRootAlias inheritanceBRoot = this;
    if (addSubClasses) {
      InheritanceBMiddleAlias inheritanceBMiddle = new InheritanceBMiddleAlias(alias + "_0", inheritanceBRoot, false);
      this.addSubClassAlias(inheritanceBMiddle);
      InheritanceBBottomAlias inheritanceBBottom = new InheritanceBBottomAlias(alias + "_1", inheritanceBMiddle, false);
      this.addSubClassAlias(inheritanceBBottom);
    }
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<InheritanceBRoot, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceBRoot> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceBRoot> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceBRoot> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 9;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBRoot> on(ForeignKeyAliasColumn<T, InheritanceBRoot> on) {
    return new JoinClause<T, InheritanceBRoot>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceBRoot> leftOn(ForeignKeyAliasColumn<T, InheritanceBRoot> on) {
    return new JoinClause<T, InheritanceBRoot>("LEFT OUTER JOIN", this, on);
  }

}
