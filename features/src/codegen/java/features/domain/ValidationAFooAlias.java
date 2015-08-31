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

public class ValidationAFooAlias extends Alias<ValidationAFoo> {

  private final List<AliasColumn<ValidationAFoo, ?, ?>> columns = new ArrayList<AliasColumn<ValidationAFoo, ?, ?>>();
  public final IdAliasColumn<ValidationAFoo> id = new IdAliasColumn<ValidationAFoo>(this, "id", ValidationAFooCodegen.Shims.id);
  public final StringAliasColumn<ValidationAFoo> name = new StringAliasColumn<ValidationAFoo>(this, "name", ValidationAFooCodegen.Shims.name);
  public final LongAliasColumn<ValidationAFoo> version = new LongAliasColumn<ValidationAFoo>(this, "version", ValidationAFooCodegen.Shims.version);

  public ValidationAFooAlias() {
    this("vaf0", null, true);
  }

  public ValidationAFooAlias(String alias) {
    this(alias, null, true);
  }

  public ValidationAFooAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(ValidationAFoo.class, "validation_a_foo", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
  }

  public List<AliasColumn<ValidationAFoo, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<ValidationAFoo> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<ValidationAFoo> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<ValidationAFoo> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 53;
  }

  public <T extends DomainObject> JoinClause<T, ValidationAFoo> on(ForeignKeyAliasColumn<T, ValidationAFoo> on) {
    return new JoinClause<T, ValidationAFoo>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, ValidationAFoo> leftOn(ForeignKeyAliasColumn<T, ValidationAFoo> on) {
    return new JoinClause<T, ValidationAFoo>("LEFT OUTER JOIN", this, on);
  }

}
