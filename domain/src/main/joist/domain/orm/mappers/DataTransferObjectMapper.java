package joist.domain.orm.mappers;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.DomainObject;


import org.exigencecorp.jdbc.RowMapper;

public class DataTransferObjectMapper<T extends DomainObject, R> implements RowMapper {

    private final Class<R> rowType;
    private final List<R> results;

    public DataTransferObjectMapper(Class<R> rowType, List<R> results) {
        this.rowType = rowType;
        this.results = results;
    }

    public void mapRow(ResultSet rs) throws SQLException {
        if (rs.getMetaData().getColumnCount() == 1) {
            Object value = rs.getObject(1);
            if (this.rowType.isAssignableFrom(value.getClass())) {
                this.results.add((R) value);
                return;
            }
        }

        R row = this.newInstance();
        for (Field field : this.rowType.getFields()) {
            try {
                field.set(row, rs.getObject(field.getName()));
            } catch (IllegalAccessException iae) {
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
