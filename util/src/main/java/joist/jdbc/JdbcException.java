package joist.jdbc;

import java.sql.SQLException;

public class JdbcException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JdbcException(SQLException sql) {
    super(sql);
  }

  public SQLException getCause() {
    return (SQLException) super.getCause();
  }

}
