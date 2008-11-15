package org.exigencecorp.domainobjects.codegen;

public class InformationSchemaColumn {

    public String name;
    public String tableName;
    public String dataType;
    public int maximumLength;
    public boolean nullable;
    public String defaultValue;
    public boolean primaryKey;
    public String foreignKeyConstraintName;
    public String foreignKeyTableName;
    public String foreignKeyColumnName;

    public String toString() {
        return this.tableName + "." + this.name;
    }

}
