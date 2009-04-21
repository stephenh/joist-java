package joist.domain.migrations.columns;

import joist.util.StringBuilderr;

public interface Column {

    String getName();

    void setTableName(String tableName);

    String toSql();

    void preInjectCommands(StringBuilderr sb);

    void postInjectCommands(StringBuilderr sb);

}
