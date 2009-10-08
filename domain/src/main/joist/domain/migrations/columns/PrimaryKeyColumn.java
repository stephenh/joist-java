package joist.domain.migrations.columns;

import java.util.List;

public class PrimaryKeyColumn extends AbstractColumn<PrimaryKeyColumn> {

    public enum UseSequence {
        Yes, No
    };

    private UseSequence useSequence = PrimaryKeyColumn.UseSequence.Yes;
    private String sequenceName = null;

    public PrimaryKeyColumn(String name) {
        super(name, "int");
    }

    public String toSql() {
        if (this.useSequence == PrimaryKeyColumn.UseSequence.Yes) {
            return super.toSql() + " AUTO_INCREMENT PRIMARY KEY";
        } else {
            return super.toSql() + " PRIMARY KEY";
        }
    }

    public String getSequenceName() {
        if (this.sequenceName != null) {
            return this.sequenceName;
        }
        return this.getTableName() + "_" + this.getName() + "_seq";
    }

    @Override
    public List<String> preInjectCommands() {
        List<String> sqls = super.preInjectCommands();
        if (this.useSequence == PrimaryKeyColumn.UseSequence.Yes) {
            // sb.line("CREATE SEQUENCE {};", this.getSequenceName());
        }
        return sqls;
    }

    public PrimaryKeyColumn noSequence() {
        this.useSequence = UseSequence.No;
        return this;
    }

}
