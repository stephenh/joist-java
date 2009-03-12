package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.CalendarDateAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;
import joist.domain.queries.columns.StringAliasColumn;

public class UserTypesAFooAlias extends Alias<UserTypesAFoo> {

    private final List<AliasColumn<UserTypesAFoo, ?, ?>> columns = new ArrayList<AliasColumn<UserTypesAFoo, ?, ?>>();
    public final CalendarDateAliasColumn<UserTypesAFoo> created = new CalendarDateAliasColumn<UserTypesAFoo>(this, "created", UserTypesAFooCodegen.Shims.created);
    public final IdAliasColumn<UserTypesAFoo> id = new IdAliasColumn<UserTypesAFoo>(this, "id", UserTypesAFooCodegen.Shims.id);
    public final StringAliasColumn<UserTypesAFoo> name = new StringAliasColumn<UserTypesAFoo>(this, "name", UserTypesAFooCodegen.Shims.name);
    public final IntAliasColumn<UserTypesAFoo> version = new IntAliasColumn<UserTypesAFoo>(this, "version", UserTypesAFooCodegen.Shims.version);

    public UserTypesAFooAlias(String alias) {
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

    public IntAliasColumn<UserTypesAFoo> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<UserTypesAFoo> getSubClassIdColumn() {
        return null;
    }

}
