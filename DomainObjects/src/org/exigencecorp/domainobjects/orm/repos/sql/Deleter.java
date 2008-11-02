package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Delete;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class Deleter<T extends DomainObject> {

    private Connection connection;
    private Delete<T> delete;

    public Deleter(Connection connection, Delete<T> delete) {
        this.connection = connection;
        this.delete = delete;
    }

    public void delete() {
        Jdbc.update(this.connection, this.toSql(), this.getParameters());
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.line("DELETE FROM {} ", this.delete.getAlias().getTableName());
        if (this.delete.getWhere() != null) {
            s.line(" WHERE {}", this.delete.getWhere());
        }
        s.stripTrailingNewLine();
        return StringUtils.replace(s.toString(), this.delete.getAlias().getName() + ".", "");
    }

    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        if (this.delete.getWhere() != null) {
            parameters.addAll(this.delete.getWhere().getParameters());
        }
        return parameters;
    }

}
