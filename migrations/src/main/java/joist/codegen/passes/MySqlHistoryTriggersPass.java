package joist.codegen.passes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import joist.codegen.InformationSchemaColumn;
import joist.codegen.Schema;
import joist.codegen.dtos.Entity;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import lombok.extern.slf4j.Slf4j;

/**
 * Automatically adds INSERT/UPDATE/DELETE triggers to all entity tables in the schema that log changes
 * to an {@code history_entry} table.
 * 
 * @author larry
 */
@Slf4j
public class MySqlHistoryTriggersPass implements Pass<Schema> {

  private final Set<Pattern> skippedTables = new HashSet<Pattern>();
  private final Set<Pattern> skippedColumns = new HashSet<Pattern>();
  private final String historyTableName;
  private DataSource ds;

  public MySqlHistoryTriggersPass() {
    this("history_entry");
  }

  public MySqlHistoryTriggersPass(String historyTableName) {
    this.historyTableName = historyTableName;
    this.skipTable(historyTableName);
  }

  public void pass(Schema schema) {
    log.info("Updating history triggers");
    this.ds = schema.getConfig().dbAppSaSettings.getDataSource();
    for (Entity entity : schema.getEntities().values()) {
      // always try to drop the trigger
      this.dropExistingTriggers(entity.getTableName());
      if (!this.shouldSkipTable(entity.getTableName())) {
        if (entity.isRoot()) {
          this.createInsertTrigger(entity.getTableName());
        }
        this.createUpdateTrigger(schema, entity.getTableName(), entity.getRootEntity().getTableName());
        this.createDeleteTrigger(schema, entity.getTableName(), entity.getRootEntity().getTableName());
      }
    }
  }

  /** @param regex regular expression of tables to ignore, {@code some_table} */
  public void skipTable(String regex) {
    this.skippedTables.add(Pattern.compile(regex));
  }

  /** @param regex regular expression of columns to ignore, {@code some_table.the_column} */
  public void skipColumn(String regex) {
    this.skippedColumns.add(Pattern.compile(regex));
  }

  private boolean shouldSkipTable(String tableName) {
    for (Pattern pattern : this.skippedTables) {
      if (pattern.matcher(tableName).matches()) {
        return true;
      }
    }
    return false;
  }

  private boolean shouldSkipColumn(String tableName, String columnName) {
    if ("version".equals(columnName) || "id".equals(columnName)) {
      return true;
    }
    String matchAgainst = tableName + "." + columnName;
    for (Pattern pattern : this.skippedColumns) {
      if (pattern.matcher(matchAgainst).matches()) {
        return true;
      }
    }
    return false;
  }

  private void dropExistingTriggers(String table) {
    Jdbc.update(this.ds, "DROP TRIGGER IF EXISTS {}_history_update", table);
    Jdbc.update(this.ds, "DROP TRIGGER IF EXISTS {}_history_insert", table);
    Jdbc.update(this.ds, "DROP TRIGGER IF EXISTS {}_history_delete", table);
  }

  private void createInsertTrigger(String tableName) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE TRIGGER {}_history_insert AFTER INSERT ON {}", tableName, tableName);
    sql.line("FOR EACH ROW");
    sql.line("BEGIN");
    sql.line(" INSERT INTO {}", this.historyTableName);
    sql.line("  (type, root_table_name, primary_key, property_name, old_value, new_value, updater, update_time, version)");
    sql.line(" VALUES");
    sql.line("  ('insert', '{}', NEW.id, null, null, null, @updater, now(), 1);", tableName);
    sql.line("END;");
    Jdbc.update(this.ds, sql.toString());
  }

  private void createDeleteTrigger(Schema schema, String tableName, String rootTableName) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE TRIGGER {}_history_delete AFTER DELETE ON {}", tableName, tableName);
    sql.line("FOR EACH ROW");
    sql.line("BEGIN");
    for (InformationSchemaColumn c : this.columnsForTable(schema, tableName)) {
      if (this.shouldSkipColumn(tableName, c.name)) {
        continue;
      }
      sql.line(" INSERT INTO {}", this.historyTableName);
      sql.line("  (type, root_table_name, primary_key, property_name, old_value, new_value, updater, update_time, version)");
      sql.line(" VALUES");
      sql.line("  ('delete', '{}', OLD.id, '{}', {}, null, @updater, now(), 1);", rootTableName, c.name, snippet("OLD", c));
    }
    sql.line("END;");
    Jdbc.update(this.ds, sql.toString());
  }

  private void createUpdateTrigger(Schema schema, String tableName, String rootTableName) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE TRIGGER {}_history_update AFTER UPDATE ON {}", tableName, tableName);
    sql.line("FOR EACH ROW");
    sql.line("BEGIN");
    for (InformationSchemaColumn c : this.columnsForTable(schema, tableName)) {
      if (this.shouldSkipColumn(tableName, c.name)) {
        continue;
      }
      sql.line("IF NOT BINARY NEW.{} <=> BINARY OLD.{} THEN", c.name, c.name);
      sql.line(" INSERT INTO {}", this.historyTableName);
      sql.line("  (type, root_table_name, primary_key, property_name, old_value, new_value, updater, update_time, version)");
      sql.line(" VALUES");
      sql.line("  ('update', '{}', NEW.id, '{}', {}, {}, @updater, now(), 1);", //
        rootTableName,
        c.name,
        snippet("OLD", c),
        snippet("NEW", c));
      sql.line("END IF;");
    }
    sql.line("END;");
    Jdbc.update(this.ds, sql.toString());
  }

  private static String snippet(String prefix, InformationSchemaColumn c) {
    String fullName = prefix + "." + c.name;
    if ("bit".equals(c.dataType)) {
      // switch byte 0/byte 1 to true/false, assuming this is a BooleanColumn
      return "if(" + fullName + " = 1, 'true', 'false')";
    } else {
      // use default to string
      return "left(" + fullName + ", 255)";
    }
  }

  private List<InformationSchemaColumn> columnsForTable(Schema schema, String table) {
    List<InformationSchemaColumn> cols = new ArrayList<InformationSchemaColumn>();
    for (InformationSchemaColumn c : schema.getColumns()) {
      if (c.tableName.equals(table)) {
        cols.add(c);
      }
    }
    return cols;
  }
}
