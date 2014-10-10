package joist.migrations.fill;

import java.sql.Connection;
import java.sql.SQLException;

import joist.jdbc.Jdbc;
import joist.util.Wrap;

public class ConstantFillInStrategy implements FillInStrategy {

  private String fragment;

  public ConstantFillInStrategy(String fragment) {
    this.fragment = fragment;
  }

  public ConstantFillInStrategy(int i) {
    this.fragment = String.valueOf(i);
  }

  public void fillIn(Connection connection, String tableName, String columnName) throws SQLException {
    Jdbc.update(//
      connection,
      "UPDATE " + Wrap.backquotes(tableName) + " SET " + columnName + " = " + this.fragment);
  }
}
