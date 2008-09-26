package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.JoinClause;
import org.exigencecorp.domainobjects.queries.Select;
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

    public <R> List<R> select(final Class<R> rowType) {
        final List<R> results = new ArrayList<R>();

        RowMapper mapper = null;
        if (this.isLoadingDomainObjects(this.select.getFrom(), rowType)) {
            mapper = new DomainObjectMapper<T>(this.select.getFrom(), (List<T>) results);
        } else {
            mapper = new DataTransferObjectMapper<T, R>(this.select.getFrom(), rowType, results);
        }

        Jdbc.query(this.connection, this.toSql(), this.getParameters(), mapper);

        return results;
    }

    private boolean isLoadingDomainObjects(Alias<?> alias, Class<?> type) {
        return alias.getDomainBaseClass().isAssignableFrom(type);
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
