package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class InheritanceABaseAlias extends Alias<InheritanceABase> {

    private final List<AliasColumn<InheritanceABase, ?, ?>> columns = new ArrayList<AliasColumn<InheritanceABase, ?, ?>>();
    public final IdAliasColumn<InheritanceABase> id = new IdAliasColumn<InheritanceABase>(this, "id", InheritanceABaseCodegen.Shims.id);
    public final StringAliasColumn<InheritanceABase> name = new StringAliasColumn<InheritanceABase>(this, "name", InheritanceABaseCodegen.Shims.name);
    public final LongAliasColumn<InheritanceABase> version = new LongAliasColumn<InheritanceABase>(this, "version", InheritanceABaseCodegen.Shims.version);

    public InheritanceABaseAlias(String alias) {
        this(alias, null, true);
    }

    public InheritanceABaseAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
        super(InheritanceABase.class, "inheritance_a_base", alias);
        InheritanceABaseAlias inheritanceABase = this;
        if (addSubClasses) {
          InheritanceASubOneAlias inheritanceASubOne = new InheritanceASubOneAlias(alias + "_0", inheritanceABase, false);
          this.addSubClassAlias(inheritanceASubOne);
          InheritanceASubTwoAlias inheritanceASubTwo = new InheritanceASubTwoAlias(alias + "_1", inheritanceABase, false);
          this.addSubClassAlias(inheritanceASubTwo);
        }
        this.columns.add(this.id);
        this.columns.add(this.name);
        this.columns.add(this.version);
    }

    public List<AliasColumn<InheritanceABase, ?, ?>> getColumns() {
        return this.columns;
    }

    public IdAliasColumn<InheritanceABase> getIdColumn() {
        return this.id;
    }

    public LongAliasColumn<InheritanceABase> getVersionColumn() {
        return this.version;
    }

    public IdAliasColumn<InheritanceABase> getSubClassIdColumn() {
        return null;
    }

    public int getOrder() {
        return 3;
    }

}
