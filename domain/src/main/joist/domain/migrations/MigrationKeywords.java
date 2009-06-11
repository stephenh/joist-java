package joist.domain.migrations;

import java.sql.SQLException;

import joist.domain.migrations.columns.BigIntColumn;
import joist.domain.migrations.columns.BooleanColumn;
import joist.domain.migrations.columns.ByteaColumn;
import joist.domain.migrations.columns.Column;
import joist.domain.migrations.columns.DateColumn;
import joist.domain.migrations.columns.ForeignKeyColumn;
import joist.domain.migrations.columns.IntColumn;
import joist.domain.migrations.columns.PrimaryKeyColumn;
import joist.domain.migrations.columns.SmallIntColumn;
import joist.domain.migrations.columns.TextColumn;
import joist.domain.migrations.columns.VarcharColumn;
import joist.domain.migrations.commands.CreateTable;
import joist.domain.migrations.fill.ConstantFillInStrategy;
import joist.domain.migrations.fill.FillInStrategy;
import joist.jdbc.Jdbc;
import joist.jdbc.JdbcException;
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

    public static void alterColumnType(String tableName, String columName, String type) {
        MigrationKeywords.execute("ALTER TABLE \"{}\" ALTER COLUMN \"{}\" TYPE {}", tableName, columName, type);
    }

    public static void addCode(String tableName, String code, String description) {
        int id = MigrationKeywords.getNextIdForCode(tableName);
        MigrationKeywords.execute("INSERT INTO \"{}\" (id, code, name, version) VALUES ({}, '{}', '{}', 0)", tableName, id, code, description);
    }

    public static void addUnique(String tableName, String... columnNames) {
        String constraintName = tableName + "_" + Join.underscore(columnNames) + "_key";
        MigrationKeywords.execute(//
            "ALTER TABLE \"{}\" ADD CONSTRAINT \"{}\" UNIQUE ({});",
            tableName,
            constraintName,
            Join.commaSpace(Wrap.quotes(columnNames)));
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

    public static ByteaColumn bytea(String name) {
        return new ByteaColumn(name);
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

    public static TextColumn text(String name) {
        return new TextColumn(name);
    }

    public static BooleanColumn bool(String name) {
        return new BooleanColumn(name);
    }

    public static void addColumn(String table, Column column) {
        MigrationKeywords.addColumn(table, column, null);
    }

    public static void addColumn(String table, Column column, FillInStrategy fill) {
        column.setTableName(table);
        // pre
        StringBuilderr pre = new StringBuilderr();
        column.preInjectCommands(pre);
        if (pre.toString().length() > 0) {
            MigrationKeywords.execute(pre.toString());
        }
        // column
        MigrationKeywords.execute("ALTER TABLE \"{}\" ADD COLUMN {}", table, column.toSql());
        // fill
        if (fill != null) {
            try {
                fill.fillIn(Migrater.getConnection(), table, column.getName());
            } catch (SQLException se) {
                throw new JdbcException(se);
            }
        }
        // post
        StringBuilderr post = new StringBuilderr();
        column.postInjectCommands(post);
        if (post.toString().length() > 0) {
            MigrationKeywords.execute(post.toString());
        }
    }

    public static void dropColumn(String table, String column) {
        MigrationKeywords.execute("ALTER TABLE \"{}\" DROP COLUMN \"{}\";", table, column);
    }

    public static FillInStrategy constantFillIn(String fragment) {
        return new ConstantFillInStrategy(fragment);
    }

    public static void addForeignKeyConstraint(String table, ForeignKeyColumn column) {
        StringBuilderr sql = new StringBuilderr();
        column.setTableName(table);
        column.postInjectCommands(sql);
        MigrationKeywords.execute(sql.toString());
    }

    public static void dropForeignKeyConstraint(String table, String column) {
        String sql = "SELECT kcu.constraint_name FROM information_schema.key_column_usage kcu, information_schema.table_constraints tc"
            + " WHERE kcu.constraint_name = tc.constraint_name"
            + " AND kcu.table_name = '{}'"
            + " AND kcu.column_name = '{}'"
            + " AND tc.constraint_type = '{}'";
        String constraint = (String) Jdbc.queryForRow(Migrater.getConnection(), sql, table, column, "FOREIGN KEY")[0];
        MigrationKeywords.dropConstraint(table, constraint);
    }

    public static void dropConstraint(String table, String constraint) {
        MigrationKeywords.execute("ALTER TABLE \"{}\" DROP CONSTRAINT \"{}\";", table, constraint);
    }

    public static void dropIndex(String index) {
        MigrationKeywords.execute("DROP INDEX \"{}\";", index);
    }

    public static void createUniqueConstraint(String table, String... columnNames) {
        String constraintName = Join.underscore(columnNames) + "_un";
        String constraintList = Join.commaSpace(Wrap.quotes(columnNames));
        MigrationKeywords.execute("ALTER TABLE \"{}\" ADD CONSTRAINT {} UNIQUE ({})", table, constraintName, constraintList);
    }

}
