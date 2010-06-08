package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.MoneyAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;
import joist.domain.orm.queries.columns.TimePointAliasColumn;

public class PrimitivesCAlias extends Alias<PrimitivesC> {

    private final List<AliasColumn<PrimitivesC, ?, ?>> columns = new ArrayList<AliasColumn<PrimitivesC, ?, ?>>();
    public final MoneyAliasColumn<PrimitivesC> dollarAmount = new MoneyAliasColumn<PrimitivesC>(this, "dollar_amount", PrimitivesCCodegen.Shims.dollarAmount);
    public final IdAliasColumn<PrimitivesC> id = new IdAliasColumn<PrimitivesC>(this, "id", PrimitivesCCodegen.Shims.id);
    public final StringAliasColumn<PrimitivesC> name = new StringAliasColumn<PrimitivesC>(this, "name", PrimitivesCCodegen.Shims.name);
    public final TimePointAliasColumn<PrimitivesC> timestamp = new TimePointAliasColumn<PrimitivesC>(this, "timestamp", PrimitivesCCodegen.Shims.timestamp);
    public final IntAliasColumn<PrimitivesC> version = new IntAliasColumn<PrimitivesC>(this, "version", PrimitivesCCodegen.Shims.version);

    public PrimitivesCAlias(String alias) {
        super(PrimitivesC.class, "primitives_c", alias);
        this.columns.add(this.dollarAmount);
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.timestamp);
        this.columns.add(this.version);
    }

    public List<AliasColumn<PrimitivesC, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<PrimitivesC> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<PrimitivesC> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<PrimitivesC> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 35;
    }

}
