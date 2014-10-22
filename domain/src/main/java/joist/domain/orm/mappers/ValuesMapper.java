package joist.domain.orm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.orm.queries.SelectItem;
import joist.domain.orm.queries.columns.ColumnExpression;
import joist.jdbc.RowMapper;

public class ValuesMapper<R> implements RowMapper {

  private final List<R> results;
  private final ColumnExpression<R, Object> column;

  public ValuesMapper(SelectItem item, List<R> results) {
    this.results = results;
    this.column = (ColumnExpression<R, Object>) item.getColumn();
  }

  public void mapRow(ResultSet rs) throws SQLException {
    Object value = rs.getObject(1);
    R domainValue = (value == null) ? null : this.column.toDomainValue(value);
    this.results.add(domainValue);
  }
}
