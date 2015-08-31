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

public class InheritanceAThingAlias extends Alias<InheritanceAThing> {

  private final List<AliasColumn<InheritanceAThing, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceAThing, ?, ?>>();
  public final IdAliasColumn<InheritanceAThing> id = new IdAliasColumn<InheritanceAThing>(this, "id", InheritanceAThingCodegen.Shims.id);
  public final StringAliasColumn<InheritanceAThing> name = new StringAliasColumn<InheritanceAThing>(this, "name", InheritanceAThingCodegen.Shims.name);
  public final LongAliasColumn<InheritanceAThing> version = new LongAliasColumn<InheritanceAThing>(this, "version", InheritanceAThingCodegen.Shims.version);

  public InheritanceAThingAlias() {
    this("iat0", null, true);
  }

  public InheritanceAThingAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceAThingAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(InheritanceAThing.class, "inheritance_a_thing", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<InheritanceAThing, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceAThing> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceAThing> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceAThing> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 5;
  }

  public <T extends DomainObject> JoinClause<T, InheritanceAThing> on(ForeignKeyAliasColumn<T, InheritanceAThing> on) {
    return new JoinClause<T, InheritanceAThing>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, InheritanceAThing> leftOn(ForeignKeyAliasColumn<T, InheritanceAThing> on) {
    return new JoinClause<T, InheritanceAThing>("LEFT OUTER JOIN", this, on);
  }

}
