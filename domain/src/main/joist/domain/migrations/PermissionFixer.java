package joist.domain.migrations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;
import joist.util.Log;

public class PermissionFixer {

    private DataSource ds;

    public PermissionFixer(DataSource saDatasource) {
        this.ds = saDatasource;
    }

    public void setOwnerOfAllTablesTo(String role) {
        Log.debug("Setting owner of all tables to {}", role);
        for (String tableName : this.getTableNames()) {
            // Jdbc.update(this.ds, "ALTER TABLE \"{}\" OWNER TO {};", tableName, role);
        }
    }

    public void setOwnerOfAllSequencesTo(String role) {
        Log.debug("Setting owner of all sequences to {}", role);
        for (String sequenceName : this.getSequenceNames()) {
            Jdbc.update(this.ds, "ALTER TABLE \"{}\" OWNER TO {};", sequenceName, role);
        }
    }

    public void grantAllOnAllTablesTo(String role) {
        Log.debug("Granting ALL on all tables to {}", role);
        for (String tableName : this.getTableNames()) {
            // Jdbc.update(this.ds, "REVOKE ALL ON TABLE `{}` FROM {}", tableName, role);
            Jdbc.update(this.ds, "GRANT ALL ON TABLE `{}` TO {}@'%'", tableName, role);
        }
    }

    public void grantAllOnAllSequencesTo(String role) {
        Log.debug("Granting ALL on all sequences to {}", role);
        for (String sequenceName : this.getSequenceNames()) {
            Jdbc.update(this.ds, "REVOKE ALL ON TABLE `{}` FROM {};", sequenceName, role);
            Jdbc.update(this.ds, "GRANT ALL ON TABLE `{}` TO {};", sequenceName, role);
        }
    }

    private List<String> getTableNames() {
        final List<String> names = new ArrayList<String>();
        Jdbc.query(this.ds, "SELECT table_name FROM information_schema.tables WHERE table_schema = 'features'", new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                names.add(rs.getString(1));
            }
        });
        return names;
    }

    private List<String> getSequenceNames() {
        final List<String> names = new ArrayList<String>();
        Jdbc.query(this.ds, "SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = 'features'", new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                names.add(rs.getString(1));
            }
        });
        return names;
    }

}
