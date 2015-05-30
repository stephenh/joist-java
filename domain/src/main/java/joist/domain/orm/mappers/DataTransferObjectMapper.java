package joist.domain.orm.mappers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.orm.queries.SelectItem;
import joist.jdbc.RowMapper;

public class DataTransferObjectMapper<T extends DomainObject, R> implements RowMapper {

  private final List<SelectItem> selectItems;
  private final Class<R> rowType;
  private final List<R> results;

  public DataTransferObjectMapper(List<SelectItem> selectItems, Class<R> rowType, List<R> results) {
    this.selectItems = selectItems;
    this.rowType = rowType;
    this.results = results;
  }

  public void mapRow(ResultSet rs) throws SQLException {
    R row = this.newInstance();

    for (SelectItem item : this.selectItems) {
      try {
        Field field = this.rowType.getField(item.getAs());
        Object value;
        if (item.getColumn() != null) {
          int column = rs.findColumn(item.getAs());
          if (rs.getObject(column) == null) {
            value = null;
          } else {
            value = item.getColumn().toDomainValue(rs, column);
          }
        } else {
          value = rs.getObject(item.getAs());
        }
        field.set(row, value);
      } catch (NoSuchFieldException nsfe) {
        throw new RuntimeException(nsfe);
      } catch (IllegalAccessException iae) {
        throw new RuntimeException(iae);
      }
    }

    this.results.add(row);
  }

  private R newInstance() {
    try {
      return this.rowType.newInstance();
    } catch (IllegalAccessException iae) {
      throw new RuntimeException(iae);
    } catch (InstantiationException ie) {
      throw new RuntimeException(ie);
    }
  }

}
