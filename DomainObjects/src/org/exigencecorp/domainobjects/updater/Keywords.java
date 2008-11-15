package org.exigencecorp.domainobjects.updater;

import org.exigencecorp.domainobjects.updater.columns.BooleanColumn;
import org.exigencecorp.domainobjects.updater.columns.Column;
import org.exigencecorp.domainobjects.updater.columns.DateColumn;
import org.exigencecorp.domainobjects.updater.columns.ForeignKeyColumn;
import org.exigencecorp.domainobjects.updater.columns.IntColumn;
import org.exigencecorp.domainobjects.updater.columns.PrimaryKeyColumn;
import org.exigencecorp.domainobjects.updater.columns.VarcharColumn;
import org.exigencecorp.domainobjects.updater.commands.CreateTable;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class Keywords {

    public static void execute(String sql, Object... args) {
        Jdbc.executeUpdate(Updater.getConnection(), sql, args);
    }

    public static void createTable(String name, Column... columns) {
        Keywords.execute(new CreateTable(name, columns).toSql());
    }

    public static void createTableSubclass(String parentName, String name, Column... columns) {
        CreateTable t = new CreateTable(name, columns);
        ((PrimaryKeyColumn) t.getColumns()[0]).noSequence();
        Keywords.execute(t.toSql());
        Keywords.addForeignKeyConstraint(name, Keywords.foreignKey("id", parentName, "id"));
    }

    public static void createCodeTable(String name) {
        Keywords.createTable(name,//
            Keywords.primaryKey("id").noSequence(),
            Keywords.varchar("code").unique(),
            Keywords.varchar("name").unique(),
            Keywords.integer("version"));
    }

    public static void createJoinTable(String table1, String table2) {
        Keywords.createJoinTable(table1 + "_to_" + table2, table1, table2);
    }

    public static void createJoinTable(String joinTableName, String table1, String table2) {
        Keywords.createTable(joinTableName,//
            Keywords.primaryKey("id"),
            Keywords.foreignKey(table1).ownerIsNeither(),
            Keywords.foreignKey(table2).ownerIsNeither(),
            Keywords.integer("version"));
    }

    public static void addCode(String tableName, String code, String description) {
        int id = Keywords.getNextIdForCode(tableName);
        Keywords.execute("INSERT INTO \"{}\" (id, code, name, version) VALUES ({}, '{}', '{}', 0)", tableName, id, code, description);
    }

    private static int getNextIdForCode(String tableName) {
        int id = Jdbc.queryForInt(Updater.getConnection(), "select next_id from code_id where table_name = '{}'", tableName);
        if (id == -1) {
            Jdbc.executeUpdate(Updater.getConnection(), "insert into code_id (table_name, next_id) values ('{}', 2)", tableName);
            id = 1;
        } else {
            Jdbc.executeUpdate(Updater.getConnection(), "update code_id set next_id = {} where table_name = '{}'", (id + 1), tableName);
        }
        return id;
    }

    public static PrimaryKeyColumn primaryKey(String name) {
        return new PrimaryKeyColumn(name);
    }

    public static ForeignKeyColumn foreignKey(String tableName) {
        return new ForeignKeyColumn(tableName);
    }

    public static ForeignKeyColumn foreignKey(String columnName, String otherTable) {
        return new ForeignKeyColumn(columnName, otherTable, "id");
    }

    public static ForeignKeyColumn foreignKey(String columnName, String otherTable, String otherTableColumn) {
        return new ForeignKeyColumn(columnName, otherTable, otherTableColumn);
    }

    public static DateColumn date(String name) {
        return new DateColumn(name);
    }

    public static IntColumn integer(String name) {
        return new IntColumn(name);
    }

    public static VarcharColumn varchar(String name) {
        return new VarcharColumn(name);
    }

    public static BooleanColumn bool(String name) {
        return new BooleanColumn(name);
    }

    public static void addForeignKeyConstraint(String table, ForeignKeyColumn column) {
        StringBuilderr sql = new StringBuilderr();
        column.setTableName(table);
        column.postInjectCommands(sql);
        Keywords.execute(sql.toString());
    }

}
