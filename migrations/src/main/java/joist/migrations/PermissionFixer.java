package joist.migrations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import joist.codegen.CodegenConfig;
import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;
import joist.util.Copy;
import joist.util.Wrap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PermissionFixer {

  private final CodegenConfig config;
  private final DataSource ds;

  public PermissionFixer(CodegenConfig config) {
    this.config = config;
    this.ds = config.dbAppSaSettings.getDataSource();
  }

  public void setOwnerOfAllTablesTo(String role) {
    log.debug("Setting owner of all tables to {}", role);
    for (String tableName : this.getTableNames()) {
      Jdbc.update(this.ds, "ALTER TABLE {} OWNER TO {};", Wrap.quotes(tableName), role);
    }
  }

  public void setOwnerOfAllSequencesTo(String role) {
    log.debug("Setting owner of all sequences to {}", role);
    for (String sequenceName : this.getSequenceNames()) {
      Jdbc.update(this.ds, "ALTER TABLE \"{}\" OWNER TO {};", sequenceName, role);
    }
  }

  public void grantAllOnAllTablesTo(String role) {
    log.debug("Granting ALL on all tables to {}", role);
    for (String tableName : this.getTableNames()) {
      Jdbc.update(this.ds, "GRANT ALL ON TABLE {} TO {}{}",//
        Wrap.quotes(tableName),
        role,
        this.config.db.isMySQL() ? "@'%'" : "");
    }
  }

  public void grantAllOnAllSequencesTo(String role) {
    log.debug("Granting ALL on all sequences to {}", role);
    for (String sequenceName : this.getSequenceNames()) {
      Jdbc.update(this.ds, "GRANT ALL ON TABLE {} TO {};", Wrap.quotes(sequenceName), role);
    }
  }

  private List<String> getTableNames() {
    final List<String> names = new ArrayList<String>();
    Jdbc.query(
      this.ds,
      "SELECT table_name FROM information_schema.tables WHERE table_schema = ?",
      Copy.<Object> list(this.config.dbAppUserSettings.schemaName),
      new RowMapper() {
        public void mapRow(ResultSet rs) throws SQLException {
          names.add(rs.getString(1));
        }
      });
    return names;
  }

  private List<String> getSequenceNames() {
    final List<String> names = new ArrayList<String>();
    Jdbc.query(
      this.ds,
      "SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = ?",
      Copy.<Object> list(this.config.dbAppUserSettings.schemaName),
      new RowMapper() {
        public void mapRow(ResultSet rs) throws SQLException {
          names.add(rs.getString(1));
        }
      });
    return names;
  }

}
