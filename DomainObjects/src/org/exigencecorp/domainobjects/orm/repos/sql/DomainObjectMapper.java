package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.orm.IdentityMap;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.jdbc.RowMapper;

public class DomainObjectMapper<T extends DomainObject> implements RowMapper {

    private final Alias<T> from;
    private final List<T> results;
    private final IdentityMap cache = UoW.getCurrent().getIdentityMap();

    public DomainObjectMapper(Alias<T> from, List<T> results) {
        this.from = from;
        this.results = results;
    }

    public void mapRow(ResultSet rs) throws SQLException {
        Integer id = new Integer(rs.getInt(this.from.getIdColumn().getName()));
        T instance = (T) this.cache.findOrNull(this.from.getDomainRootClass(), id);

        if (instance == null) {
            instance = this.newInstance(rs);
            this.hydrate(instance, rs);
            this.cache.store(this.from.getDomainRootClass(), instance);
        }

        this.results.add(instance);
    }

    private void hydrate(T instance, ResultSet rs) throws SQLException {
        Alias<?> current = this.from;
        while (current != null) {
            for (AliasColumn<?, ?, ?> c : current.getColumns()) {
                Object jdbcValue = rs.getObject(c.getName());
                ((AliasColumn<T, ?, Object>) c).setJdbcValue(instance, jdbcValue);
            }
            current = current.getBaseClassAlias();
        }
    }

    private T newInstance(ResultSet rs) throws SQLException {
        if (this.from.getSubClassAliases().size() > 0) {
            int offset = rs.getInt("_clazz");
            return this.newInstance(this.from.getSubClassAliases().get(offset));
        }
        return this.newInstance(this.from);
    }

    private T newInstance(Alias<? extends T> alias) {
        try {
            return alias.getDomainClass().newInstance();
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(iae);
        } catch (InstantiationException ie) {
            throw new RuntimeException(ie);
        }
    }

}
