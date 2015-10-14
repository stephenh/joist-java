package joist.migrations;

import static java.util.Arrays.asList;
import static joist.util.Copy.list;

import java.sql.SQLException;
import java.util.List;

import joist.codegen.Config;
import joist.jdbc.Jdbc;
import joist.jdbc.JdbcException;
import joist.migrations.columns.BigIntColumn;
import joist.migrations.columns.BooleanColumn;
import joist.migrations.columns.ByteaColumn;
import joist.migrations.columns.Column;
import joist.migrations.columns.DateColumn;
import joist.migrations.columns.DatetimeColumn;
import joist.migrations.columns.ForeignKeyColumn;
import joist.migrations.columns.ForeignKeyColumn.Owner;
import joist.migrations.columns.IntColumn;
import joist.migrations.columns.PrimaryKeyColumn;
import joist.migrations.columns.SmallIntColumn;
import joist.migrations.columns.TextColumn;
import joist.migrations.columns.VarcharColumn;
import joist.migrations.commands.CreateTable;
import joist.migrations.fill.ConstantFillInStrategy;
import joist.migrations.fill.FillInStrategy;
import joist.util.Interpolate;
import joist.util.Join;
import joist.util.Wrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationKeywords {

  private static final Logger log = LoggerFactory.getLogger(MigrationKeywords.class);
  public static Config config;

  public static boolean isMySQL() {
    return config.db.isMySQL();
  }

  public static boolean isPg() {
    return config.db.isPg();
  }

  public static void execute(String sql, Object... args) {
    Jdbc.update(Migrater.getConnection(), sql, args);
  }

  public static void createTable(String name, Column... columns) {
    for (String sql : new CreateTable(name, asList(columns)).toSql()) {
      MigrationKeywords.execute(sql);
    }
  }

  public static void dropTable(String name) {
    MigrationKeywords.execute("DROP TABLE " + Wrap.quotes(name));
  }

  public static void createSubclassTable(String parentName, String name, Column... _columns) {
    List<Column> columns = list(_columns);
    columns.add(new PrimaryKeyColumn("id").noSequence());
    CreateTable t = new CreateTable(name, columns);
    for (String sql : t.toSql()) {
      MigrationKeywords.execute(sql);
    }
    MigrationKeywords.addForeignKeyConstraint(name, MigrationKeywords.foreignKey("id", parentName, "id").ownerIsThem());
  }

  public static void createCodeTable(String name, String... codePlusDescriptions) {
    MigrationKeywords.createTable(name,//
      MigrationKeywords.primaryKey("id").noSequence(),
      MigrationKeywords.varchar("code").unique(),
      MigrationKeywords.varchar("name").unique(),
      MigrationKeywords.integer("version"));
    MigrationKeywords.addCodes(name, codePlusDescriptions);
  }

  public static void createEntityTable(String name, Column... _columns) {
    List<Column> columns = list(_columns);
    columns.add(0, new PrimaryKeyColumn("id"));
    columns.add(new IntColumn("version"));
    for (String sql : new CreateTable(name, columns).toSql()) {
      MigrationKeywords.execute(sql);
    }
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
    MigrationKeywords.execute(Interpolate.string("ALTER TABLE {} ALTER COLUMN {} TYPE {}", //
      Wrap.quotes(tableName),
      Wrap.quotes(columName),
      type));
  }

  public static void addCode(String tableName, String code, String description) {
    int id = MigrationKeywords.getNextIdForCode(tableName);
    MigrationKeywords.execute(Interpolate.string(
      "INSERT INTO {} (id, code, name, version) VALUES ({}, '{}', '{}', 0)",
      tableName,
      id,
      code,
      description));
  }

  public static void addCodes(String tableName, String... codePlusDescriptions) {
    for (String codePlusDescription : codePlusDescriptions) {
      int i = codePlusDescription.indexOf(' ');
      if (i == -1) {
        throw new IllegalStateException("Format for codes is 'CODE_VALUE_1 Code Value 1'");
      }
      MigrationKeywords.addCode(tableName, codePlusDescription.substring(0, i), codePlusDescription.substring(i + 1));
    }
  }

  public static void removeCode(String tableName, String code) {
    MigrationKeywords.execute(Interpolate.string("DELETE FROM {} WHERE code = '{}'", tableName, code));
  }

  public static void addUniqueConstraint(String tableName, String... columnNames) {
    String constraintName = tableName + "_" + Join.underscore(columnNames) + "_un";
    MigrationKeywords.execute(Interpolate.string(//
      "ALTER TABLE {} ADD CONSTRAINT {} UNIQUE ({});",
      Wrap.quotes(tableName),
      Wrap.quotes(constraintName),
      Join.commaSpace(Wrap.quotes(columnNames))));
  }

  private static int getNextIdForCode(String tableName) {
    int id = Jdbc.queryForInt(Migrater.getConnection(), "SELECT MAX(id) + 1 FROM " + Wrap.quotes(tableName));
    if (id <= 0) {
      id = 1;
    }
    return id;
  }

  public static void dropNotNull(String tableName, String columnName) {
    if (isMySQL()) {
      // Have to pull the column type/default value from MySQL
      String[] s = getColumnTypeAndDefaultValue(tableName, columnName);
      String columnType = s[0];
      String columnDefault = s[1];
      execute(Interpolate.string("ALTER TABLE {} MODIFY {} {} {}",//
        Wrap.quotes(tableName),
        Wrap.quotes(columnName),
        columnType,
        columnDefault == null ? "" : "DEFAULT " + columnDefault));
    } else {
      execute(Interpolate.string("ALTER TABLE {} ALTER COLUMN {} DROP NOT NULL",//
        Wrap.quotes(tableName),
        Wrap.quotes(columnName)));
    }
  }

  public static void addNotNull(String tableName, String columnName) {
    if (isMySQL()) {
      // Have to pull the column type/default value from MySQL
      String[] s = getColumnTypeAndDefaultValue(tableName, columnName);
      String columnType = s[0];
      String columnDefault = s[1];
      execute(Interpolate.string("ALTER TABLE {} MODIFY {} {} NOT NULL {}", //
        Wrap.quotes(tableName),
        Wrap.quotes(columnName),
        columnType,
        columnDefault == null ? "" : "DEFAULT " + columnDefault));
    } else {
      execute(Interpolate.string("ALTER TABLE {} ALTER COLUMN {} SET NOT NULL",//
        Wrap.quotes(tableName),
        Wrap.quotes(columnName)));
    }
  }

  public static void renameTable(String oldTableName, String newTableName) {
    if (isMySQL()) {
      execute(Interpolate.string("RENAME TABLE {} TO {};", Wrap.quotes(oldTableName), Wrap.quotes(newTableName)));
    } else {
      execute(Interpolate.string("ALTER TABLE {} RENAME TO {};", Wrap.quotes(oldTableName), Wrap.quotes(newTableName)));
    }
    // clean up the old history triggers, as they'll get recreated after the migrations are applied
    dropHistoryTriggers(oldTableName);
  }

  public static void renameColumn(String tableName, String oldColumnName, String newColumnName) {
    if (isMySQL()) {
      // Have to pull the column type/default value from MySQL
      String[] s = getColumnTypeAndDefaultValue(tableName, oldColumnName);
      String columnType = s[0];
      String columnDefault = s[1];
      boolean notNullable = "NO".equals(s[2]);
      execute(Interpolate.string("ALTER TABLE {} CHANGE {} {} {} {} {}", //
        Wrap.quotes(tableName),
        Wrap.quotes(oldColumnName),
        Wrap.quotes(newColumnName),
        columnType,
        notNullable ? "NOT NULL" : "",
        columnDefault == null ? "" : "DEFAULT " + columnDefault));
    } else {
      execute(Interpolate.string("ALTER TABLE {} RENAME COLUMN {} TO {}",//
        Wrap.quotes(tableName),
        Wrap.quotes(oldColumnName),
        Wrap.quotes(newColumnName)));
    }
    // if another migration does an insert on the table, the old history trigger will fail
    dropHistoryTriggers(tableName);
  }

  /**
   * Removes the history triggers for {@code tableName}, if they exist.
   *
   * Currently Joist does not delete/re-create the history triggers until after
   * all migrations have applied.
   *
   * This means if one migration changes the schema in a way that breaks the
   * history triggers, and then another migration tries to INSERT/UPDATE/DELETE
   * a table that has broken history triggers, the 2nd migration will fail.
   *
   * We could conceivably fix the history trigger directly after doing an add
   * column, remove column, etc., but it would take some work to do, and it
   * seems like the data manipulation done in the 2nd migration is probably
   * not super critical log.
   */
  public static void dropHistoryTriggers(String tableName) {
    if (isMySQL()) {
      execute("DROP TRIGGER IF EXISTS " + tableName + "_history_delete");
      execute("DROP TRIGGER IF EXISTS " + tableName + "_history_insert");
      execute("DROP TRIGGER IF EXISTS " + tableName + "_history_update");
    } else {
      execute("DROP TRIGGER IF EXISTS " + tableName + "_history_delete ON " + tableName);
      execute("DROP TRIGGER IF EXISTS " + tableName + "_history_insert ON " + tableName);
      execute("DROP TRIGGER IF EXISTS " + tableName + "_history_update ON " + tableName);
    }
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

  public static DatetimeColumn datetime(String name) {
    return new DatetimeColumn(name);
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

  public static void addColumns(String table, Column... columns) {
    for (Column column : columns) {
      MigrationKeywords.addColumn(table, column, null);
    }
  }

  public static void addColumn(String table, Column column, FillInStrategy fill) {
    column.setTableName(table);
    if (!column.isNullable() && !column.hasDefault() && fill == null) {
      log.warn(Interpolate.string("Adding non-null/no-default column {}.{} will fail if rows already exist", table, column.getName()));
    }
    // column
    MigrationKeywords.execute("ALTER TABLE " + Wrap.quotes(table) + " ADD COLUMN " + column.toSql());
    // fill
    if (fill != null) {
      try {
        fill.fillIn(Migrater.getConnection(), table, column.getName());
      } catch (SQLException se) {
        throw new JdbcException(se);
      }
    }
    // post
    List<String> post = column.postInjectCommands();
    for (String sql : post) {
      MigrationKeywords.execute(sql);
    }
  }

  public static void dropColumn(String table, String column) {
    if (config.db.isMySQL()) {
      // mysql will fail the drop if the column has a foreign key constraint
      String fkName = findConstraintName(table, column, "FOREIGN KEY");
      if (fkName != null) {
        dropConstraint(table, fkName);
      }
    }
    execute(Interpolate.string("ALTER TABLE {} DROP COLUMN {};", Wrap.quotes(table), Wrap.quotes(column)));
    // if another migration does an insert on the table, the old history trigger will fail
    dropHistoryTriggers(table);
  }

  public static FillInStrategy constantFillIn(String fragment) {
    return new ConstantFillInStrategy(fragment);
  }

  public static void addForeignKeyConstraint(String table, ForeignKeyColumn column) {
    column.setTableName(table);
    for (String sql : column.constraintCommands()) {
      MigrationKeywords.execute(sql);
    }
  }

  public static void changeOwner(String table, String column, Owner owner) {
    String[] referenced = findReferencedTableAndColumn(table, column);
    dropForeignKeyConstraint(table, column);
    addForeignKeyConstraint(table, foreignKey(column, referenced[0], referenced[1]).ownerIs(owner));
  }

  public static void dropForeignKeyConstraint(String table, String column) {
    dropConstraint(table, findConstraintName(table, column, "FOREIGN KEY"), "FOREIGN KEY");
  }

  public static void dropUniqueConstraint(String table, String column) {
    dropConstraint(table, findConstraintName(table, column, "UNIQUE"), "UNIQUE");
  }

  public static void dropConstraint(String table, String constraint) {
    if (config.db.isMySQL()) {
      dropConstraint(table, constraint, findConstraintType(table, constraint));
    } else {
      dropConstraint(table, constraint, null);
    }
  }

  public static void dropIndex(String index) {
    MigrationKeywords.execute("DROP INDEX " + Wrap.quotes(index));
  }

  public static FillInStrategy fillIn(String constant) {
    return new ConstantFillInStrategy("'" + constant + "'");
  }

  public static FillInStrategy fillIn(int constant) {
    return new ConstantFillInStrategy(constant);
  }

  public static FillInStrategy fillIn(long constant) {
    return new ConstantFillInStrategy(Long.toString(constant));
  }

  private static String[] getColumnTypeAndDefaultValue(String tableName, String columnName) {
    // The 'column_type' is MySQL-only but gets us varchar(100) instead of just varchar
    Object[] result = Jdbc
      .queryForRow(
        Migrater.getConnection(),
        "SELECT column_type, column_default, is_nullable FROM information_schema.columns WHERE table_schema = ? AND table_name = ? AND column_name = ?",
        getSchemaName(),
        tableName,
        columnName);
    if (result[0] == null) {
      throw new RuntimeException("Could not find metadata for " + tableName + "." + columnName);
    }
    String dataType = (String) result[0];
    String defaultValue = (String) result[1];
    String nullable = (String) result[2];
    if (dataType.startsWith("varchar") && defaultValue != null) {
      defaultValue = "'" + defaultValue + "'";
    }
    return new String[] { dataType, defaultValue, nullable };
  }

  private static void dropConstraint(String table, String constraint, String constraintType) {
    if (config.db.isMySQL()) {
      if ("FOREIGN KEY".equals(constraintType)) {
        execute(Interpolate.string("ALTER TABLE {} DROP FOREIGN KEY {}", Wrap.quotes(table), Wrap.quotes(constraint)));
      } else if ("UNIQUE".equals(constraintType)) {
        execute(Interpolate.string("ALTER TABLE {} DROP INDEX {}", Wrap.quotes(table), Wrap.quotes(constraint)));
      } else {
        throw new RuntimeException("Unhandled constraint type " + constraintType);
      }
    } else if (config.db.isPg()) {
      execute(Interpolate.string("ALTER TABLE {} DROP CONSTRAINT {};", Wrap.quotes(table), Wrap.quotes(constraint)));
    } else {
      throw new RuntimeException("Unsupported db " + config.db);
    }
  }

  private static String findConstraintName(String table, String column, String type) {
    String sql = "SELECT kcu.constraint_name"
      + " FROM information_schema.key_column_usage kcu,"
      + "   information_schema.table_constraints tc"
      + " WHERE kcu.constraint_name = tc.constraint_name"
      + " AND kcu.table_schema = ?"
      + " AND kcu.table_name = ?"
      + " AND kcu.column_name = ?"
      + " AND tc.constraint_schema = ?"
      + " AND tc.constraint_type = ?";
    return (String) Jdbc.queryForRow(Migrater.getConnection(), sql, getSchemaName(), table, column, getSchemaName(), type)[0];
  }

  private static String findConstraintType(String table, String constraintName) {
    String sql = "SELECT tc.constraint_type"
      + " FROM information_schema.table_constraints tc"
      + " WHERE tc.constraint_schema = ?"
      + " AND tc.table_name = ?"
      + " AND tc.constraint_name = ?";
    return (String) Jdbc.queryForRow(Migrater.getConnection(), sql, getSchemaName(), table, constraintName)[0];
  }

  private static String[] findReferencedTableAndColumn(String table, String column) {
    final String sql;
    if (isMySQL()) {
      sql = "SELECT kcu.referenced_table_name, kcu.referenced_column_name"
        + " FROM information_schema.key_column_usage kcu,"
        + "   information_schema.table_constraints tc"
        + " WHERE kcu.constraint_name = tc.constraint_name"
        + " AND kcu.table_schema = ?"
        + " AND kcu.table_name = ?"
        + " AND kcu.column_name = ?"
        + " AND tc.constraint_schema = ?"
        + " AND tc.constraint_type = 'FOREIGN KEY'";
    } else {
      sql = "SELECT ccu.table_name, ccu.column_name"
        + " FROM information_schema.key_column_usage kcu,"
        + "   information_schema.constraint_column_usage ccu"
        + " WHERE kcu.constraint_name = ccu.constraint_name"
        + " AND kcu.table_schema = ?"
        + " AND kcu.table_name = ?"
        + " AND kcu.column_name = ?"
        + " AND ccu.constraint_schema = ?";
    }
    Object[] row = Jdbc.queryForRow(Migrater.getConnection(), sql, getSchemaName(), table, column, getSchemaName());
    return new String[] { (String) row[0], (String) row[1] };
  }

  private static String getSchemaName() {
    return config.dbAppUserSettings.schemaName;
  }
}
