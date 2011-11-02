package features.domain;

import java.util.ArrayList;
import java.util.List;

import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceASubOneAlias extends Alias<InheritanceASubOne> {

  private final List<AliasColumn<InheritanceASubOne, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceASubOne, ?, ?>>();
  private final IdAliasColumn<InheritanceASubOne> subClassId = new IdAliasColumn<InheritanceASubOne>(this, "id", null);
  public final StringAliasColumn<InheritanceASubOne> one = new StringAliasColumn<InheritanceASubOne>(this, "one", InheritanceASubOneCodegen.Shims.one);
  public final ForeignKeyAliasColumn<InheritanceASubOne, InheritanceAThing> inheritanceAThing = new ForeignKeyAliasColumn<InheritanceASubOne, InheritanceAThing>(this, "inheritance_a_thing_id", InheritanceASubOneCodegen.Shims.inheritanceAThingId);
  private final InheritanceABaseAlias baseAlias;
  public final IdAliasColumn<InheritanceABase> id;
  public final StringAliasColumn<InheritanceABase> name;
  public final LongAliasColumn<InheritanceABase> version;

  public InheritanceASubOneAlias(String alias) {
    this(alias, null, true);
  }

  public InheritanceASubOneAlias(String alias, InheritanceABaseAlias baseAlias, boolean addSubClasses) {
    super(InheritanceASubOne.class, "inheritance_a_sub_one", alias);
    this.baseAlias = (baseAlias != null) ? baseAlias : new InheritanceABaseAlias(alias + "_b", null, false);
    this.columns.add(this.one);
    this.columns.add(this.inheritanceAThing);
    this.id = this.baseAlias.id;
    this.name = this.baseAlias.name;
    this.version = this.baseAlias.version;
  }

  public List<AliasColumn<InheritanceASubOne, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<InheritanceABase> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<InheritanceABase> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<InheritanceASubOne> getSubClassIdColumn() {
    return this.subClassId;
  }

  public Alias<InheritanceABase> getBaseClassAlias() {
    return this.baseAlias;
  }

  public int getOrder() {
    return 4;
  }

}
