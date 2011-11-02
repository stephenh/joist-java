package features.domain;

import java.util.ArrayList;
import java.util.List;

import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class OneToOneBBarAlias extends Alias<OneToOneBBar> {

  private final List<AliasColumn<OneToOneBBar, ?, ?>> columns = new ArrayList<AliasColumn<OneToOneBBar, ?, ?>>();
  public final IdAliasColumn<OneToOneBBar> id = new IdAliasColumn<OneToOneBBar>(this, "id", OneToOneBBarCodegen.Shims.id);
  public final StringAliasColumn<OneToOneBBar> name = new StringAliasColumn<OneToOneBBar>(this, "name", OneToOneBBarCodegen.Shims.name);
  public final LongAliasColumn<OneToOneBBar> version = new LongAliasColumn<OneToOneBBar>(this, "version", OneToOneBBarCodegen.Shims.version);
  public final ForeignKeyAliasColumn<OneToOneBBar, OneToOneBFoo> oneToOneBFoo = new ForeignKeyAliasColumn<OneToOneBBar, OneToOneBFoo>(this, "one_to_one_b_foo_id", OneToOneBBarCodegen.Shims.oneToOneBFooId);

  public OneToOneBBarAlias(String alias) {
    this(alias, null, true);
  }

  public OneToOneBBarAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(OneToOneBBar.class, "one_to_one_b_bar", alias);
    this.columns.add(this.id);
    this.columns.add(this.name);
    this.columns.add(this.version);
    this.columns.add(this.oneToOneBFoo);
  }

  public List<AliasColumn<OneToOneBBar, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<OneToOneBBar> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<OneToOneBBar> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<OneToOneBBar> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 24;
  }

}
