package features.domain;

import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.ForeignKeyAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;

public class ManyToManyAFooToBarAlias extends Alias<ManyToManyAFooToBar> {

    private final List<AliasColumn<ManyToManyAFooToBar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyAFooToBar, ?, ?>>();
    public final IdAliasColumn<ManyToManyAFooToBar> id = new IdAliasColumn<ManyToManyAFooToBar>(this, "id", ManyToManyAFooToBarCodegen.Shims.id);
    public final IntAliasColumn<ManyToManyAFooToBar> version = new IntAliasColumn<ManyToManyAFooToBar>(this, "version", ManyToManyAFooToBarCodegen.Shims.version);
    public final ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyABar> manyToManyABar = new ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyABar>(this, "many_to_many_a_bar_id", ManyToManyAFooToBarCodegen.Shims.manyToManyABarId);
    public final ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyAFoo> manyToManyAFoo = new ForeignKeyAliasColumn<ManyToManyAFooToBar, ManyToManyAFoo>(this, "many_to_many_a_foo_id", ManyToManyAFooToBarCodegen.Shims.manyToManyAFooId);

    public ManyToManyAFooToBarAlias(String alias) {
        super(ManyToManyAFooToBar.class, "many_to_many_a_foo_to_bar", alias);
        this.columns.add(this.id);
        this.columns.add(this.version);
        this.columns.add(this.manyToManyABar);
        this.columns.add(this.manyToManyAFoo);
    }

    public List<AliasColumn<ManyToManyAFooToBar, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ManyToManyAFooToBar> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ManyToManyAFooToBar> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ManyToManyAFooToBar> getSubClassIdColumn() {
        return null;
    }

}
