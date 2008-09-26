package org.exigencecorp.updater.fill;

import java.sql.Connection;
import java.sql.SQLException;

public interface FillInStrategy {

    void fillIn(Connection connection, String tableName, String columnName) throws SQLException;

}
