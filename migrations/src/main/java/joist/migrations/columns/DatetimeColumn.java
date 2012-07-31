package joist.migrations.columns;

import joist.migrations.MigrationKeywords;

public class DatetimeColumn extends AbstractColumn<DatetimeColumn> {

  public DatetimeColumn(String name) {
    super(name, MigrationKeywords.isMySQL() ? "datetime" : "timestamp");
  }

}
