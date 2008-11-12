package features.domain.queries;

import features.domain.Primitives;
import features.domain.PrimitivesCodegen;
import java.util.ArrayList;
import java.util.List;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.BooleanAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public class PrimitivesAlias extends Alias<Primitives> {

    private final List<AliasColumn<Primitives, ?, ?>> columns = new ArrayList<AliasColumn<Primitives, ?, ?>>();
    public final IdAliasColumn<Primitives> id = new IdAliasColumn<Primitives>(this, "id", PrimitivesCodegen.Shims.id);
    public final BooleanAliasColumn<Primitives> flag = new BooleanAliasColumn<Primitives>(this, "flag", PrimitivesCodegen.Shims.flag);
    public final StringAliasColumn<Primitives> name = new StringAliasColumn<Primitives>(this, "name", PrimitivesCodegen.Shims.name);
    public final IntAliasColumn<Primitives> version = new IntAliasColumn<Primitives>(this, "version", PrimitivesCodegen.Shims.version);

    public PrimitivesAlias(String alias) {
        super(Primitives.class, Primitives.class, "primitives", alias);
        this.columns.add(this.id);
        this.columns.add(this.flag);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<Primitives, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<Primitives> getIdColumn() {
        return this.id;
    }

    public IntAliasColumn<Primitives> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<Primitives> getSubClassIdColumn() {
        return null;
    }

}
