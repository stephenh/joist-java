package joist.migrations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import joist.codegen.Config;
import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;
import joist.util.Copy;
import joist.util.Interpolate;
import joist.util.Wrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermissionFixer {

  private static final Logger log = LoggerFactory.getLogger(PermissionFixer.class);
  private final Config config;
  private final DataSource ds;

  public PermissionFixer(Config config) {
    this.config = config;
    this.ds = config.dbAppSaSettings.getDataSource();
  }

  public void setOwnerOfAllTablesTo(String role) {
    log.info("Setting owner of all tables to {}", role);
    for (String tableName : this.getTableNames()) {
      Jdbc.update(this.ds, Interpolate.string("ALTER TABLE {} OWNER TO {};", Wrap.quotes(tableName), role));
    }
  }

  public void setOwnerOfAllSequencesTo(String role) {
    log.info("Setting owner of all sequences to {}", role);
    for (String sequenceName : this.getSequenceNames()) {
      Jdbc.update(this.ds, Interpolate.string("ALTER TABLE {} OWNER TO {};", Wrap.quotes(sequenceName), role));
    }
  }

  public void grantAllOnAllTablesTo(String role) {
    if (this.config.db.isMySQL()) {
      String userhost = this.config.userhost;
      log.info("Granting ALL on all tables to '{}'@'{}'", role, userhost);
      for (String tableName : this.getTableNames()) {
        Jdbc.update(this.ds, Interpolate.string(
          "GRANT ALL ON TABLE {}.{} TO '{}'@'{}'",
          this.config.dbAppUserSettings.schemaName,
          Wrap.quotes(tableName),
          role,
          userhost));
      }
    } else {
      log.info("Granting ALL on all tables to {}", role);
      for (String tableName : this.getTableNames()) {
        Jdbc.update(this.ds, Interpolate.string("GRANT ALL ON TABLE {} TO {}", Wrap.quotes(tableName), role));
      }
    }
  }

  public void grantAllOnAllSequencesTo(String role) {
    log.info("Granting ALL on all sequences to {}", role);
    for (String sequenceName : this.getSequenceNames()) {
      Jdbc.update(this.ds, Interpolate.string("GRANT ALL ON TABLE {} TO {};", Wrap.quotes(sequenceName), role));
    }
  }

  public void flushPermissionIfNeeded() {
    if (this.config.db.isMySQL()) {
      Jdbc.update(this.ds, "FLUSH PRIVILEGES;");
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
