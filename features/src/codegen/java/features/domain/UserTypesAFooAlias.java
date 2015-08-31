package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.CalendarDateAliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class UserTypesAFooAlias extends Alias<UserTypesAFoo> {

  private final List<AliasColumn<UserTypesAFoo, ?, ?>> columns = new ArrayList<AliasColumn<UserTypesAFoo, ?, ?>>();
  public final CalendarDateAliasColumn<UserTypesAFoo> created = new CalendarDateAliasColumn<UserTypesAFoo>(this, "created", UserTypesAFooCodegen.Shims.created);
  public final IdAliasColumn<UserTypesAFoo> id = new IdAliasColumn<UserTypesAFoo>(this, "id", UserTypesAFooCodegen.Shims.id);
  public final StringAliasColumn<UserTypesAFoo> name = new StringAliasColumn<UserTypesAFoo>(this, "name", UserTypesAFooCodegen.Shims.name);
  public final LongAliasColumn<UserTypesAFoo> version = new LongAliasColumn<UserTypesAFoo>(this, "version", UserTypesAFooCodegen.Shims.version);

  public UserTypesAFooAlias() {
    this("utaf0", null, true);
  }

  public UserTypesAFooAlias(String alias) {
    this(alias, null, true);
  }

  public UserTypesAFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(UserTypesAFoo.class, "user_types_a_foo", alias);
    this.columns.add(this.created);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<UserTypesAFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<UserTypesAFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<UserTypesAFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<UserTypesAFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 52;
  }

  public <T extends DomainObject> JoinClause<T, UserTypesAFoo> on(ForeignKeyAliasColumn<T, UserTypesAFoo> on) {
    return new JoinClause<T, UserTypesAFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, UserTypesAFoo> leftOn(ForeignKeyAliasColumn<T, UserTypesAFoo> on) {
    return new JoinClause<T, UserTypesAFoo>("LEFT OUTER JOIN", this, on);
  }

}
