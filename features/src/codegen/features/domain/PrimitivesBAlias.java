package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.BooleanAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.ShortAliasColumn;

public class PrimitivesBAlias extends Alias<PrimitivesB> {

    private final List<AliasColumn<PrimitivesB, ?, ?>> columns = new ArrayList<AliasColumn<PrimitivesB, ?, ?>>();
    public final LongAliasColumn<PrimitivesB> big1 = new LongAliasColumn<PrimitivesB>(this, "big1", PrimitivesBCodegen.Shims.big1);
    public final LongAliasColumn<PrimitivesB> big2 = new LongAliasColumn<PrimitivesB>(this, "big2", PrimitivesBCodegen.Shims.big2);
    public final BooleanAliasColumn<PrimitivesB> bool1 = new BooleanAliasColumn<PrimitivesB>(this, "bool1", PrimitivesBCodegen.Shims.bool1);
    public final BooleanAliasColumn<PrimitivesB> bool2 = new BooleanAliasColumn<PrimitivesB>(this, "bool2", PrimitivesBCodegen.Shims.bool2);
    public final IdAliasColumn<PrimitivesB> id = new IdAliasColumn<PrimitivesB>(this, "id", PrimitivesBCodegen.Shims.id);
    public final IntAliasColumn<PrimitivesB> int1 = new IntAliasColumn<PrimitivesB>(this, "int1", PrimitivesBCodegen.Shims.int1);
    public final IntAliasColumn<PrimitivesB> int2 = new IntAliasColumn<PrimitivesB>(this, "int2", PrimitivesBCodegen.Shims.int2);
    public final ShortAliasColumn<PrimitivesB> small1 = new ShortAliasColumn<PrimitivesB>(this, "small1", PrimitivesBCodegen.Shims.small1);
    public final ShortAliasColumn<PrimitivesB> small2 = new ShortAliasColumn<PrimitivesB>(this, "small2", PrimitivesBCodegen.Shims.small2);
    public final IntAliasColumn<PrimitivesB> version = new IntAliasColumn<PrimitivesB>(this, "version", PrimitivesBCodegen.Shims.version);

    public PrimitivesBAlias(String alias) {
        super(PrimitivesB.class, "primitives_b", alias);
        this.columns.add(this.big1);
        this.columns.add(this.big2);
        this.columns.add(this.bool1);
        this.columns.add(this.bool2);
        this.columns.add(this.id);
        this.columns.add(this.int1);
        this.columns.add(this.int2);
        this.columns.add(this.small1);
        this.columns.add(this.small2);
        this.columns.add(this.version);
    }

    public List<AliasColumn<PrimitivesB, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<PrimitivesB> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<PrimitivesB> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<PrimitivesB> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 28;
    }

}
