package joist.domain.orm.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.jdbc.RowMapper;

public class IdsMapper<T extends DomainObject> implements RowMapper {

    private final Alias<T> from;
    private final List<Integer> ids;

    public IdsMapper(Alias<T> from, List<Integer> ids) {
        this.from = from;
        this.ids = ids;
    }

    public void mapRow(ResultSet rs) throws SQLException {
        this.ids.add(new Integer(rs.getInt(this.from.getIdColumn().getName())));
    }

}
