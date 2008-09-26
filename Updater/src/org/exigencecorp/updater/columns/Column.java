package org.exigencecorp.updater.columns;

import org.exigencecorp.util.StringBuilderr;

public interface Column {

    void setTableName(String tableName);

    String toSql();

    void preInjectCommands(StringBuilderr sb);

    void postInjectCommands(StringBuilderr sb);

}
