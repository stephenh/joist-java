package joist.domain.migrations.columns;

import joist.util.StringBuilderr;

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
            return super.toSql() + " DEFAULT nextval('" + this.getSequenceName() + "'), PRIMARY KEY (" + this.getQuotedName() + ")";
        } else {
            return super.toSql() + ", PRIMARY KEY (" + this.getQuotedName() + ")";
        }
    }

    public String getSequenceName() {
        if (this.sequenceName != null) {
            return this.sequenceName;
        }
        return this.getTableName() + "_" + this.getName() + "_seq";
    }

    @Override
    public void preInjectCommands(StringBuilderr sb) {
        super.preInjectCommands(sb);
        if (this.useSequence == PrimaryKeyColumn.UseSequence.Yes) {
            sb.line("CREATE SEQUENCE {};", this.getSequenceName());
        }
    }

    public PrimaryKeyColumn noSequence() {
        this.useSequence = UseSequence.No;
        return this;
    }

}
