package org.exigencecorp.domainobjects.util;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;

public class FlushDatabase {

    private DataSource ds;

    public FlushDatabase(DataSource ds) {
        this.ds = ds;
    }

    public void flush() {
        Jdbc.queryForInt(this.ds, "SELECT flush_test_database()");
    }

}
