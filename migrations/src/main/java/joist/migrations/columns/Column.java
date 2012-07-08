package joist.migrations.columns;

import java.util.List;

public interface Column {

  boolean isNullable();

  boolean hasDefault();

  String getName();

  void setTableName(String tableName);

  String toSql();

  List<String> postInjectCommands();

}
