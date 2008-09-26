package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.orm.ObjectCache;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.JoinClause;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.Join;
import org.exigencecorp.util.StringBuilderr;

public class Selecter<T extends DomainObject> {

    private Connection connection;
    private Select<T> select;

    public Selecter(Connection connection, Select<T> select) {
        this.connection = connection;
        this.select = select;
    }

    public List<T> select(final Class<T> type) {
        final List<T> results = new ArrayList<T>();
        final Alias<T> from = this.select.getFrom();
        final ObjectCache cache = UoW.getCurrent().getObjectCache();
        Jdbc.query(this.connection, this.toSql(), this.getParameters(), new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                try {
                    Integer id = new Integer(rs.getInt(from.getIdColumn().getName()));
                    T instance = (T) cache.findOrNull(from.getDomainBaseClass(), id);
                    if (instance == null) {
                        if (from.getSubClassAliases().size() > 0) {
                            int offset = rs.getInt("_clazz");
                            instance = (T) from.getSubClassAliases().get(offset).getDomainClass().newInstance();
                        }
                        if (instance == null) {
                            instance = type.newInstance();
                        }

                        Alias<?> current = from;
                        while (current != null) {
                            for (AliasColumn<?, ?, ?> c : current.getColumns()) {
                                Object jdbcValue = rs.getObject(c.getName());
                                ((AliasColumn<T, ?, Object>) c).setJdbcValue(instance, jdbcValue);
                            }
                            current = current.getBaseClassAlias();
                        }

                        cache.store(from.getDomainBaseClass(), instance);
                    }
                    results.add(instance);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return results;
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.line("SELECT {}", Join.commaSpace(this.select.getSelectItems()));
        s.line(" FROM {} {}", this.select.getFrom().getTableName(), this.select.getFrom().getName());
        for (JoinClause join : this.select.getJoins()) {
            s.line(" {}", join);
        }
        if (this.select.getWhere() != null) {
            s.line(" WHERE {}", this.select.getWhere());
        }
        if (this.select.getOrderBy() != null) {
            s.line(" ORDER BY {}", Join.commaSpace(this.select.getOrderBy()));
        }
        return s.stripTrailingNewLine().toString();
    }

    public List<Object> getParameters() {
        if (this.select.getWhere() != null) {
            return this.select.getWhere().getParameters();
        }
        return new ArrayList<Object>();
    }

}
