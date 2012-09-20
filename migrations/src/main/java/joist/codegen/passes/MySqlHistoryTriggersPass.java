package joist.codegen.passes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import joist.codegen.Codegen;
import joist.codegen.InformationSchemaColumn;
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
public class MySqlHistoryTriggersPass implements Pass<Codegen> {

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

  public void pass(Codegen codegen) {
    log.info("Updating history triggers");
    this.ds = codegen.getConfig().dbAppSaSettings.getDataSource();
    for (Entity entity : codegen.getSchema().getEntities().values()) {
      // always try to drop the trigger
      this.dropExistingTriggers(codegen, entity.getTableName());
      if (this.shouldCreateTrigger(entity.getTableName())) {
        if (entity.isRoot()) {
          this.createInsertTrigger(codegen, entity.getTableName());
          this.createDeleteTrigger(codegen, entity.getTableName());
        }
        this.createUpdateTrigger(codegen, entity.getTableName(), entity.getRootEntity().getTableName());
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

  private boolean shouldCreateTrigger(String tableName) {
    for (Pattern pattern : this.skippedTables) {
      if (pattern.matcher(tableName).matches()) {
        return false;
      }
    }
    return true;
  }

  private boolean shouldCreateTrigger(String tableName, String columnName) {
    String matchAgainst = tableName + "." + columnName;
    for (Pattern pattern : this.skippedColumns) {
      if (pattern.matcher(matchAgainst).matches()) {
        return false;
      }
    }
    return true;
  }

  private void dropExistingTriggers(Codegen codegen, String table) {
    Jdbc.update(this.ds, "DROP TRIGGER IF EXISTS {}_history_update", table);
    Jdbc.update(this.ds, "DROP TRIGGER IF EXISTS {}_history_insert", table);
    Jdbc.update(this.ds, "DROP TRIGGER IF EXISTS {}_history_delete", table);
  }

  private void createInsertTrigger(Codegen codegen, String tableName) {
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

  private void createDeleteTrigger(Codegen codegen, String tableName) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE TRIGGER {}_history_delete AFTER DELETE ON {}", tableName, tableName);
    sql.line("FOR EACH ROW");
    sql.line("BEGIN");
    sql.line(" INSERT INTO {}", this.historyTableName);
    sql.line("  (type, root_table_name, primary_key, property_name, old_value, new_value, updater, update_time, version)");
    sql.line(" VALUES");
    sql.line("  ('delete', '{}', OLD.id, null, null, null, @updater, now(), 1);", tableName);
    sql.line("END;");
    Jdbc.update(this.ds, sql.toString());
  }

  private void createUpdateTrigger(Codegen codegen, String tableName, String rootTableName) {
    StringBuilderr sql = new StringBuilderr();
    sql.line("CREATE TRIGGER {}_history_update AFTER UPDATE ON {}", tableName, tableName);
    sql.line("FOR EACH ROW");
    sql.line("BEGIN");
    for (InformationSchemaColumn c : this.columnsForTable(codegen, tableName)) {
      if (c.name.equals("version") || !this.shouldCreateTrigger(tableName, c.name)) {
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

  private List<InformationSchemaColumn> columnsForTable(Codegen codegen, String table) {
    List<InformationSchemaColumn> cols = new ArrayList<InformationSchemaColumn>();
    for (InformationSchemaColumn c : codegen.getSchema().getColumns()) {
      if (c.tableName.equals(table)) {
        cols.add(c);
      }
    }
    return cols;
  }
}
