package org.exigencecorp.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UpdateMapper {

    void mapUpdate(PreparedStatement ps) throws SQLException;

}
