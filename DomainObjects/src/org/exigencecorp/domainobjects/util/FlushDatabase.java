package org.exigencecorp.domainobjects.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Wrap;

public class FlushDatabase {

    private DataSource ds;
    private Schema schema;

    public FlushDatabase(DataSource ds) {
        this.ds = ds;
        this.schema = new Schema(ds);
    }

    public void flush() {
        Connection conn = null;
        try {
            conn = this.ds.getConnection();

            // All non system data sequences will start at 2
            for (String name : this.schema.getNonSystemDataSequenceNames()) {
                Jdbc.executeUpdate(conn, "ALTER SEQUENCE {} RESTART WITH 2 INCREMENT BY 1", Wrap.quotes(name));
            }

            // We need auto commit=false so deferred waits until conn.commit
            conn.setAutoCommit(false);
            Jdbc.executeUpdate(conn, "SET CONSTRAINTS ALL DEFERRED;");

            // Everything goes from non-system data tables accept call_reason
            for (String name : this.schema.getNonSystemDataTableNames()) {
                Jdbc.executeUpdate(conn, "DELETE FROM {}", Wrap.quotes(name));
            }

            // Anything >= 1000 goes from system data tables
            for (String name : this.schema.getSystemDataTableNames()) {
                if (!"schema_info".equals(name) && !"code_id".equals(name)) { // schema_info has no id field
                    Jdbc.executeUpdate(conn, "DELETE FROM {} WHERE id >= 1000;", Wrap.quotes(name));
                }
            }

            conn.setAutoCommit(true);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(conn);
        }
    }

}
