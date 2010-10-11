package joist.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {

  void mapRow(ResultSet rs) throws SQLException;

}
