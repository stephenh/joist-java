package joist.domain.migrations;

import joist.domain.migrations.columns.BigIntColumn;
import joist.domain.migrations.columns.BooleanColumn;
import joist.domain.migrations.columns.Column;
import joist.domain.migrations.columns.DateColumn;
import joist.domain.migrations.columns.ForeignKeyColumn;
import joist.domain.migrations.columns.IntColumn;
import joist.domain.migrations.columns.PrimaryKeyColumn;
import joist.domain.migrations.columns.SmallIntColumn;
import joist.domain.migrations.columns.VarcharColumn;
import joist.domain.migrations.commands.CreateTable;
import joist.jdbc.Jdbc;
import joist.util.Join;
import joist.util.StringBuilderr;
import joist.util.Wrap;

public class MigrationKeywords {

    public static void execute(String sql, Object... args) {
        Jdbc.update(Migrater.getConnection(), sql, args);
    }

    public static void createTable(String name, Column... columns) {
        MigrationKeywords.execute(new CreateTable(name, columns).toSql());
    }

    public static void createTableSubclass(String parentName, String name, Column... columns) {
        CreateTable t = new CreateTable(name, columns);
        ((PrimaryKeyColumn) t.getColumns()[0]).noSequence();
        MigrationKeywords.execute(t.toSql());
        MigrationKeywords.addForeignKeyConstraint(name, MigrationKeywords.foreignKey("id", parentName, "id"));
    }

    public static void createCodeTable(String name) {
        MigrationKeywords.createTable(name,//
            MigrationKeywords.primaryKey("id").noSequence(),
            MigrationKeywords.varchar("code").unique(),
            MigrationKeywords.varchar("name").unique(),
            MigrationKeywords.integer("version"));
    }

    public static void createJoinTable(String table1, String table2) {
        MigrationKeywords.createJoinTable(table1 + "_to_" + table2, table1, table2);
    }

    public static void createJoinTable(String joinTableName, String table1, String table2) {
        MigrationKeywords.createTable(joinTableName,//
            MigrationKeywords.primaryKey("id"),
            MigrationKeywords.foreignKey(table1).ownerIsNeither(),
            MigrationKeywords.foreignKey(table2).ownerIsNeither(),
            MigrationKeywords.integer("version"));
    }

    public static void addCode(String tableName, String code, String description) {
        int id = MigrationKeywords.getNextIdForCode(tableName);
        MigrationKeywords.execute("INSERT INTO \"{}\" (id, code, name, version) VALUES ({}, '{}', '{}', 0)", tableName, id, code, description);
    }

    private static int getNextIdForCode(String tableName) {
        int id = Jdbc.queryForInt(Migrater.getConnection(), "select next_id from code_id where table_name = '{}'", tableName);
        if (id == -1) {
            Jdbc.update(Migrater.getConnection(), "insert into code_id (table_name, next_id) values ('{}', 2)", tableName);
            id = 1;
        } else {
            Jdbc.update(Migrater.getConnection(), "update code_id set next_id = {} where table_name = '{}'", (id + 1), tableName);
        }
        return id;
    }

    public static void dropNotNull(String tableName, String columnName) {
        Jdbc.update(Migrater.getConnection(), "alter table \"{}\" alter column \"{}\" drop not null", tableName, columnName);
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

    public static SmallIntColumn smallint(String name) {
        return new SmallIntColumn(name);
    }

    public static BigIntColumn bigint(String name) {
        return new BigIntColumn(name);
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
        MigrationKeywords.execute(sql.toString());
    }

    public static void createUniqueConstraint(String table, String... columnNames) {
        String constraintName = Join.underscore(columnNames) + "_un";
        String constraintList = Join.commaSpace(Wrap.quotes(columnNames));
        MigrationKeywords.execute("ALTER TABLE \"{}\" ADD CONSTRAINT {} UNIQUE ({})", table, constraintName, constraintList);
    }

}
