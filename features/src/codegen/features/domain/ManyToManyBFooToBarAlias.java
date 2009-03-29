package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.ForeignKeyAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.queries.columns.IntAliasColumn;

public class ManyToManyBFooToBarAlias extends Alias<ManyToManyBFooToBar> {

    private final List<AliasColumn<ManyToManyBFooToBar, ?, ?>> columns = new ArrayList<AliasColumn<ManyToManyBFooToBar, ?, ?>>();
    public final IdAliasColumn<ManyToManyBFooToBar> id = new IdAliasColumn<ManyToManyBFooToBar>(this, "id", ManyToManyBFooToBarCodegen.Shims.id);
    public final IntAliasColumn<ManyToManyBFooToBar> version = new IntAliasColumn<ManyToManyBFooToBar>(this, "version", ManyToManyBFooToBarCodegen.Shims.version);
    public final ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBFoo> blue = new ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBFoo>(this, "blue_id", ManyToManyBFooToBarCodegen.Shims.blueId);
    public final ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBBar> green = new ForeignKeyAliasColumn<ManyToManyBFooToBar, ManyToManyBBar>(this, "green_id", ManyToManyBFooToBarCodegen.Shims.greenId);

    public ManyToManyBFooToBarAlias(String alias) {
        super(ManyToManyBFooToBar.class, "many_to_many_b_foo_to_bar", alias);
        this.columns.add(this.id);
        this.columns.add(this.version);
        this.columns.add(this.blue);
        this.columns.add(this.green);
    }

    public List<AliasColumn<ManyToManyBFooToBar, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<ManyToManyBFooToBar> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<ManyToManyBFooToBar> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<ManyToManyBFooToBar> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 15;
    }

}
