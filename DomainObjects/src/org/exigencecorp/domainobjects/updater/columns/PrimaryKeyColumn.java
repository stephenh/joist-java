package org.exigencecorp.domainobjects.updater.columns;

import org.exigencecorp.util.StringBuilderr;

public class PrimaryKeyColumn extends AbstractColumn {

    public enum UseSequence {
        Yes, No
    };

    private UseSequence useSequence;
    private String primaryKeySet = null;
    private String sequenceName = null;

    public PrimaryKeyColumn(String name) {
        this(name, PrimaryKeyColumn.UseSequence.Yes);
    }

    public PrimaryKeyColumn(String name, UseSequence useSequence, String sequenceName, String primaryKeySet) {
        this(name, useSequence);
        this.primaryKeySet = primaryKeySet;
        this.sequenceName = sequenceName;
    }

    public PrimaryKeyColumn(String name, UseSequence useSequence) {
        super(name, Nullable.No);
        this.useSequence = useSequence;
    }

    public String toSql() {
        if (this.primaryKeySet != null) {
            return "PRIMARY KEY (" + this.getName() + ", " + this.primaryKeySet + "),";
        }
        if (this.useSequence == PrimaryKeyColumn.UseSequence.Yes) {
            return this.getName() + " int DEFAULT nextval('" + this.getSequenceName() + "') NOT NULL, PRIMARY KEY (" + this.getName() + "),";
        }
        return this.getName() + " int NOT NULL, PRIMARY KEY (" + this.getName() + "),";
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

    public UseSequence getUseSequence() {
        return this.useSequence;
    }

    public void setUseSequence(UseSequence useSequence) {
        this.useSequence = useSequence;
    }

}
