package joist.domain.migrations.columns;

import java.util.List;

public interface Column {

    String getName();

    void setTableName(String tableName);

    String toSql();

    List<String> preInjectCommands();

    List<String> postInjectCommands();

}
