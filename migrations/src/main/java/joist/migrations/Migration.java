package joist.migrations;

import java.sql.SQLException;

public interface Migration {

  void apply() throws SQLException;

}
