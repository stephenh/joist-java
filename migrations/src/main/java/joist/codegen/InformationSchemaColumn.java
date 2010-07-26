package joist.codegen;

public class InformationSchemaColumn implements Comparable<InformationSchemaColumn> {

    public String name;
    public String tableName;
    public String dataType;
    public int maximumLength;
    public boolean nullable;
    public String defaultValue;
    public boolean primaryKey;
    public boolean unique;
    public String foreignKeyConstraintName;
    public String foreignKeyTableName;
    public String foreignKeyColumnName;

    public int compareTo(InformationSchemaColumn other) {
        return this.toString().compareTo(other.toString());
    }

    public String toString() {
        return this.tableName + "." + this.name;
    }

}
