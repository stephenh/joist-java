package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Insert;
import org.exigencecorp.domainobjects.queries.SetItem;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class Inserter<T extends DomainObject> {

    private Connection connection;
    private Insert<T> insert;

    public Inserter(Connection connection, Insert<T> insert) {
        this.connection = connection;
        this.insert = insert;
    }

    public void insert() {
        Jdbc.update(this.connection, this.toSql(), this.getParameters());
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.line("INSERT INTO {}", this.insert.getAlias().getTableName());
        s.append(" (");
        for (SetItem setItem : this.insert.getSetItems()) {
            s.append("{}, ", setItem.getColumn().getName());
        }
        s.stripLastCommaSpace();
        s.line(")");
        s.line(" VALUES ");
        s.append(" (");
        s.append(StringUtils.repeat("?, ", this.insert.getSetItems().size()));
        s.stripLastCommaSpace();
        s.line(")");
        s.stripTrailingNewLine();
        return s.toString();
    }

    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        for (SetItem setItem : this.insert.getSetItems()) {
            parameters.add(setItem.getValue());
        }
        return parameters;
    }

}
