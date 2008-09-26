package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.jdbc.RowMapper;

public class IdsMapper<T extends DomainObject> implements RowMapper {

    private final Alias<T> from;
    private final Ids<T> ids;

    public IdsMapper(Alias<T> from, Ids<T> ids) {
        this.from = from;
        this.ids = ids;
    }

    public void mapRow(ResultSet rs) throws SQLException {
        Integer id = new Integer(rs.getInt(this.from.getIdColumn().getName()));
        this.ids.add(id);
    }

}
